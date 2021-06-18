<template>
    <div>
        <!--设置文件夹管理员界面-->
        <el-dialog title="选择文件夹管理员" :visible.sync="formFolderAdmin.visible" :close-on-click-modal="false" @open="openDialog" width="700px">
            <el-form :model="formFolderAdmin.data" label-width="80px" ref="formFolderAdminData">
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="目录名：" prop="name">
                            <app-svg iconClass="wenjianjia3"></app-svg>
                            <span style="font-style: normal;font-weight: normal;">{{formFolderAdmin.data.folderName}}</span>
                        </el-form-item>
                    </el-col>
                    <el-col :span="16">
                        <el-form-item label="管理员列表：" prop="name" label-width="100px">
                            <span style="font-style: normal;font-weight: normal;">{{adminName}}</span>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" style="min-height: 400px; height:400px;">
                    <el-col :span="10">
                        <div style="box-shadow:0 0 1px #ccc;">
                            <el-scrollbar style="height:450px;">
                                <el-tree class="mr20" ref="deptTree" :data="deptTree.data"
                                         highlight-current v-loading="deptTree.loading" :props="deptTree.props"
                                         :expand-on-click-node="false" @node-click="deptSelect" node-key="id" :default-expanded-keys="[1]">
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <div v-if="data.id === 1">
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-zhuye11"></use>
                                            </svg>
                                            <span><b>{{node.label}}</b></span>
                                        </div>
                                        <div v-else>
                                            <div v-if="data.type === 1">
                                                <svg class="icon" aria-hidden="true">
                                                    <use xlink:href="#icon-zuzhiguanli-"></use>
                                                </svg>
                                                <span>{{node.label}}</span>
                                            </div>
                                            <div v-else>
                                                <svg class="icon" aria-hidden="true">
                                                    <use xlink:href="#icon-danweizuzhi"></use>
                                                </svg>
                                                <span>{{node.label}}</span>
                                            </div>
                                        </div>
                                    </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>
                    </el-col>
                    <el-col :span="14">
                        <el-table ref="memberTable" v-loading="memberTable.loading" :data="memberTable.body"
                                  :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" class="custom-table-cell"
                                  highlight-current-row
                                  @current-change="memberSelect"
                                  height="400px">
                            <el-table-column prop="name" label="用户姓名" >
                                <template slot-scope="scope">
                                    <div>
                                        <i class="iconfont icon-geren"></i>
                                        <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleFolderAdminSubmit" :loading="formFolderAdmin.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="handleFolderAdminClose">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--设置文件夹管理员界面-->
    </div>
</template>

<script>
    export default {
        name: "FolderAdmin",
        data(){
            return{
                formFolderAdmin: {
                    data:{
                        folderId: 0,
                        folderName:'',
                        userId: 0,
                        userName: '',
                        deptId:0,
                    },
                    visible: false,
                    loading: false,
                },
                deptTree:{
                    data: [],
                    props: {
                        children: 'children',
                        label: 'name'
                    },
                    loading: false,
                    selected:null,
                },
                memberTable:{
                    height: 0,
                    body: [],
                    pageSize: 1000,
                    total: 0,
                    curPage:1,
                    loading: false,
                    selected:null,
                },
                paginationData: {
                    pageSize: 1000,
                    total: 0,
                    curPage:1
                },
                adminName: ''
            }
        },
        methods:{
            handleFolderAdmin(node){
                // if (this.$refs.formFolderAdminData) this.$refs.formFolderAdminData.created()
                // if (this.$refs.deptTree) this.$refs.deptTree.load()
                this.formFolderAdmin.data ={
                    folderId: node.data.id,
                    folderName:node.data.name,
                    deptId:0,
                    userId: 0,
                    userName: '',
                }
                if (this.$refs.formFolderAdminData) this.$refs.formFolderAdminData.resetFields()
                this.formFolderAdmin.visible = true
            },
            openDialog(){
                this.getDept()
                this.getFolderAdmin(this.formFolderAdmin.data.folderId)
            },
            getDept(){
                this.deptTree.loading = true
                this.$axios({
                    url: `/admin/depts`,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt)
                    this.deptTree.data = data
                    this.deptTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getFolderAdmin(id){
                this.$axios({
                    url: `/admin/folder/${id}/admin`,
                    method: 'get'
                }).then(res => {
                    let datas = []
                    res.ctt.forEach(c => {
                        datas.push(c.name)
                    })
                    this.adminName = datas.join('，')
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            deptSelect(data) {
                this.deptTree.selected = data
                this.getUser(data)
            },
            getUser(data) {
                this.memberTable.loading = true
                this.$axios({
                    url: `/admin/dept/${data.id}/users/${this.paginationData.curPage}-${this.paginationData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.memberTable.body = res.ctt.list
                    this.paginationData.total = res.ctt.total
                    this.memberTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            memberSelect(row) {
                this.memberTable.selected = row
                this.formFolderAdmin.data.userId = row.id
                this.formFolderAdmin.data.userName = row.name
                this.formFolderAdmin.data.deptId = this.deptTree.selected.id
            },
            handleFolderAdminSubmit(){
                if (! this.memberTable.selected) {
                    this.$message.error("请选择文件夹管理员！")
                    return
                }
                this.$confirm('确认添加文件夹管理员吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.formFolderAdmin.loading = true
                    let para = Object.assign({}, this.formFolderAdmin.data);
                    this.$axios({
                        url: `/admin/folder/${this.formFolderAdmin.data.folderId}/admin`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        if (res.c === 0) {
                            this.$message.success("添加成功")
                        } else {
                            this.$message.error(res.msg)
                        }
                        this.formFolderAdmin.loading = false;
                        this.formFolderAdmin.visible = false
                    })
                }).catch(() => {

                });
            },
            handleFolderAdminClose:function(){
                this.formFolderAdmin.loading = false;
                this.formFolderAdmin.visible = false;
                this.memberTable.body = [];
                this.memberTable.selected = null;
            },
        }
    }
</script>

<style scoped>

</style>

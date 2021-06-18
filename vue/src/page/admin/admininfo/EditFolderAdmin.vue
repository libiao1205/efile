<template>
    <div>
    <!--新增界面-->
    <el-dialog title="新增文件夹管理员" :visible.sync="formFolderAdmin.visible" :close-on-click-modal="false" @open="getData" width="1200px">
        <el-form :model="formFolderAdmin.data" label-width="80px" :rules="formFolderAdmin.rules" ref="formAddAdmin">
            <el-row :gutter="20" style="min-height: 400px; height:400px;">
                <el-col :span="8">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar style="height:400px;">
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

                <el-col :span="8">
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
                <el-col :span="8">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar style="height:400px;">
                            <el-tree class="mr20" ref="folderTree" v-if="formFolderAdmin.visible" :accordion="true" :highlight-current="true"
                                     :data="folderTree.body" :props="folderTree.props"  v-loading="folderTree.loading"
                                     :default-expand-all="false" :expand-on-click-node="false" node-key='id' :default-expanded-keys="defaultKey"
                                     lazy :load="printFolderTree" @node-click="folderSelect" >
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <div v-if="data.id === 1" >
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-zhuye11"></use>
                                            </svg>
                                            <b>{{node.label}}</b>
                                        </div>
                                        <div v-else>
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-wenjianjia"></use>
                                            </svg>
                                            <b>{{node.label}}</b>
                                        </div>
                                    </span>
                            </el-tree>
                    </el-scrollbar>
                    </div>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleFolderAdminSubmit" :loading="formFolderAdmin.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="handleFolderAdminClose">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--新增界面-->

    </div>
</template>

<script>
    export default {
        name: "EditFolderAdmin",
        props: {
            addUser: {
                type: Function,
                default: null
            },
            editUser: {
                type: Function,
                default: null
            },

        },
        data(){
            return{
                defaultKey: [1],
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
                folderTree:{
                    visible: false,
                    loading: false,
                    props:{
                        label: 'name',
                        children: 'children',
                    },
                    body: [],
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
        methods: {
            getData: function(){
                this.getDept();
            },
            printFolderTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    id = node.data.id
                }
                this.folderTree.loading = true
                this.$axios({
                    url: `/admin/folder/${id}/children`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.folderTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderSelect(data) {
                this.folderTree.selected = data
                this.formFolderAdmin.data.folderId = data.id
                this.formFolderAdmin.data.folderName = data.name
            },
            getDept: function(){
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
            //新增管理员人员-start
            handleAdminAdd: function () {
                this.formFolderAdmin.data ={
                    folderId: 0,
                    folderName:'',
                    deptId:0,
                    userId: 0,
                    userName: '',
                }
                this.formFolderAdmin.visible = true;
                if (this.$refs.formAddAdmin) this.$refs.formAddAdmin.resetFields()
            },
            //设为文件夹管理员
            handleFolderAdminSubmit: function () {

                if (! this.memberTable.selected) {
                    this.$message.error("请选择文件夹管理员！")
                    return
                }
                if (! this.folderTree.selected) {
                    this.$message.error("请选择文件夹！")
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
                            this.$message.success("已设为文件夹管理员")
                            this.memberTable.selected = null;
                            this.memberTable.body = [];
                            this.folderTree.selected = null;
                            this.formFolderAdmin.loading = false;
                            this.formFolderAdmin.visible = false
                            this.editUser();
                        }else if (res.c === 1013 || res.c === 1014){
                            this.$message.error(res.msg)
                            this.formFolderAdmin.loading = false;
                        } else {
                            this.$message.error(res.msg)
                            this.memberTable.selected = null;
                            this.memberTable.body = [];
                            this.folderTree.selected = null;
                            this.formFolderAdmin.loading = false;
                            this.formFolderAdmin.visible = false
                            this.editUser();
                        }

                    })
                }).catch(() => {

                });
            },
            handleFolderAdminClose:function(){
                this.formFolderAdmin.loading = false;
                this.formFolderAdmin.visible = false;
                this.folderTree.selected = null;
                this.memberTable.body = [];
                this.memberTable.selected = null;
            },
            //新增管理员人员-end
        }
    }
</script>

<style scoped>

</style>

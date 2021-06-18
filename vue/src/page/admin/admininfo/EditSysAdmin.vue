<template>
    <div>
    <!--新增界面-->
    <el-dialog title="新增系统管理员" :visible.sync="formSysAdmin.visible" :close-on-click-modal="false" @open="getDept" width="700px">
        <el-form :model="formSysAdmin.data" label-width="80px" :rules="formSysAdmin.rules" ref="formAddAdmin">
            <el-row :gutter="20" style="min-height: 400px; height:400px;">
                <el-col :span="10">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar>
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
                    <!--<el-form-item label="管理权限">-->
                        <!--<el-radio-group v-model="formUser.data.level">-->
                            <!--<el-radio class="radio" :label="0" border>文件夹管理员</el-radio>-->
                            <!--<el-radio class="radio" :label="1" border>系统管理员</el-radio>-->
                        <!--</el-radio-group>-->
                    <!--</el-form-item>-->
                </el-col>

            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleSysAdminSubmit" :loading="formSysAdmin.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="handleSysAdminClose">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--新增界面-->

    </div>
</template>

<script>
    export default {
        name: "EditSysAdmin",
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
                formSysAdmin: {
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
        methods: {
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
                this.formSysAdmin.data.userId = row.id
                this.formSysAdmin.data.userName = row.name
                this.formSysAdmin.data.deptId = this.deptTree.selected.id
            },

            //新增管理员人员-start
            handleAdminAdd: function () {
                this.formSysAdmin.data ={
                    folderId: 0,
                    folderName:'',
                    deptId:0,
                    userId: 0,
                    userName: '',
                }
                this.formSysAdmin.visible = true;
                if (this.$refs.formAddAdmin) this.$refs.formAddAdmin.resetFields()
            },
            //设为系统管理员
            handleSysAdminSubmit:function(){
                if (! this.memberTable.selected) {
                    this.$message.error("请选择系统管理员！")
                    return
                }
                let para = this.memberTable.selected;
                let msg = "确认把["+ para.name +"]设置为系统管理员吗?"
                this.$confirm(msg, '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: `/admin/user/${para.id}/sysadmin`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        if (res.c === 0) {
                            this.$message.success("已设为系统管理员")
                            this.memberTable.selected = null;
                            this.memberTable.body = [];
                            this.formSysAdmin.loading = false;
                            this.formSysAdmin.visible = false
                            this.editUser();
                        }else if (res.c === 1013 || res.c === 1014){
                            this.$message.error(res.msg)
                            this.formSysAdmin.loading = false;
                        } else {
                            this.$message.error(res.msg)
                            this.memberTable.selected = null;
                            this.memberTable.body = [];
                            this.formSysAdmin.loading = false;
                            this.formSysAdmin.visible = false
                            this.editUser();
                        }

                    })
                }).catch(() => {

                });
            },
            handleSysAdminClose:function(){
                this.formSysAdmin.loading = false;
                this.formSysAdmin.visible = false;
                this.memberTable.body = [];
                this.memberTable.selected = null;
            },
            //新增管理员人员-end
        }
    }
</script>

<style scoped>

</style>

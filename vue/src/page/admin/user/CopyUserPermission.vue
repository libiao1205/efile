<template>
    <div>
    <!--新增界面-->
    <el-dialog title="复制用户权限" :visible.sync="formCopyUserPermission.visible" :close-on-click-modal="false" @open="printDept" width="1000px">
        <el-form :model="formCopyUserPermission.data" label-width="80px" :rules="formCopyUserPermission.rules" ref="formCopyUserPermission">
            <el-row :gutter="20" style="min-height: 460px; height:460px;">
                <el-col :span="8">
                    <div style="box-shadow:0 0 1px #ccc;height:460px;">
                        <el-scrollbar>
                            <el-tree class="mr20" ref="deptTree" :data="deptTree.data"
                                     highlight-current v-loading="deptTree.loading" :props="deptTree.props"
                                     :expand-on-click-node="false" @node-click="selectDept" node-key="id" :default-expanded-keys="[1]">
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

                <el-col :span="16">
                    <el-row style="margin-bottom: 20px;" >
                        <el-col :span="19">
                            <el-radio-group v-model="formCopyUserPermission.data.radio">
                                <el-radio-button :label="1">把所选用户的权限复制给{{formCopyUserPermission.data.user.name}}</el-radio-button>
                                <el-radio-button :label="2">把{{formCopyUserPermission.data.user.name}}的权限复制给所选用户</el-radio-button>
                            </el-radio-group>
                        </el-col>
                        <el-col :span="2">
                            <el-checkbox v-model="formCopyUserPermission.data.isMove">删除复制来源</el-checkbox>
                        </el-col>
                    </el-row>
                    <el-row  :gutter="20" >
                        <el-col :span="12">
                            <el-table ref="memberTable" class="custom-table-cell" v-loading="memberTable.loading" :data="memberTable.body"
                                      :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                                      height="400px">
                                <el-table-column prop="name" label="用户姓名" >
                                    <template slot-scope="scope">
                                        <div>
                                            <i class="iconfont icon-geren"></i>
                                            <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="name" label="操作" width="60px">
                                    <template slot-scope="scope">
                                        <div>
                                            <el-tooltip class="item" effect="dark" content="增加成员" placement="right">
                                                <el-button class="btnAdd" type="text" size="mini"
                                                           icon="el-icon-d-arrow-right"
                                                           @click="addUserToGroup(scope.row)"
                                                           circle></el-button>
                                            </el-tooltip>
                                        </div>
                                    </template>
                                </el-table-column>

                            </el-table>
                        </el-col>

                        <el-col :span="12">
                            <el-table ref="selectedTable" class="custom-table-cell" v-loading="selectedTable.loading" :data="selectedTable.body"
                                      :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                                      height="400px">
                                <el-table-column prop="name" label="已选择用户">
                                    <template slot-scope="scope">
                                        <div>
                                            <i class="iconfont icon-geren"></i>
                                            <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="name" label="操作" width="60px">
                                    <template slot-scope="scope">
                                        <div>
                                            <el-tooltip class="item" effect="dark" content="删除成员" placement="right">
                                                <el-button class="btnDel" type="text" size="mini"
                                                           icon="iconfont icon-yonghushanchu"
                                                           @click="removeUserFromGroup(scope.row)"
                                                           circle></el-button>
                                            </el-tooltip>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleUserGroupSubmit" :loading="formCopyUserPermission.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="handleUserGroupClose">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--新增界面-->

    </div>
</template>

<script>
    export default {
        name: "CopyUserPermission",
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
              formCopyUserPermission: {
                    data:{
                        id:0,
                        radio: 1,
                        isMove:false,
                        user:{
                          name:''
                        },
                    },
                    submit:{
                        isMove:0,
                        from:[],
                        to:[],
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
                    total: 0,
                    loading: false,
                    selected:null,
                },
                selectedTable:{
                    height:0,
                    body:[],
                    loading:false,
                    selected:null,
                    changeSelectedDept:false,
                },
                paginationData: {
                    pageSize: 1000,
                    total: 0,
                    curPage:1
                },
            }
        },
        methods: {
            printDept: function(){
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
            selectDept(data) {
                this.deptTree.selected = data
                this.printUser(data)
                this.selectedTable.changeSelectedDept = true
            },
            printUser(data) {
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
            addUserToGroup(row) {
                for (var i=0;i<this.selectedTable.body.length;i++) {
                    if (this.selectedTable.body[i].id === row.id){
                        this.$message.warning("成员已在组内，不能再次添加！")
                        return
                    }
                }
                this.selectedTable.body.push(row)
            },
            removeUserFromGroup(row){
                for (var i=0;i<this.selectedTable.body.length;i++) {
                    if (this.selectedTable.body[i].id === row.id){
                        this.selectedTable.body.splice(i, 1);
                        return
                    }
                }
            },
            //编辑权限用户-start
            handleUserPermissionCopy: function (user) {
                this.formCopyUserPermission.data ={
                    id: 0,
                    radio:1,
                    user:user
                }
                this.formCopyUserPermission.visible = true;
                if (this.$refs.formCopyUserPermission) this.$refs.formCopyUserPermission.resetFields()
            },
            handleUserGroupClose:function(){
                this.formCopyUserPermission.loading = false;
                this.formCopyUserPermission.visible = false;
                this.memberTable.body = [];
                this.memberTable.selected = null;
                this.selectedTable.body = [];
                this.selectedTable.selected = null;
            },
            handleUserGroupSubmit:function(){
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if ((!this.selectedTable.body)||this.selectedTable.body.length === 0) {
                    this.$message.error("请选择需要复制权限的用户！")
                    return
                }

                let msg = ''
                if (this.formCopyUserPermission.data.radio === 1){
                  if (this.formCopyUserPermission.data.isMove){
                    msg = `确认把所选用户的权限转移给[${this.formCopyUserPermission.data.user.name}]吗?<br/> 请注意，这将删除所选用户的相应权限！`
                    this.formCopyUserPermission.submit.isMove = 1
                  }else{
                    msg = "确认把所选用户的权限复制给["+ this.formCopyUserPermission.data.user.name +"]吗?"
                    this.formCopyUserPermission.submit.isMove = 0
                  }
                  this.formCopyUserPermission.submit.from =this.selectedTable.body
                  this.formCopyUserPermission.submit.to.push(this.formCopyUserPermission.data.user)
                }else{
                  if (this.formCopyUserPermission.data.isMove){
                    msg = `确认把[${this.formCopyUserPermission.data.user.name}]的权限转移给所选用户吗?<br/> 请注意，这将删除[${this.formCopyUserPermission.data.user.name}]的相应权限！`
                    this.formCopyUserPermission.submit.isMove = 1
                  }else{
                    msg = "确认把["+ this.formCopyUserPermission.data.user.name +"]的权限复制给所选用户吗?"
                    this.formCopyUserPermission.submit.isMove = 0
                  }
                  this.formCopyUserPermission.submit.from.push(this.formCopyUserPermission.data.user)
                  this.formCopyUserPermission.submit.to  =this.selectedTable.body
                }

                let method = 'post'
                this.$confirm(msg, '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    dangerouslyUseHTMLString: true,
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: `/admin/copyRole`,
                        method: method,
                        data: this.formCopyUserPermission.submit
                    }).then((res) => {
                        if (res.c === 0) {
                            this.$message.success("用户权限复制完成！")
                            this.formCopyUserPermission.loading = false;
                            this.formCopyUserPermission.visible = false;
                            this.memberTable.body = [];
                            this.memberTable.selected = null;
                            this.selectedTable.body = [];
                            this.selectedTable.selected = null;
                        } else {
                            this.$message.error(res.msg)
                            this.formCopyUserPermission.loading = false;
                            this.formCopyUserPermission.visible = false;
                            this.memberTable.body = [];
                            this.memberTable.selected = null;
                            this.selectedTable.body = [];
                            this.selectedTable.selected = null;
                        }

                    })
                }).catch(() => {

                });
            },
            //编辑用户组-end
        }
    }
</script>

<style scoped>

</style>

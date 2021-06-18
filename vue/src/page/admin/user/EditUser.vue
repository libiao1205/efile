<template>
    <div>
    <!--新增界面-->
    <el-dialog title="新增用户" :visible.sync="formUser.addVisible" :close-on-click-modal="false" @open="getDept" width="700px">
        <el-form :model="formUser.data" label-width="80px" :rules="formUser.rules" ref="formAddUser">
            <el-row :gutter="20" style="min-height: 400px; height:400px;">
                <el-col :span="10">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar>
                            <el-tree ref="moveDeptTree" :data="treeDept.data" highlight-current v-loading="treeDept.loading" :props="treeDept.props"
                                     :expand-on-click-node="false" @node-click="parentDeptSelect" node-key="id" :default-expanded-keys="[1]">
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
                    <!--</app-panel>-->
                </el-col>

                <el-col :span="14">
                    <el-form-item label="所属组织" prop="deptName">
                        <el-input v-model="formUser.data.deptName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="登录账号" prop="username">
                        <el-input v-model="formUser.data.username" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="用户姓名" prop="name">
                        <el-input v-model="formUser.data.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-col :span="24"><el-input v-model="formUser.data.password">
                            <el-button slot="append" icon="el-icon-refresh" @click="handleResetPassword()">随机密码</el-button>
                        </el-input></el-col>
                    </el-form-item>
                    <el-form-item label="管理权限">
                        <el-radio-group v-model="formUser.data.level">
                            <el-radio class="radio" :label="0" border>普通用户</el-radio>
                            <el-radio class="radio" :label="1" border>系统管理员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>

            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleUserAddSubmit" :loading="formUser.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="formUser.addVisible = false">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--新增界面-->

    <!--修改界面-->
    <el-dialog title="用户编辑" :visible.sync="formUser.editVisible" :close-on-click-modal="false"  @open="getDept" width="700px">
        <el-form :model="formUser.data" label-width="100px" :rules="formUser.rules" ref="formEditUser">
            <el-row :gutter="20" style="min-height: 400px; height:400px;">
                <el-col :span="10">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar>
                            <el-tree ref="moveDeptTree" :data="treeDept.data" highlight-current v-loading="treeDept.loading" :props="treeDept.props"
                                     :expand-on-click-node="false" @node-click="parentDeptSelect" node-key="id" :default-expanded-keys="treeDept.defaultNodes">
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
                    <!--</app-panel>-->
                </el-col>


                <el-col :span="14">
                    <el-form-item label="所属组织" prop="oldDeptName">
                        <el-input v-model="formUser.data.oldDeptName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="编辑新组织" prop="deptName">
                        <el-input v-model="formUser.data.deptName" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="登录名" prop="username">
                        <el-input v-model="formUser.data.username" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="formUser.data.name" auto-complete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleUserBaseEditSubmit" :loading="formUser.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="formUser.editVisible = false">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--修改界面-->

    <!--密码重置界面-->
    <el-dialog title="密码重置" :visible.sync="formUser.passwordVisible" :close-on-click-modal="false" width="400px">
        <el-form :model="formUser.data" label-width="80px" :rules="formUser.rules" ref="formPassword">
            <el-form-item label="密码" prop="password">
                <el-col :span="24"><el-input v-model="formUser.data.password">
                    <el-button slot="append" icon="el-icon-refresh" @click="handleResetPassword">随机密码</el-button>
                </el-input></el-col>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleUserPasswordEditSubmit" :loading="formUser.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="formUser.passwordVisible = false">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
    <!--密码重置界面-->
    </div>
</template>

<script>
    export default {
        name: "EditUser",
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
                /*人员新增、编辑数据处理 Start*/
                formUser:{
                    addVisible: false,
                    editVisible: false,
                    passwordVisible: false,
                    loading: false,
                    rules:{
                        username: [
                            { required: true, message: '请输入登录名', trigger: 'blur' }
                        ],
                        name: [
                            { required: true, message: '请输入姓名', trigger: 'blur' }
                        ],
                        password: [
                            { required: true, message: '请输入密码', trigger: 'blur' }
                        ],
                    },
                    data: {
                        departments: [{
                            "id": 0
                        }],
                        admin:{
                            "level": 0
                        },
                        id:0,
                        name:'',
                        username: '',
                        password: '',
                        status: 1,
                        oldDeptId:0,
                        oldDeptName:'',
                        deptId:0,
                        deptName:'',
                        level:0,
                    }
                },
                /*人员新增、编辑数据处理 End*/

                /*部门树数据 Start*/
                treeDept:{
                    data: [],
                    loading: false,
                    props: {
                        children: 'children',
                        label: 'name',
                    },
                    defaultNodes: [1],
                    currentKey : ''
                }
                /*部门树数据 End*/
            }
        },
        methods: {
            //新增人员-start
            handleUserAdd: function () {
                this.formUser.addVisible = true;
                this.formUser.data = {
                    departments: [{
                        "id": 0
                    }],
                    admin:{
                        "level": 0
                    },
                    id:0,
                    name:'',
                    username: '',
                    password: 'p@ssw0rd',
                    status: 1,
                    oldDeptId:0,
                    oldDeptName:'',
                    deptId:0,
                    deptName:'',
                    level:0,
                }
                if (this.$refs.formAddUser) this.$refs.formAddUser.resetFields()
            },
            //新增人员-start
            handleUserAddByDept: function (data) {
                this.formUser.addVisible = true;
                this.formUser.data = {
                    departments: [{
                        "id": 0
                    }],
                    admin:{
                        "level": 0
                    },
                    id:0,
                    name:'',
                    username: '',
                    password: 'p@ssw0rd',
                    status: 1,
                    oldDeptId:0,
                    deptId:0,
                    deptName:'',
                    level:0,
                }
                if (this.$refs.formAddUser) this.$refs.formAddUser.resetFields()
                this.parentDeptSelect(data)
            },
            handleUserAddSubmit: function () {
                if (this.formUser.data.deptId === 0) {
                    this.$message.error("请选择用户所属组织架构")
                    return
                }
                this.$refs.formAddUser.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认新增吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formUser.loading = true
                            let pram = Object.assign({}, this.formUser.data)
                            pram.departments = [{
                                "id": this.formUser.data.deptId
                            }],
                            pram.admin = {
                                "level": this.formUser.data.level
                            },
                            this.$axios({
                                url: '/admin/user',
                                method: 'post',
                                data: pram
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("添加用户成功")
                                    this.$refs.formAddUser.resetFields()
                                    this.formUser.addVisible = false
                                    this.addUser()
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formUser.loading = false
                            })
                        })
                    }
                })
            },
            //新增人员-end

            //编辑人员基本信息-start
            handleUserBaseEdit: function (index, row) {
                this.fillModifyData(row)
                this.formUser.editVisible = true;
            },
            handleUserBaseEditSubmit: function () {
                this.$refs.formEditUser.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交修正吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formUser.loading = true
                            let param = Object.assign({}, this.formUser.data);
                            if (this.formUser.data.oldDeptId !== this.formUser.data.deptId){
                                param.departments = [{
                                    "id": this.formUser.data.deptId
                                }]
                            }
                            this.$axios({
                                url: `/admin/user/${this.formUser.data.id}/base`,
                                method: 'put',
                                data: param
                            }).then((res) => {
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs.formEditUser.resetFields()
                                this.formUser.editVisible = false
                                this.formUser.loading = false
                                this.editUser();
                            })
                        })
                    }
                })
            },
            //编辑人员基本信息-end

            //设为系统管理员
            handleAdminSubmit:function(index, row){
                this.$confirm('确认设置为管理员?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/user/${row.id}/sysadmin`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '已设为系统管理员',
                            type: 'success'
                        });
                        this.editUser();
                    })
                }).catch(() => {

                });
            },

            //账户封禁
            handleUserLockSubmit:function(index, row){
                this.$confirm('确认封禁该用户?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/user/${row.id}/lock`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '用户状态已更新',
                            type: 'success'
                        });
                        this.editUser();
                    })
                }).catch(() => {

                });
            },

            //人员离职-start
            handleUserLeaveSubmit: function (index, row) {
                this.$confirm('确认该用户离职?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/user/${row.id}/leave`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '用户状态已更新',
                            type: 'success'
                        });
                        this.editUser();
                    })
                }).catch(() => {

                });
            },
            //人员离职-end

            //人员状态恢复
            handleUserUnlockSubmit:function(index, row){
                this.$confirm('确认恢复用户正常状态?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/user/${row.id}/normal`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '用户状态已恢复',
                            type: 'success'
                        });
                        this.editUser();
                    })
                }).catch(() => {

                });
            },
            //人员删除-start
            handleUserDeleteSubmit: function (index, row) {
                this.$confirm('确认删除该用户?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: '/admin/user',
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '用户状态已更新',
                            type: 'success'
                        });
                        this.editUser();
                    })
                }).catch(() => {

                });
            },
            //人员删除-end

            //填充编辑用数据包
            fillModifyData:function(row) {
                this.formUser.data = {
                    id:row.id,
                    departments: row.departments,
                    admin:row.admin,
                    name:row.name,
                    username: row.username,
                    password: row.password,
                    status: row.status,
                    oldDeptId:row.departments[0].id,
                    oldDeptName:row.departments[0].name,
                    deptId:row.departments[0].id,
                    deptName:row.departments[0].name,
                    level:(row.admin)?row.admin.level:0,
                }
            },
            //密码重置-start
            handleUserPasswordEdit: function (index, row) {
                this.fillModifyData(row)
                this.formUser.passwordVisible = true;
            },
            handleResetPassword:function(){
                this.formUser.data.password = this.randomString(6)
            },
            handleUserPasswordEditSubmit: function () {
                this.$refs.formPassword.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交修正吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formUser.loading = true
                            let para = Object.assign({}, this.formUser.data);
                            this.$axios({
                                url: `/admin/user/${this.formUser.data.id}/password`,
                                method: 'put',
                                data: para
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message({
                                        message: '提交成功',
                                        type: 'success'
                                    });
                                }else{
                                    this.$message({
                                        message: res.msg,
                                        type: 'error'
                                    });
                                }
                                this.formUser.loading = false
                                this.$refs.formPassword.resetFields()
                                this.formUser.passwordVisible = false
                                this.editUser();
                            })
                        })
                    }
                })
            },
            //密码重置-end

            randomString(len) {
                len = len || 6;
                // var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
                var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
                var maxPos = $chars.length;
                var pwd = '';
                for (var i = 0; i < len; i++) {
                    pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            },
            getDept: function(){
                this.treeDept.loading = true
                this.$axios({
                    url: `/admin/depts`,
                    method: 'get'
                }).then((res) => {
                    let data = []
                    data.push(res.ctt)
                    data[0].isRoot = true
                    this.treeDept.data = data
                    this.treeDept.loading = false
                    this.$nextTick(function () {
                        if (this.formUser.data.deptId > 0){
                            this.findDefaultNodes(this.formUser.data.deptId,data)
                        }
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //部门树选择赋值
            parentDeptSelect(data) {
                this.formUser.data.deptId = data.id
                this.formUser.data.deptName = data.name
            },
            findDefaultNodes(id,data){
                for(let i = 0; i < data.length; i++){
                    if (data[i].id === id) {
                        this.treeDept.defaultNodes = data[i].parents
                        this.$refs.moveDeptTree.setCurrentKey(data[i].id)
                        return true
                    }else{
                        if (data[i].children.length > 0){
                            let finded = this.findDefaultNodes(id,data[i].children)
                            if (finded) return true
                        }
                    }
                }
            },
        }
    }
</script>

<style scoped>

</style>

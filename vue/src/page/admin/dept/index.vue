<template>
    <el-container>
        <el-header height="106" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="drxx95"></app-svg>  组织架构管理</h1>
                </div>
                <div class="ib pd10">
                    <el-input placeholder="请输入用户姓名" class="input-with-select" clearable @keyup.enter.native = "search" @clear="clearSearchTxt" v-model="searchTxt">
                        <el-button  slot="append" icon="el-icon-search" @click="search"></el-button>
                    </el-input>
                </div>
            </div>
<!--            <div class="fxb bb1" >-->
<!--                <div class="fxr pd5">-->
<!--                    <el-button type="primary" size="mini" icon="iconfont icon-caozuo-shangchuan-download" @click="handleDeptInput" plain>批量导入</el-button>-->
<!--                </div>-->
<!--            </div>-->
        </el-header>
        <split-pane split="vertical" :className="splitClass" :defaultPercent=50  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
            <template slot="paneL">
                    <el-scrollbar>
                        <el-tree class="mr20" ref="deptTree" :data="deptData" highlight-current v-loading="deptDataLoading" :props="deptDataProps"
                                 :expand-on-click-node="false" @node-click="mainDeptSelect" node-key="id" :default-expanded-keys="[1]">
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
                                <span>
                                    <el-tooltip class="item" effect="dark" content="新增下级组织架构" placement="bottom">
                                        <el-button v-if="data.isRoot" class="btnAdd" type="text" size="mini"
                                                   icon="iconfont icon-add" @click="handleDeptAdd(node)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="编辑名称" placement="bottom">
                                        <el-button v-if="!data.isRoot"
                                                   class="btnEdit" type="text"
                                                   icon="iconfont icon-edit-row"
                                                   @click="handleDeptEdit(node)"
                                                   circle></el-button>
                                    </el-tooltip>
<!--                                    <el-tooltip class="item" effect="dark" content="编辑组织结构" placement="bottom">-->
<!--                                        <el-button v-if="!data.isRoot"-->
<!--                                                   class="btnMove" type="text" size="mini"-->
<!--                                                   icon="iconfont icon-edit-node"-->
<!--                                                   @click="handleDeptMove(node)"-->
<!--                                                   circle></el-button>-->
<!--                                    </el-tooltip>-->
                                    <el-tooltip class="item" effect="dark" content="封存" placement="bottom">
                                        <el-button v-if="!data.isRoot"
                                                   class="btnDel" type="text" size="mini"
                                                   icon="iconfont icon-dongjie"
                                                   @click="handleDeptDeleteSubmit(node)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="新增组织架构用户" placement="bottom">
                                        <el-button v-if="!data.isRoot"
                                                   class="btnAddUser" type="text" size="mini"
                                                   icon="iconfont icon-add-user"
                                                   @click="handleUserAdd(node)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </span>
                            </span>
                        </el-tree>
                    </el-scrollbar>
                </template>
                <template slot="paneR">
                    <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                        :cell-style="{padding:'5px'}" class="custom-table-cell"
                        :header-cell-style="{padding: '2px'}" :height="tableData.height">
                        <el-table-column prop="name" label="用户姓名" >
                            <template slot-scope="scope">
                                <div>
                                    <i class="iconfont icon-geren"></i>
                                    <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column  label="操作" align="center">
                            <template slot-scope="scope">
                                <div v-if="scope.row.status === 8"><span>离职用户</span>
                                    <el-tooltip class="item" effect="dark" content="再入职" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnLock" type="text" size="mini"
                                                   icon="iconfont icon-add-user"
                                                   @click="handleUserUnlockSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </div>
                                <div v-else-if="scope.row.status === 4"><span>限制登录</span>
                                    <el-tooltip class="item" effect="dark" content="解封账户" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnLock" type="text" size="mini"
                                                   icon="iconfont icon-jiechu"
                                                   @click="handleUserUnlockSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </div>
                                <div v-else>
                                    <el-tooltip class="item" effect="dark" content="编辑基本信息" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnAdd" type="text" size="mini"
                                                   icon="iconfont icon-edit-row"
                                                   @click="handleUserBaseEdit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="密码重置" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnAdd" type="text" size="mini"
                                                   icon="iconfont icon-denglumima"
                                                   @click="handleUserPasswordEdit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="设为系统管理员" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnEdit" type="text" size="mini"
                                                   icon="iconfont icon-guanli_danse"
                                                   @click="handleAdminSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <!--<el-tooltip class="item" effect="dark" content="编辑部门" placement="bottom">-->
                                    <!--<el-button v-if="!scope.row.isRoot"-->
                                    <!--class="btnMove" type="text" size="mini"-->
                                    <!--icon="iconfont icon-edit-node"-->
                                    <!--@click="handleUserBaseEdit(scope.$index, scope.row)"-->
                                    <!--circle></el-button>-->
                                    <!--</el-tooltip>-->
                                    <el-tooltip class="item" effect="dark" content="封禁" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnLock" type="text" size="mini"
                                                   icon="iconfont icon-dongjie"
                                                   @click="handleUserLockSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <!--<el-tooltip class="item" effect="dark" content="离职" placement="bottom">-->
                                        <!--<el-button v-if="!scope.row.isRoot"-->
                                                   <!--class="btnLeave" type="text" size="mini"-->
                                                   <!--icon="iconfont icon-lizhi"-->
                                                   <!--@click="handleUserLeaveSubmit(scope.$index, scope.row)"-->
                                                   <!--circle></el-button>-->
                                    <!--</el-tooltip>-->
                                    <el-tooltip class="item" effect="dark" content="账户封存" placement="bottom">
                                        <el-button v-if="!scope.row.isRoot"
                                                   class="btnDel" type="text" size="mini"
                                                   icon="iconfont icon-yonghushanchu"
                                                   @click="handleUserDeleteSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </div>
                            </template>
                        </el-table-column>
                        <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                            <span slot="no-more"></span>
                        </infinite-loading>
                    </el-table>
                </template>
            </split-pane>
        <!--新增部门界面-->
        <el-dialog title="组织架构新增" :visible.sync="formDeptAddVisible" :close-on-click-modal="false" width="450px">
            <el-form :model="formDeptData" label-width="100px" :rules="formDeptRules" ref="formDeptData">
                <el-form-item label="上级组织" prop="parentName">
                    <el-input v-model="formDeptData.parentName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="组织名称" prop="name">
                    <el-input v-model="formDeptData.name" auto-complete="off"></el-input>
                </el-form-item>
                <!--<el-form-item label="基地/分公司设定">-->
                    <!--<el-checkbox v-model="formDeptData.type" :trueLabel=0 :falseLabel=1 border>设为基地/分公司</el-checkbox>-->
                <!--</el-form-item>-->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleDeptAddSubmit" :loading="deptDataLoading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="formDeptAddVisible = false">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--新增部门界面-->

        <!--编辑部门界面-->
        <el-dialog title="组织架构编辑" :visible.sync="formDeptEditVisible" :close-on-click-modal="false" width="450px">
            <el-form :model="formDeptData" label-width="100px" :rules="formDeptRules" ref="formDeptData">
                <el-form-item label="上级组织" prop="parentName">
                    <el-input v-model="formDeptData.parentName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="组织名称" prop="name">
                    <el-input v-model="formDeptData.name" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleDeptEditSubmit" :loading="deptDataLoading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="formDeptEditVisible = false">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--编辑部门界面-->

        <!--部门移动-->
        <el-dialog title="变更部门组织结构" :visible.sync="formDeptMoveVisible" :close-on-click-modal="false" width="700px">
            <el-form :model="formDeptMoveData" label-width="120px" :rules="formDeptMoveRules" ref="formDeptMoveData">
                <el-row :gutter="30" style="min-height: 400px; height:400px;">
                    <el-col :span="12" style="height: 100%;" >
                        <div style="box-shadow:0 0 1px #ccc;height:400px;">
                            <el-scrollbar>
                                <el-tree ref="moveDeptTree" :data="deptData" highlight-current v-loading="deptDataLoading" :props="deptDataProps"
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
                    </el-col>
                    <el-col :span="12" >
                        <el-form-item label="部门名称" prop="orgParentName">
                            <el-input v-model="formDeptMoveData.name" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="当前上级部门" prop="orgParentName">
                            <el-input v-model="formDeptMoveData.orgParentName" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="新的上级部门" prop="parentName">
                            <el-input v-model="formDeptMoveData.parentName" :disabled="true"></el-input>
                        </el-form-item>
                        <div style="margin: 15px 0 0 15px; padding: 5px; display: flex; height: 100px;border-radius:5px;border: 1px solid #dcdfe6;">
                            <span style="color: #606266;font-weight:bold;">注意：修正上级部门时，将同时转换本身部门的下级部门和部门人员的组织关系。</span>
                        </div>
                    </el-col>
                </el-row>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-edit-node" @click.native="handleDeptMoveSubmit" :loading="deptDataLoading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="formDeptMoveVisible = false">
                    <span style="padding-left: 10px;"><b>取消</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--部门移动-->

        <edit-user ref="editUser" :add-user="refresh" :edit-user="refresh"></edit-user>
    </el-container>
</template>

<script>
    import splitPane from 'vue-splitpane'
    import EditUser from 'sysPage/admin/user/EditUser'
    export default {
        components:{EditUser, splitPane},
        name: 'deptlist',
        data() {
            return {
                /*部门树数据 Start*/
                deptData: [],
                deptDataProps: {
                    children: 'children',
                    label: 'name'
                },
                deptDataLoading: false,
                selectDept:null,
                /*部门树数据 End*/

                /*用户表格数据 Start*/
                tableData: {
                    height: 0,
                    loading: false,
                    body: [],
                    pageSize: 1000,
                    total: 0,
                    curPage:0
                },
                /*用户表格数据 End*/


                /*部门新增、编辑数据处理 Start*/
                formDeptAddVisible: false,
                formDeptEditVisible: false,
                formDeptRules: {
                    name: [
                        { required: true, message: '请输入组织架构名称', trigger: 'blur' }
                    ]
                },
                formDeptData: {
                    id:0,
                    name: '',
                    parentId: 0,
                    parentName: '',
                    type:1,
                },
                /*部门新增、编辑数据处理 End*/

                /*部门移动数据处理 Start*/
                formDeptMoveVisible: false,
                formDeptMoveRules: {
                    parentName: [
                        { required: true, message: '请选择上级组织架构', trigger: 'blur' }
                    ]
                },
                formDeptMoveData: {
                    id:0,
                    name: '',
                    parentId: 0,
                    parentName: '',
                    orgParentId:0,
                    orgParentName:'',
                    type:1,
                },
                /*部门移动数据处理 End*/
                splitClass:'',
                searchTxt: '',
                searchData: '',
            }
        },
        mounted() {
            this.getData()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.tableData.height = window.innerHeight - this.$refs.split.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableData.height = window.innerHeight - self.$refs.split.$el.offsetTop
                    }
                })
            },
            getData(){
                this.deptDataLoading = true
                this.$axios({
                    url: `/admin/depts`,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt)
                    data[0].isRoot = true
                    this.deptData = data
                    this.deptDataLoading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            mainDeptSelect(data) {
                this.searchTxt = '';
                this.searchData = '';
                this.tableData.body = [];
                this.selectDept = data;
                this.refresh()
            },
            //新增部门-start
            handleDeptAdd: function (node) {
                this.formDeptAddVisible = true;
                this.formDeptData = {
                    id: 0,
                    name: '',
                    parentId: node.data.id,
                    parentName: node.data.name,
                    type: 1,
                }
                if (this.$refs.formDeptData) this.$refs.formDeptData.resetFields()
            },
            handleDeptInput: function () {
              this.$confirm('开始导入最新组织机构数据吗?', '提示', {
                cancelButtonClass: 'btn-custom-cancel',
                type: 'success'
              }).then(() => {
                this.tableData.loading = true
                this.$axios({
                  url: `/relay/depts`,
                  method: 'post'
                }).then(res => {
                  this.$message({
                    message: '已经完成导入',
                    type: 'success'
                  });
                  this.tableData.loading = false
                  this.refresh()
                }).catch(err => {
                  this.tableData.loading = false
                  this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
              }).catch(() => {

              });
            },
            handleDeptAddSubmit: function () {
                this.$refs.formDeptData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.deptDataLoading = true
                            let pram = Object.assign({}, this.formDeptData)
                            this.$axios({
                                url: '/admin/dept',
                                method: 'post',
                                data: pram
                            }).then((res) => {
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['formDeptData'].resetFields()
                                this.formDeptAddVisible = false
                                this.getData()
                                this.deptDataLoading = false
                            })
                        })
                    }
                })
            },
            //新增部门-end

            //编辑部门-start
            handleDeptEdit: function (node) {
                this.formDeptEditVisible = true;
                this.formDeptData = {
                    id:node.data.id,
                    name:node.data.name,
                    parentId:node.parent.data.id,
                    parentName:node.parent.data.name,
                    type:node.data.type,
                }
                if (this.$refs.formDeptData) this.$refs.formDeptData.resetFields()
            },
            handleDeptEditSubmit: function () {
                this.$refs.formDeptData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.deptDataLoading = true
                            let pram = Object.assign({}, this.formDeptData)
                            this.$axios({
                                url: '/admin/dept',
                                method: 'put',
                                data: pram
                            }).then((res) => {
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['formDeptData'].resetFields()
                                this.formDeptEditVisible = false
                                this.getData()
                                this.deptDataLoading = false
                            })
                        })
                    }
                })
            },
            //编辑部门-end

            //移动部门-start
            handleDeptMove: function (node) {
                this.formDeptMoveData = {
                    id:node.data.id,
                    name: node.data.name,
                    parentId: 0,
                    parentName: '',
                    orgParentId:node.parent.data.id,
                    orgParentName:node.parent.data.name,
                    type:node.data.type,
                }
                this.formDeptMoveVisible = true;
            },
            parentDeptSelect(data) {
                this.formDeptMoveData.parentId = data.id
                this.formDeptMoveData.parentName = data.name
            },
            handleDeptMoveSubmit: function () {
                if (this.formDeptMoveData.parentId === 0) {
                    this.$message.error("请选择移动组织架构")
                    return
                }

                this.$confirm('确认提交吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.deptDataLoading = true
                    let pram = Object.assign({}, this.formDeptMoveData)
                    this.$axios({
                        url: '/admin/dept',
                        method: 'put',
                        data: pram
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("变更成功")
                            this.formDeptMoveVisible = false
                            this.$refs['formDeptMoveData'].resetFields()
                            this.getData()
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.deptDataLoading = false
                    })
                })
            },
            //移动部门-end

            //封存部门-start
            handleDeptDeleteSubmit:function(node){
                this.$confirm('确认封存该组织吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.deptDataLoading = true;
                    let para = Object.assign({}, node.data);
                    this.$axios({
                        url: '/admin/dept',
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("封存成功")
                            this.getData()
                        }else {
                            this.$message.error(res.msg)
                        }
                        this.deptDataLoading = false
                    })
                }).catch(() => {

                });
            },
            //封存部门-end

            getUserData(data, $state) {
                if(!data && !this.searchData){
                    if($state) $state.complete()
                    return
                }
                this.tableData.loading = true && this.$refs.infinite.isFirstLoad

                let url
                if(this.searchData){
                    url = `/admin/search/users/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData}&islove=true`
                }else{
                    url = `/admin/dept/${data.id}/users/${this.tableData.curPage}-${this.tableData.pageSize}`
                }

                this.$axios({
                    url: url,
                    method: 'get'
                }).then(res => {
                    let data = []
                    if (res.ctt.list.length > 0){
                        data.push(res.ctt.list)
                        if (data[0].length > 0 && data[0][0].id === 1){
                            data[0][0].isRoot = true
                        }
                    }
                    this.tableData.body = this.tableData.body.concat(res.ctt.list)
                    this.tableData.total = res.ctt.total
                    this.tableData.loading = false

                    if (res.ctt.list.length == 0){
                        if($state) $state.complete()
                    }
                    else{
                        if($state) $state.loaded()
                    }
                    this.$nextTick(() => {
                        this.tableHeight()
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            handleUserAdd: function (node) {
                this.$refs.editUser.handleUserAddByDept(node.data)
            },
            handleUserUnlockSubmit(index, row){
                this.$refs.editUser.handleUserUnlockSubmit(index, row)
            },
            handleUserBaseEdit(index, row){
                this.$refs.editUser.handleUserBaseEdit(index, row)
            },
            handleUserPasswordEdit(index, row){
                this.$refs.editUser.handleUserPasswordEdit(index, row)
            },
            handleAdminSubmit(index, row){
                this.$refs.editUser.handleAdminSubmit(index, row)
            },
            handleUserLockSubmit(index, row){
                this.$refs.editUser.handleUserLockSubmit(index, row)
            },
            handleUserLeaveSubmit(index, row){
                this.$refs.editUser.handleUserLeaveSubmit(index, row)
            },
            handleUserDeleteSubmit(index, row){
                this.$refs.editUser.handleUserDeleteSubmit(index, row)
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            refresh(){
                this.tableData.total = 0
                this.tableData.body = []
                this.tableData.curPage = 0
                this.$refs.infinite.stateChanger.reset()
            },
            clearSearchTxt(){
                this.searchData = ''
                this.refresh()
            },
            nextPage($state){
                this.tableData.curPage += 1
                this.getUserData(this.selectDept, $state)
            },
            search(){
                if(!this.searchTxt || !this.searchTxt.trim()) {
                    this.$message.success("请输入搜索关键词")
                    return
                }
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.searchTxt.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                this.searchData = this.searchTxt
                this.refresh()
            },
        }
    }
</script>

<style scoped>

</style>

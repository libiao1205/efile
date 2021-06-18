<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="drxx10"></app-svg>  用户管理</h1>
                </div>
                <div class="ib pd10">
                    <el-input placeholder="请输入关键字" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="clearSearchTxt">
                        <el-button @click="search" slot="append" icon="el-icon-search" ></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleUserAdd" plain>新增用户</el-button >
<!--                    <el-button type="primary" size="mini" icon="iconfont icon-caozuo-shangchuan-download" @click="handleUserInput" plain>  批量导入</el-button>-->
                </div>
                <!--<div class="fxr pd5">-->
                    <!--<el-checkbox v-model="isShowAll" @change="showAllChange">显示离职用户</el-checkbox>-->
                <!--</div>-->
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell"
                      :header-cell-style="{padding: '2px'}" :height="tableData.height">
                <el-table-column prop="name" label="用户姓名" align="left" width="180" >
                    <template slot-scope="scope">
                        <div>
                            <i class="iconfont icon-geren"></i>
                            <span style="padding-left: 5px;">{{scope.row.name}}</span>
                        </div>
                    </template>
                </el-table-column>
<!--                <el-table-column prop="name" label="所属公司" align="left" width="180" >-->
<!--                    <template slot-scope="scope">-->
<!--                        <div>-->
<!--                            <div v-for = "item in scope.row.departments">-->
<!--                                <div v-if="item.id === 1">-->
<!--                                    <svg class="icon" aria-hidden="true">-->
<!--                                        <use xlink:href="#icon-zhuye11"></use>-->
<!--                                    </svg>-->
<!--                                    {{item.name}}-->
<!--                                </div>-->
<!--                                <div v-else>-->
<!--                                    <svg class="icon" aria-hidden="true">-->
<!--                                        <use xlink:href="#icon-zhuye11"></use>-->
<!--                                    </svg>-->
<!--                                    {{ item.compName }}-->
<!--                                </div>-->
<!--                            </div>-->
<!--                        </div>-->
<!--                    </template>-->
<!--                </el-table-column>-->
                <el-table-column prop="departments" label="用户组织" align="center" >
                    <template slot-scope="scope">
                        <div v-for = "item in scope.row.departments">
                            <div v-if="item.id === 1">
                                <svg class="icon" aria-hidden="true">
                                    <use xlink:href="#icon-zhuye11"></use>
                                </svg>
                                {{item.name}}
                            </div>
                            <div v-else>
                                <div v-if="item.type === 1">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-zuzhiguanli-"></use>
                                    </svg>
                                    {{ item.name }}
                                </div>
                                <div v-else>
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-danweizuzhi"></use>
                                    </svg>
                                    {{ item.name }}
                                </div>
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" min-width="20%">
                    <template slot-scope="scope">
                        <div v-if="scope.row.status === 8"><span>离职</span></div>
                        <div v-else-if="scope.row.status === 4"><span>账户封禁</span></div>
                        <div v-else><span>正常</span></div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" min-width="30%">
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
                            <!--<el-tooltip class="item" effect="dark" content="权限复制" placement="bottom">-->
                                <!--<el-button v-if="!scope.row.isRoot"-->
                                           <!--class="btnEdit" type="text" size="mini"-->
                                           <!--icon="iconfont icon-jiaoseguanli"-->
                                           <!--@click="handleCopyUserPermission(scope.$index, scope.row)"-->
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
        </el-main>
        <edit-user ref="editUser" :add-user="refresh" :edit-user="refresh"></edit-user>
        <copy-user-permission ref="copyUserPermission"></copy-user-permission>
    </el-container>
</template>

<script>
    import EditUser from './EditUser'
    import CopyUserPermission from "./CopyUserPermission";
    export default {
        components:{CopyUserPermission, EditUser},
        name: 'user',
        data() {
            return {
                searchTxt: '',
                searchData: '',
                /*人员表格数据 Start*/
                tableData: {
                    height: 0,
                    loading: true,
                    body: [],
                    pageSize: 20,
                    total: 0,
                    curPage:0
                },
                /*人员表格数据 End*/
                isShowAll: false
            }
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.tableData.height = window.innerHeight - this.$refs.table.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableData.height = window.innerHeight - self.$refs.table.$el.offsetTop
                    }
                })
            },
            //共通数据处理-start
            getData($state) {
                this.tableData.loading = true && this.$refs.infinite.isFirstLoad
                let url
                if(this.searchData){
                    url = `/admin/search/users/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData}&islove=${!this.isShowAll}`
                }else{
                    url = `/admin/users/${this.tableData.curPage}-${this.tableData.pageSize}?islove=${this.isShowAll}`
                }

                this.$axios({
                    url: url,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt.list)
                    if (data[0].length > 0 && data[0][0].id === 1){
                        data[0][0].isRoot = true
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
            //检索界面
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
            handleUserAdd: function () {
                this.$refs.editUser.handleUserAdd()
            },

            handleUserInput: function () {
              this.$confirm('开始导入最新用户数据吗?', '提示', {
                cancelButtonClass: 'btn-custom-cancel',
                type: 'success'
              }).then(() => {
                this.tableData.loading = true
                this.$axios({
                  url: `/relay/users`,
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
            handleUserUnlockSubmit(index, row){
                this.$refs.editUser.handleUserUnlockSubmit(index, row)
            },
            //复制用户权限
            handleCopyUserPermission: function (index, row) {
                this.$refs.copyUserPermission.handleUserPermissionCopy(row)
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
            nextPage($state){
                this.tableData.curPage += 1
                this.getData($state)
            },
            //画面刷新
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
            showAllChange() {
                this.refresh()
            }
        }
    }
</script>

<style scoped>

</style>

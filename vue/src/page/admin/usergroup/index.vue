<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="lyq-qunguanli"></app-svg>  项目组</h1>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleSysAdminAdd" plain>新增用户组</el-button >
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <split-pane split="vertical" :className="splitClass" :defaultPercent=70  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
                <template slot="paneL">
                    <el-scrollbar>
                        <el-table ref="table" class="custom-table-cell"v-loading="userGroupData.loading" :data="userGroupData.body"
                                  :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" :height="userGroupData.height"
                                  highlight-current-row @current-change="onClickUserGroup">
                            <el-table-column prop="name" label="项目组名称" align="left" >
                                <template slot-scope="scope">
                                    <div>
                                        <i class="iconfont icon-geren"></i>
                                        <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center" min-width="10%">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="编辑项目组" placement="bottom">
                                            <el-button v-if="!scope.row.isRoot"
                                                       class="btnEdit" type="text" size="mini"
                                                       icon="iconfont icon-guanli_danse"
                                                       @click="handleUserGroupEditSubmit(scope.$index, scope.row)"
                                                       circle></el-button>
                                        </el-tooltip>
                                        <el-tooltip class="item" effect="dark" content="删除项目组" placement="bottom">
                                            <el-button v-if="!scope.row.isRoot"
                                                       class="btnDel" type="text" size="mini"
                                                       icon="iconfont icon-trash"
                                                       @click="handleUserGroupDeleteSubmit(scope.$index, scope.row)"
                                                       circle></el-button>
                                        </el-tooltip>
                                    </div>
                                </template>
                            </el-table-column>
                            <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                                <span slot="no-more"></span>
                            </infinite-loading>
                        </el-table>
                    </el-scrollbar>
                </template>
                <template slot="paneR">
                    <el-scrollbar >
                        <el-table ref="userTable" v-loading="userData.loading" :data="userData.body" highlight-current-row
                                  :cell-style="{padding:'5px'}" class="custom-table-cell"
                                  :header-cell-style="{padding: '2px'}" :height="userGroupData.height">
                            <el-table-column prop="name" label="用户名字" align="left">
                                <template slot-scope="scope">
                                    <div>
                                        <i class="iconfont icon-geren"></i>
                                        <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center" width="60px">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="删除用户" placement="right">
                                            <el-button class="btnDel" type="text" size="mini"
                                                       icon="iconfont icon-yonghushanchu"
                                                       @click="deleteUserByGroup(scope.row.id)"
                                                       circle></el-button>
                                        </el-tooltip>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-scrollbar>
                </template>
            </split-pane>

        </el-main>
        <edit-user-group ref="editUserGroup" :add-user="refresh" :edit-user="refresh"></edit-user-group>
    </el-container>
</template>

<script>
     import EditUserGroup from './EditUserGroup'
    import splitPane from 'vue-splitpane'
    export default {
        components:{EditUserGroup,splitPane},
        name: 'usergroup',
        data() {
            return {
                splitClass:'',
                /*用户组表格数据 Start*/
                userGroupData: {
                    height: 0,
                    loading: true,
                    body: [],
                    pageSize: 1000,
                    total: 0,
                    curPage:0
                },
                /*人员表格数据 End*/
                userData: {
                    loading: false,
                    groupId:0,
                    body: [],
                    pageSize: 1000,
                    total: 0,
                    curPage:1
                },
            }
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.userGroupData.height = window.innerHeight - this.$refs.table.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.userGroupData.height = window.innerHeight - self.$refs.table.$el.offsetTop
                    }
                })
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            onClickUserGroup(currentRow, oldCurrentRow){
                let id = 0;
                if(currentRow!== null){
                    id = currentRow.id
                }else{
                    id = oldCurrentRow.id
                }
                this.printUser(id)
            },
            //画面刷新
            refresh(){
                this.userGroupData.total = 0
                this.userGroupData.body = []
                this.userGroupData.curPage = 0
                this.$refs.infinite.stateChanger.reset()
                this.userData.total = 0
                this.userData.groupId = 0
                this.userData.body = []
                this.userData.curPage = 1
            },
            nextPage($state){
                this.userGroupData.curPage += 1
                this.printUserGroup($state)
            },
            //共通数据处理-start
            printUserGroup($state) {
                this.userGroupData.loading = true && this.$refs.infinite.isFirstLoad
                let url = `/admin/groups/${this.userGroupData.curPage}-${this.userGroupData.pageSize}`
                this.$axios({
                    url: url,
                    method: 'get'
                }).then(res => {
                    this.userGroupData.body = this.userGroupData.body.concat(res.ctt.list)
                    this.userGroupData.total = res.ctt.total
                    this.userGroupData.loading = false

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
            printUser(id){
                this.userData.loading = true
                this.$axios({
                    url: `/admin/dept/${id}/users/${this.userData.curPage}-${this.userData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.userData.groupId = id
                    this.userData.body = res.ctt.list
                    this.userData.total = res.ctt.total
                    this.userData.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //共通数据处理-end

            //新增用户组
            handleSysAdminAdd: function () {
                this.$refs.editUserGroup.handleUserGroupAdd()
            },
            //编辑用户组
            handleUserGroupEditSubmit(index, row){
                this.userData.loading = true
                this.$axios({
                    url: `/admin/dept/${row.id}/users/${this.userData.curPage}-${this.userData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.userData.groupId = row.id
                    this.userData.body = res.ctt.list
                    this.userData.total = res.ctt.total
                    this.userData.loading = false
                    this.$refs.editUserGroup.handleUserGroupEdit(row, this.userData)
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //删除用户组
            handleUserGroupDeleteSubmit(index, row){
                this.$confirm('确认删除该用户组?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/group/${para.id}`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message({
                                message: '用户组已删除',
                                type: 'success'
                            });
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.refresh()
                    })
                }).catch(() => {

                });
            },
            //删除组成员
            deleteUserByGroup(id){
                this.$confirm('确认从当前用户组中删除该成员?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.userData.loading = true
                    this.$axios({
                        url: `/admin/group/${this.userData.groupId}/user/${id}`,
                        method: 'delete'
                    }).then(res => {
                        this.userData.loading = false
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.printUser(this.userData.groupId)
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });
            }
        }
    }
</script>

<style scoped>

</style>

<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="guanlirenyuan"></app-svg>  管理人员设定</h1>
                </div>
                <!--<div class="ib pd10">-->
                    <!--<el-input placeholder="请输入管理员姓名" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="clearSearchTxt">-->
                        <!--<el-button @click="search" slot="append" icon="el-icon-search" ></el-button>-->
                    <!--</el-input>-->
                <!--</div>-->
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleSysAdminAdd" plain>新增系统管理员</el-button >
                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleFolderAdminAdd" plain>新增文件夹管理员</el-button >
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <split-pane split="vertical" :className="splitClass" :defaultPercent=70  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
                <template slot="paneL">
                    <el-scrollbar v-show="!searchData.isSearch">
                        <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                                  @current-change="getFolders"
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
<!--                            <el-table-column prop="name" label="所属公司" align="left" width="180" >-->
<!--                                <template slot-scope="scope">-->
<!--                                    <div>-->
<!--                                        <div v-for = "item in scope.row.departments">-->
<!--                                            <div v-if="item.id === 1">-->
<!--                                                <svg class="icon" aria-hidden="true">-->
<!--                                                    <use xlink:href="#icon-zhuye11"></use>-->
<!--                                                </svg>-->
<!--                                                {{item.name}}-->
<!--                                            </div>-->
<!--                                            <div v-else>-->
<!--                                                <svg class="icon" aria-hidden="true">-->
<!--                                                    <use xlink:href="#icon-zhuye11"></use>-->
<!--                                                </svg>-->
<!--                                                {{ item.compName }}-->
<!--                                            </div>-->
<!--                                        </div>-->
<!--                                    </div>-->
<!--                                </template>-->
<!--                            </el-table-column>-->
                            <el-table-column prop="departments" label="用户组织" align="center">
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
                            <el-table-column prop="status" label="管理目录" min-width="50%">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.adminStatus === 2"><span>文件夹管理员</span></div>
                                    <div v-else><span>系统管理员</span></div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="status" label="状态" min-width="30%">
                                <template slot-scope="scope">
                                    <div v-if="scope.row.status === 8"><span>离职</span></div>
                                    <div v-else-if="scope.row.status === 4"><span>账户封禁</span></div>
                                    <div v-else><span>正常</span></div>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center" min-width="30%">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="删除管理员权限" placement="bottom">
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
                    </el-scrollbar>
                </template>
                <template slot="paneR">
                    <el-scrollbar >
                        <el-table ref="folderTable" v-loading="folderData.loading" :data="folderData.body" highlight-current-row
                                  :cell-style="{padding:'5px'}" class="custom-table-cell"
                                  :header-cell-style="{padding: '2px'}" :height="tableData.height">
                            <el-table-column prop="folderName" label="文件夹名称" align="left" width="180" >
                                <template slot-scope="scope">
                                    <div>
                                        <app-svg iconClass="wenjianjia"></app-svg>
                                        <span style="padding-left: 5px;">{{scope.row.folderName}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" align="center" min-width="30%">
                                <template slot-scope="scope">
                                    <div>
                                        <el-tooltip class="item" effect="dark" content="删除管理员权限" placement="bottom">
                                            <el-button class="btnDel" type="text" size="mini"
                                                       icon="iconfont icon-yonghushanchu"
                                                       @click="deleteFolderByAdmin(scope.row.id,scope.row.adminId)"
                                                       circle></el-button>
                                        </el-tooltip>
                                    </div>
                                </template>
                            </el-table-column>
                            <!--<infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">-->
                                <!--<span slot="no-more"></span>-->
                            <!--</infinite-loading>-->
                        </el-table>
                    </el-scrollbar>
                </template>
            </split-pane>

        </el-main>
        <edit-sys-admin ref="editSysAdmin" :add-user="refresh" :edit-user="refresh"></edit-sys-admin>
        <edit-folder-admin ref="editFolderAdmin" :add-user="refresh" :edit-user="refresh"></edit-folder-admin>
    </el-container>
</template>

<script>
    import EditSysAdmin from './EditSysAdmin'
    import EditFolderAdmin from './EditFolderAdmin'
    import splitPane from 'vue-splitpane'
    export default {
        components:{EditSysAdmin,EditFolderAdmin,splitPane},
        name: 'user',
        data() {
            return {
                splitClass:'',
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
                folderData: {
                    loading: false,
                    body: [],
                    pageSize: 20,
                    total: 0,
                    curPage:0
                },
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
                    url = `/admin/search/users/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData}&islove=true`
                }else{
                    url = `/admin/adminusers/${this.tableData.curPage}-${this.tableData.pageSize}`
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
            nextPage($state){
                this.tableData.curPage += 1
                this.getData($state)
            },

            //添加管理员
            handleSysAdminAdd: function () {
                this.$refs.editSysAdmin.handleAdminAdd()
            },
            //添加管理员
            handleFolderAdminAdd: function () {
                this.$refs.editFolderAdmin.handleAdminAdd()
            },
            //删除管理员
            handleUserDeleteSubmit(index, row){
                this.$confirm('确认删除该用户的管理员权限?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row.admin);
                    this.$axios({
                        url: `/admin/admin/${para.id}`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message({
                                message: '用户状态已更新',
                                type: 'success'
                            });
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.refresh()
                        this.findFolderList(para.id)
                    })
                }).catch(() => {

                });
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
            clearSearchTxt(){
                this.searchData = ''
                this.refresh()
            },
            //画面刷新
            refresh(){
                this.tableData.total = 0
                this.tableData.body = []
                this.tableData.curPage = 0
                this.$refs.infinite.stateChanger.reset()
                this.folderData.total = 0
                this.folderData.body = []
                this.folderData.curPage = 0
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            getFolders(currentRow, oldCurrentRow){
                let id = 0;
                if(currentRow!== null){
                    id = currentRow.admin.id
                }else{
                    id = oldCurrentRow.admin.id
                }
                this.findFolderList(id)
            },
            findFolderList(id){
                this.folderData.loading = true
                this.$axios({
                    url: `/admin/userFolders/${id}`,
                    method: 'get'
                }).then(res => {
                    this.folderData.body = res.ctt.list
                    this.folderData.total = res.ctt.total
                    this.folderData.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            deleteFolderByAdmin(id,adminid){
                this.$confirm('确认删除该文件夹的管理员权限?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.folderData.loading = true
                    this.$axios({
                        url: `/admin/deleteFolderByAdmin/${id}`,
                        method: 'delete'
                    }).then(res => {
                        this.folderData.loading = false
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.findFolderList(adminid)
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

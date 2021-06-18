<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="web-xietongwendang"></app-svg>  元数据设置</h1>
                </div>
                <div class="ib pd10">
                    <el-input placeholder="请输入关键字" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="clearSearchTxt">
                        <el-button @click="search" slot="append" icon="el-icon-search" ></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleAddMetaData" plain>新增元数据</el-button >
                    <!-- <el-button type="primary" size="mini" icon="iconfont icon-caozuo-shangchuan-download" @click="handleUserInput" plain>  批量导入</el-button> -->
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell"
                      :header-cell-style="{padding: '2px'}" :height="tableData.height">
                <el-table-column prop="id" label="id" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.id}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="docType" label="文档类型" align="left">
                    <template slot-scope="scope">
                        <div>
                            <!-- <span style="padding-left: 5px;">{{scope.row.docType}}</span> -->
                            <span style="padding-left: 5px;">{{formatDocType(scope.row.docType)}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="code" label="字段编码" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.code}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="字段名称" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.name}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="seq" label="排序" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.seq}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="dataType" label="数据类型" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{formatDataType(scope.row.dataType)}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="size" label="长度" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.size}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="notnull" label="非空" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{formatNum2Str(scope.row.notnull)}}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="solrId" label="solr对应字段" align="left">
                    <template slot-scope="scope">
                        <div>
                            <span style="padding-left: 5px;">{{scope.row.solrName}}</span>
                        </div>
                    </template>
                </el-table-column>
                <!-- min-width="30%" -->
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <div>
                            <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
                                <el-button
                                    class="btnEdit" type="text" size="mini"
                                    icon="iconfont icon-edit-file" circle
                                    @click="handleEditItem(scope.$index, scope.row)"
                                ></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                                <el-button
                                    class="btnDel" type="text" size="mini"
                                    icon="iconfont icon-trash" circle
                                    @click="handleDelItem(scope.$index, scope.row)"
                                ></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="无效化" placement="bottom" v-if="scope.row.status === 1">
                                <el-button
                                    class="btnLock" type="text" size="mini"
                                    icon="iconfont icon-dongjie" circle
                                    @click="handleSetValidItem(scope.$index, scope.row)"
                                ></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="有效化" placement="bottom" v-if="scope.row.status === 9">
                                <el-button
                                    class="btnLock" type="text" size="mini"
                                    icon="iconfont icon-jiechu" circle
                                    @click="handleSetValidItem(scope.$index, scope.row)"
                                ></el-button>
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
        <edit-meta-data ref="editMetaData"></edit-meta-data>
        <copy-user-permission ref="copyUserPermission"></copy-user-permission>
    </el-container>
</template>

<script>
    import Utils from '../../../util/utils';
    import {dataTypes} from '../../../util/initData';
    import EditUser from '../user/EditUser'
    import CopyUserPermission from "../user/CopyUserPermission";
    import EditMetaData from './EditMetaData'
    export default {
        components:{CopyUserPermission, EditUser, EditMetaData},
        name: 'metadata',
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
                isShowAll: false,
                docTypes: [],
            }
        },
        mounted() {
            this.initDocTypes();
            Utils.$on('refreshMetadata', (val) => {
                this.refresh();
            });
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
                    url = `/external/metadata/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData}`
                }else{
                    url = `/external/metadata/${this.tableData.curPage}-${this.tableData.pageSize}?name=`
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
                // console.log('refresh');
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
            },
            handleEditItem(index, item) {
                this.$refs.editMetaData.dialogType = 'edit';
                this.$refs.editMetaData.visible = true;
                this.$refs.editMetaData.docTypes = this.docTypes;
                const {dataProperty, solrStructure, docType, ...formItem} = item;
                this.$refs['editMetaData'].form = {
                    ...formItem,
                    docType: String(docType),
                    notnull: item.notnull === 1,
                };
            },
            handleDelItem(index, item) {
                this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    this.$axios({
                        method: 'delete',
                        url: `/external/metadata`,
                        data: {
                            id: item.id,
                            // status: 0,
                        },
                    }).then((res) => {
                        this.refresh();
                        this.$message.success('删除成功');
                    }).catch(err => {
                        this.$message.error(`删除数据失败，失败码：${err.response.status}`);
                    });
                }).catch(() => {
                    // this.$message({
                    //     type: 'info',
                    //     message: '已取消删除'
                    // });
                });
            },
            handleSetValidItem(index, item) {
                this.$confirm(`此操作将该数据置为${item.status === 1 ? '无效' : '有效'}, 是否继续?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    this.$axios({
                        method: 'put',
                        url: `/external/metadataInvalid`,
                        data: {
                            id: item.id,
                            status: item.status === 1 ? 9 : 1,
                        },
                    }).then((res) => {
                        // console.log(res);
                        if (res.c === 0) {
                            this.refresh();
                            this.$message.success('设置成功');
                        }

                    }).catch(err => {
                        this.$message.error(`设置数据失败，失败码：${err.response.status}`);
                    });
                }).catch(() => {
                    // this.$message({
                    //     type: 'info',
                    //     message: '已取消删除'
                    // });
                });
            },
            handleAddMetaData() {
                this.$refs.editMetaData.dialogType = 'create';
                this.$refs.editMetaData.docTypes = this.docTypes;
                this.$refs.editMetaData.visible = true;
            },
            initDocTypes() {
                this.$axios({
                    method: 'get',
                    url: `/external/metadataType`,
                }).then((res) => {
                    this.docTypes = res.ctt;
                });
            },
            formatNum2Str(num) {
                if (num === 1) {
                    return '是';
                } else {
                    return '否';
                }
            },
            formatDocType(num) {
                let item = this.docTypes.find((i) => parseInt(i.value) === num);
                if (item && item.comment) {
                    return item.comment;
                }
                return num;
            },
            formatDataType(num) {
                let item = dataTypes.find((i) => parseInt(i.value) === num);
                if (item) {
                    return item.label;
                }
                return num;
            },
        }
    }
</script>

<style scoped>

</style>

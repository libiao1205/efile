<template>
    <div class="database-container">
        <el-container>
            <el-header height="79" class="pd0">
                <div class="app-title fxb bb1 pd10">
                    <div class="fxr fnt-s20 pd10">
                        <h1><app-svg iconClass="shezhi1"></app-svg>  数据源管理</h1>
                    </div>
                    <div class="ib pd10">
                        <el-input placeholder="请输入关键字" class="input-with-select" v-model="searchTxt" @keyup.enter.native="search" clearable @clear="clearSearchTxt">
                            <el-button @click="search" slot="append" icon="el-icon-search" ></el-button>
                        </el-input>
                    </div>
                </div>
                <div class="fxb bb1">
                    <div class="fxr pd5">
                        <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="handleAddDataBase" plain>新增数据源</el-button>
                    </div>
                </div>
            </el-header>

            <el-main class="sys-content pd0">
                <el-table
                    ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                    :cell-style="{padding:'5px'}" class="custom-table-cell"
                    :header-cell-style="{padding: '2px'}" :height="tableData.height"
                >
                    <el-table-column prop="id" label="数据源别名" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.aliasName}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="id" label="数据源名称" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.dbName}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="dataDrive" label="数据驱动" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.driverName}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="metaDataUrl" label="数据源URL" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.ip}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="isSync" label="是否已经同步" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.syncTime ? '是' : '否'}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="isEnable" label="是否启用" align="left">
                        <template slot-scope="scope">
                            <div>
                                <span style="padding-left: 5px;">{{scope.row.status === 1 ? '是' : '否'}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" min-width="50%">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="结构同步" placement="bottom">
                                    <el-button
                                        class="btnAdd" type="text" size="mini"
                                        icon="iconfont icon-caozuo-shangchuan-upload" circle
                                        @click="handleSyncItem(scope.$index, scope.row)"
                                    ></el-button>
                                </el-tooltip>
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
            <edit-database ref="editDatabase"></edit-database>
            <sync-database ref="syncDatabase"></sync-database>
        </el-container>
    </div>
</template>

<script>
import Utils from '../../../util/utils';
import EditDatabase from './EditDatabase';
import SyncDatabase from './SyncDatabase';
export default {
    name: 'database',
    components:{
        EditDatabase,
        SyncDatabase,
    },
    data() {
        return {
            searchTxt: '',
            searchData: '',
            tableData: {
                height: 0,
                loading: true,
                body: [],
                pageSize: 20,
                total: 0,
                curPage: 0,
            },
            isShowAll: true,
        };
    },
    mounted() {
        Utils.$on('refreshDb', (val) => {
            this.refresh();
        });
    },
    methods: {
        tableHeight(){
            this.$nextTick(function () {
                this.tableData.height = window.innerHeight - this.$refs.table.$el.offsetTop;
                let self = this;
                window.onresize = function() {// 监听窗口大小变化
                    self.tableData.height = window.innerHeight - self.$refs.table.$el.offsetTop
                }
            })
        },
        clearSearchTxt() {
            this.searchData = '';
            this.refresh();
        },
        search() {
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
        refresh(){
            this.tableData.total = 0
            this.tableData.body = []
            this.tableData.curPage = 0
            this.$refs.infinite.stateChanger.reset()
        },
        nextPage($state){
            this.tableData.curPage += 1
            this.getData($state)
        },
        getData($state) {
            this.tableData.loading = true && this.$refs.infinite.isFirstLoad
            let url
            if(this.searchData){
                    url = `/external/dbs/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData}`;
                }else{
                    url = `/external/dbs/${this.tableData.curPage}-${this.tableData.pageSize}?name=`;
                }

            this.$axios({
                url: url,
                method: 'get'
            }).then(res => {
                let data = []
                data.push(res.ctt)
                if (data[0].length > 0 && data[0][0].id === 1){
                    data[0][0].isRoot = true
                }
                this.tableData.body = this.tableData.body.concat(res.ctt)
                this.tableData.total = res.ctt.total
                this.tableData.loading = false

                if (res.ctt.length == 0){
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
        handleEditItem(index, item) {
            this.$refs.editDatabase.dialogType = 'edit';
            this.$refs.editDatabase.visible = true;
            const {comment, mapperPackage, mapperPath, syncTime, tables, ...formItem} = item;
            this.$refs['editDatabase'].form = {
                ...formItem,
                port: parseInt(formItem.port),
                status: formItem.status === 1,
            };
        },
        handleSetValidItem(index, item) {
            this.$confirm(`此操作将该数据置为${item.status === 1 ? '无效' : '有效'}, 是否继续?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                this.$axios({
                    method: 'put',
                    url: `/external/dbInvalid`,
                    data: {
                        id: item.id,
                        status: item.status === 1 ? 9 : 1,
                    },
                }).then((res) => {
                    // console.log(res);
                    this.refresh();
                    this.$message.success('设置成功');
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
        handleDelItem(index, item) {
            this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                this.$axios({
                    method: 'delete',
                    url: `/external/db`,
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
        handleAddDataBase() {
            // console.log(this.$refs.editMetaData);
            this.$refs.editDatabase.dialogType = 'create';
            this.$refs.editDatabase.visible = true;
        },
        handleSyncItem(index, item) {
            this.$refs.syncDatabase.visible = true;
            let date = new Date(item.syncTime);
            this.$refs.syncDatabase.form = {
                id: item.id,
                name: item.dbName,
                dateTime: `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`,
                status: item.syncTime ? '成功' : '失败',
            };
        },
    },
}
</script>

<style lang="scss">

</style>

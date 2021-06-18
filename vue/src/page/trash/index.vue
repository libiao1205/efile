<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr">
                    <el-button-group>
                        <el-button size="small" icon="el-icon-arrow-left" @click="curFolderAction"></el-button>
                        <el-button size="small" @click="nextFolderAction"><i class="el-icon-arrow-right el-icon--right"></i></el-button>
                    </el-button-group>
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="pl10">
                        <el-breadcrumb-item>回收站</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="ib">
                    <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="clearSearchTxt">
                        <el-button @click="search" slot="append" icon="el-icon-search"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button size="mini" icon="iconfont icon-shuaxin" @click="refresh"> 刷新</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon-fanhui1" @click="handleMultiNormalSubmit" plain>  文档恢复</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon-shanchu" @click="handleMultiDeleteSubmit" plain>  彻底删除</el-button>
                </div>
            </div>
        </el-header>
        <el-main class="sys-content pd0">
            <el-table ref="table" v-loading="trashTable.loading" :data="trashTable.body" @selection-change="handleSelectionChange" highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell"
                      :header-cell-style="{padding: '2px'}" :height="trashTable.height">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <el-table-column prop="name" label="文件名" width="350px" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div v-if="scope.row.docType === 20">
                                <img style="width: 20px;height: auto" :src="viewImageUrl" alt=""/>
                                <b>{{scope.row.name}}</b>
                            </div>
                            <div v-else>
                                <div v-if="scope.row.kind === 'folder'">
                                    <app-svg iconClass="wenjianjia3"></app-svg>
                                    <a href="#" target="_self"  style="margin-left:10px;text-decoration-line:none;" @click="nodeClick(scope.row)"><b>{{scope.row.name}}</b></a>
                                </div>
                                <div v-else>
                                    <div>
                                        <app-svg :iconText="scope.row.suffix"></app-svg>
                                        <b>{{scope.row.name}}.{{scope.row.suffix}}</b>
                                    </div>
                                </div>
                            </div>
                        </template></el-table-column>
                    <el-table-column label="文件原始路径" align="left" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <p>{{scope.row.fullName}}</p>
                        </template>
                    </el-table-column>
                    <el-table-column label="删除日期" align="center" width="200px">
                        <template slot-scope="scope">
                            <el-tooltip class="item" effect="dark" placement="bottom">
                                <div slot="content">{{dateFormat(scope.row.deleteDate)}}</div>
                                <p>{{dateShortFormat(scope.row.deleteDate)}}</p>
                            </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column label="删除者" align="center" width="120px">
                        <template slot-scope="scope">
                            <p>{{scope.row.deleteUserName}}</p>
                        </template>
                    </el-table-column>
                    <el-table-column label="文件大小" align="center" width="120px">
                        <template slot-scope="scope">
                            <div v-if="scope.row.kind === 'folder'">
                            </div>
                            <div v-else>
                                <p>{{scope.row.size}}</p>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="120px">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="文档恢复" placement="bottom">
                                    <el-button class="btnAdd" type="text" size="mini" icon="iconfont icon-fanhui1" @click="handleToNormal(scope.$index, scope.row)"  circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="彻底删除" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini" icon="iconfont icon-trash"  @click="handleToDelete(scope.$index, scope.row)"  circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                    <span slot="no-more"></span>
                </infinite-loading>
            </el-table>
        </el-main>
    </el-container>
</template>

<script>
    import {mapActions } from 'vuex'
    export default {
        name: "trash",
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                /*表格数据 Start*/
                trashTable: {
                    loading: true,
                    body: [],
                    height: 0,
                    pageSize: 20,
                    total: 0,
                    curPage: 0,
                    multipleSelection: [],
                },
                /*表格数据 End*/
                searchData: '',
                searchTxt: '',
            }
        },
        mounted() {
            this.tableHeight()
        },
        methods: {
            ...mapActions({
                curFolderAction: 'folderHistory/curFolder',
                nextFolderAction: 'folderHistory/nextFolder',
            }),
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.trashTable.height = window.innerHeight - this.$refs.table.$el.offsetTop - 60;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.trashTable.height = window.innerHeight - self.$refs.table.$el.offsetTop - 60
                    }
                })
            },
            // 获取table数据
            getData($state) {
                this.trashTable.loading = this.$refs.infinite.isFirstLoad

                let url

                if(this.searchData){
                    url = `/user/search/trash/${this.trashTable.curPage}-${this.trashTable.pageSize}?value=${this.searchData}`
                }else{
                    url = `/user/trash/${this.trashTable.curPage}-${this.trashTable.pageSize}`
                }


                this.$axios({
                    url: url,
                    method: 'get'
                }).then(res => {
                    this.trashTable.body = this.trashTable.body.concat(res.ctt.list)
                    this.trashTable.total = res.ctt.total
                    this.trashTable.loading = false

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
                this.trashTable.loading = false
            },
            //画面刷新
            refresh(){
                this.trashTable.total = 0
                this.trashTable.body = []
                this.trashTable.curPage = 0
                this.$refs.infinite.stateChanger.reset()
            },
            clearSearchTxt(){
                this.searchData = ''
                this.refresh()
            },
            //检索
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
            //画面表格多选赋值操作
            handleSelectionChange(val) {
                this.trashTable.multipleSelection = val;
            },
            //日期格式化
            dateFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm");
            },
            //日期格式化
            dateShortFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("MM月DD日 HH:mm");
            },
            //批量恢复
            handleMultiNormalSubmit:function(){
                if (this.trashTable.multipleSelection.length === 0){
                    this.$message.error("没有选择需要恢复的文件，请重新选择！")
                    return
                }
                this.$confirm('确认恢复这些文件吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.trashTable.loading = true;
                    let para = this.trashTable.multipleSelection;
                    this.$axios({
                        url: `user/trashs`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '恢复成功',
                            type: 'success'
                        });
                        this.refresh()
                        this.trashTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //恢复
            handleToNormal: function (index, row) {
                this.$confirm('是否恢复该记录?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.trashTable.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `user/trash`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("数据已恢复")
                            this.refresh()
                            this.$emit('lay_addFolder', res)
                        }else {
                            this.$message.error(res.msg)
                        }
                        this.trashTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //文件批量彻底删除
            handleMultiDeleteSubmit: function () {
                if (this.trashTable.multipleSelection.length === 0){
                    this.$message.error("没有选择需要删除的文件，请重新选择！")
                    return
                }
                this.$confirm('确认删除这些文件吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.trashTable.loading = true;
                    let para = this.trashTable.multipleSelection;
                    this.$axios({
                        url: `user/trashs`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.refresh();
                        this.trashTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //彻底删除
            handleToDelete: function (index, row) {
                this.$confirm('彻底该记录?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.trashTable.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `user/trash`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("已经彻底删除")
                            this.refresh()
                        }else {
                            this.$message.error(res.msg)
                        }
                        this.trashTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            nextPage($state){
                this.trashTable.curPage += 1
                this.getData($state)
            }
        }
    }
</script>

<style scoped>

</style>

<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="gongxiangwenjianjia"></app-svg>  外部链接</h1>
                </div>
                <div class="ib">
                    <!--<el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="refresh">-->
                        <!--<el-button @click="search" slot="append" icon="el-icon-search"></el-button>-->
                    <!--</el-input>-->
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="iconfont icon-caozuo-shangchuan-download"  @click="$refs.outPutCSV.handleOutputCSV()"  plain>  导出记录</el-button>
                </div>
                <div class="fxr pd5">
                    <el-checkbox v-model="onlyShowValid" @change="onlyValid">只表示有效外链</el-checkbox>
                </div>
            </div>

        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="tableData.height">
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <template v-for="res in props.row.resources">
                                <el-row style="padding-left: 25px" >
                                    <el-col :span="1" align="right">
                                        <div v-if="res.kind === 'folder'">
                                            <app-svg iconClass="wenjianjia3"></app-svg>
                                        </div>
                                        <div v-else>
                                            <app-svg :iconText="res.suffix"></app-svg>
                                        </div>
                                    </el-col>
                                    <el-col :span="7">
                                        <P style="line-height: 30px; font-size:15px;padding-left: 3px;"><b>{{res.name}}.{{res.suffix}}</b></P>
                                    </el-col>
                                </el-row>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column type="selection" min-width="10%"></el-table-column>
                    <el-table-column prop="name" label="外链">
                        <template slot-scope="props">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-gongxiangwenjianjia"></use>
                            </svg>
                            {{props.row.name}}
                        </template>
                    </el-table-column>
                    <el-table-column label="发布者" align="center" min-width="10%">
                        <template slot-scope="scope">
                            <p>{{scope.row.user.name}}</p>
                        </template>
                    </el-table-column>
                    <el-table-column label="有效期" align="center" min-width="10%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.days === 0"><p>无期限</p></div>
                            <div v-else><p>{{scope.row.days}}天</p></div>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间"  align="center" min-width="15%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.days === 0"></div>
                            <div v-else><p>{{dateFormat(scope.row.endDate)}}截至</p></div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="15%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.status === 'EXPIRE'">
                                <p>已失效</p>
                            </div>
                            <div v-else-if="scope.row.status === 'DELETE'">
                                <p>已删除</p>
                            </div>
                            <div v-else>
                                <el-tooltip class="item" effect="dark" content="外链删除" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini" icon="iconfont icon--duankailianjie"  @click="handleDeleteSubmit(scope.$index, scope.row)"  circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <outPutCSV ref="outPutCSV"></outPutCSV>
    </el-container>
</template>

<script>
    import OutPutCSV from './OutPutCSV'
    export default {
        components : {OutPutCSV},
        name: "link",
        data() {
            return {
                onlyShowValid:true,
                tableData: {
                    height: 0,
                    loading: true,
                    body: []
                },
                formRules: {},
                formData: {},
                addFormVisible: false,
                editFormVisible: false,
                paginationData: {
                    pageSize: 1000,
                    total: 0,
                    curPage: 1
                },
                multipleSelection: [],

                searchTxt: '',
            }
        },
        mounted() {
            this.getData()
            this.tableHeight()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.tableData.height = window.innerHeight - this.$refs.table.$el.offsetTop - 200;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableData.height = window.innerHeight - self.$refs.table.$el.offsetTop - 200
                    }
                })
            },
            //画面刷新
            refresh(){
                this.getData()
            },
            // 获取table数据
            getData() {
                this.tableData.loading = true
                this.$axios({
                    url: `/admin/dept/0/links/${this.paginationData.curPage}-${this.paginationData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    if (this.onlyShowValid){
                        let valid = [];
                        if (res.ctt.list.length > 0) {
                            for( let i = 0; i < res.ctt.list.length; i++){
                                //user等于空证明是root用户分享的，隐藏掉，否则会报错
                                if (res.ctt.list[i].status === 'NORMAL' && res.ctt.list[i].user != null){
                                    valid.push(res.ctt.list[i])
                                }
                            }
                        }else {
                            valid = res.ctt.list;
                        }
                        this.tableData.body = valid
                        this.paginationData.total = res.ctt.total
                        this.tableData.loading = false
                    }else{
                        this.tableData.body = res.ctt.list
                        this.paginationData.total = res.ctt.total
                        this.tableData.loading = false
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //检索
            search(){
                if(!this.searchTxt || !this.searchTxt.trim()) {
                    this.$message.success("请输入搜索关键词")
                    return
                }
                // let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                // if(pattern.test(this.searchTxt.trim())){
                //     this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                //     return
                // }
                // this.tableData.loading = true
                // this.$axios({
                //     url: `/admin/file/deleted/search/${this.paginationData.curPage}-${this.paginationData.pageSize}?name=${this.searchTxt.trim()}`,
                //     method: 'get'
                // }).then(res => {
                //     this.tableData.body = res.ctt.list
                //     this.paginationData.total = res.ctt.total
                //     this.tableData.loading = false
                // }).catch(err => {
                //     this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                // })
            },
            sizeChange(num){
                this.paginationData.pageSize = num
                this.getData()
            },
            pageChange(num){
                this.paginationData.curPage = num
                this.getData()
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            onlyValid(val){
                this.onlyShowValid = val
                this.getData()
            },
            //日期格式化
            dateShortFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("MM月DD日 HH:mm");
            },
            //日期格式化
            dateFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("YYYY年MM月DD日");
            },
            //删除
            handleDeleteSubmit: function (index, row) {
                this.$confirm('确认删除此外链吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tableData.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `admin/link`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                    })
                }).catch(() => {

                });
            },

        },
    }
</script>

<style scoped>

</style>

<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="weibiaoti-"></app-svg>  回收站</h1>
                </div>
                <div class="ib">
                    <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable @clear="refresh">
                        <el-button @click="search" slot="append" icon="el-icon-search"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <!--<el-button type="primary" size="mini" icon="iconfont icon-system-extension" @click="$refs.editDeptAndTimeCondition.handleCondition()" plain>  设置查询条件</el-button>-->
                    <el-button type="primary" size="mini" icon="iconfont icon-fanhui1" @click="handleMultiRollback" plain>  文档恢复</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon-shanchu" @click="handleMultiDelete" plain>  彻底删除</el-button>
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell" @selection-change="handleSelectionChange"
                          :header-cell-style="{padding: '2px'}" :height="tableData.height">
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
                                        <a href="#" @click="handlePreview(scope.$index, scope.row)" style="margin-left: 10px;text-decoration-line:none;"><b>{{scope.row.name}}.{{scope.row.suffix}}</b></a>
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
                    <el-table-column label="文件大小" align="center"  width="120px">
                        <template slot-scope="scope">
                            <div v-if="scope.row.kind === 'folder'">
                            </div>
                            <div v-else>
                                <p>{{scope.row.size}}</p>
                            </div>
                        </template></el-table-column>
                    <el-table-column label="操作" align="center" width="120px">
                        <template slot-scope="scope">
                            <el-tooltip class="item" effect="dark" content="删除文档恢复" placement="bottom">
                                <el-button class="btnAdd" type="text" size="mini" icon="iconfont icon-fanhui1"  @click="handleRollback(scope.$index, scope.row)"   circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="彻底删除" placement="bottom">
                                <el-button class="btnDel" type="text" size="mini" icon="iconfont icon-shanchu"  @click="handleDelete(scope.$index, scope.row)"   circle></el-button>
                            </el-tooltip>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <dept-and-time-condition ref="editDeptAndTimeCondition" :set-condition="setCondition"  :editLog="getData"></dept-and-time-condition>
    </el-container>
</template>

<script>
    import DeptAndTimeCondition from '../condition/DeptAndTimeCondition'
    export default {
        components:{DeptAndTimeCondition},
        name: "trash",
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                /*表格数据 Start*/
                tableData: {
                    height: 0,
                    loading: true,
                    body: []
                },
                paginationData: {
                    pageSize: 1000,
                    total: 0,
                    curPage: 1
                },
                multipleSelection: [],
                /*表格数据 End*/
                searchTxt: '',
                condition : {
                    visible : false,
                    name : '',
                    dept: { id: 0},
                    dateRange: ["",""],
                }
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
                    url: `/admin/file/deleted/${this.paginationData.curPage}-${this.paginationData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.tableData.body = res.ctt.list
                    this.paginationData.total = res.ctt.total
                    this.tableData.loading = false
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
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.searchTxt.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                this.tableData.loading = true
                this.$axios({
                    url: `/admin/file/deleted/search/${this.paginationData.curPage}-${this.paginationData.pageSize}?name=${this.searchTxt.trim()}`,
                    method: 'get'
                }).then(res => {
                    this.tableData.body = res.ctt.list
                    this.paginationData.total = res.ctt.total
                    this.tableData.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //画面数据过滤
            setCondition(data) {
                console.log(data)
                if(data.selected !== null){
                    this.condition.dept = data.selected.id
                    this.condition.name = data.selected.name + "  "
                }else{
                    this.condition.id = 0
                }
                if(data.dateRange!== null && data.dateRange.length > 0){
                    this.condition.dateRange = data.dateRange
                }else{
                    this.condition.dateRange = ["",""]
                }
                if (data.dateRange && data.dateRange.length  === 2){
                    this.condition.name += this.$moment(data.dateRange[0]).format("YYYY年MM月DD日") + '〜' + this.$moment(data.dateRange[1]).format("MM月DD日")
                }
                if (this.condition.name !== '') this.condition.visible = true

                this.refresh()
                //this.getData()
            },
            sizeChange(num){
                this.paginationData.pageSize = num
                this.getData()
            },
            pageChange(num){
                this.paginationData.curPage = num
                this.getData()
            },
            //画面表格多选赋值操作
            handleSelectionChange(val) {
                this.multipleSelection = val;
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
            //恢复
            handleRollback: function (index, row) {
                this.$confirm('是否恢复该记录?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tableData.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/file/deleted`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '恢复完成',
                            type: 'success'
                        });
                        this.getData();
                        this.tableData.loading = false;
                    })
                }).catch(() => {

                });
            },
            //批量恢复
            handleMultiRollback:function(){
                if (this.multipleSelection.length === 0){
                    this.$message.error("没有选择需要恢复的文件，请重新选择！")
                    return
                }
                this.$confirm('确认恢复这些文件吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tableData.loading = true;
                    let para = this.multipleSelection;
                    this.$axios({
                        url: `/admin/file/deleteds`,
                        method: 'put',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '恢复成功',
                            type: 'success'
                        });
                        this.getData();
                        this.tableData.loading = false;
                    })
                }).catch(() => {

                });
            },
            //彻底删除
            handleDelete: function (index, row) {
                this.$confirm('彻底删除该记录?(被删除记录无法恢复)', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tableData.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/admin/file/deleted`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除完成',
                            type: 'success'
                        });
                        this.getData();
                        this.tableData.loading = false;
                    })
                }).catch(() => {

                });
            },
            //批量彻底删除
            handleMultiDelete: function () {
                if (this.multipleSelection.length === 0){
                    this.$message.error("没有选择需要删除的文件，请重新选择！")
                    return
                }
                this.$confirm('确认删除这些文件吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tableData.loading = true;
                    let para = this.multipleSelection;
                    this.$axios({
                        url: `/admin/file/deleteds`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                        this.tableData.loading = false;
                    })
                }).catch(() => {

                });
            },
        },
    }
</script>

<style scoped>

</style>

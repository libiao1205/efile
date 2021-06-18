<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="zhinengrizhiguanlipingtai"></app-svg>  系统日志</h1>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="iconfont icon-system-extension" @click="$refs.editLog.handleCondition()" plain>  设置检索条件</el-button>
                    <el-button-group class="plr10">
                        <el-button type="primary" size="mini" icon="iconfont icon-caozuo-shangchuan-download" @click="$refs.editLog.handleOutputCSV()" plain>  导出记录</el-button>
                        <el-button type="primary" size="mini" icon="iconfont icon-trash" @click="$refs.editLog.handleDelete()" plain>  删除记录</el-button>
                    </el-button-group>
                    <el-tag v-if="tagCondition.visible" key="condition" style="margin-left: 50px" size="medium" closable @close="clearCondition" type="info">{{tagCondition.name}}</el-tag>
                </div>
            </div>
        </el-header>

    <el-main class="sys-content pd0">
            <el-table ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell"
                      :header-cell-style="{padding: '2px'}" :height="tableData.height">
                <el-table-column type="index" label="序号" width="64" align="center"></el-table-column>
                <el-table-column prop="execinfo" label="操作" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="ip" label="IP"  min-width="20%"></el-table-column>
                <el-table-column prop="url" label="URL" min-width="40%" :show-overflow-tooltip="true"></el-table-column>
            </el-table>
            <div style="padding-top: 15px;text-align:center">
                <el-pagination
                    background
                    @current-change="selectPage"
                    layout="prev,pager,next,jumper"
                    :page-size="tableData.pageSize"
                    :current-page="tableData.curPage"
                    :total="tableData.total">
                </el-pagination>
            </div>
        </el-main>
        <edit-log ref="editLog" :set-condition="setCondition" :delete-condition="deleteCondition" :refresh="refresh" :editLog="getData"></edit-log>
    </el-container>
</template>

<script>
    import EditLog from './EditLog'
    export default {
        components:{EditLog},
        name: 'log',
        data() {
            return {
                searchForm: {
                    time: '',
                    type: '',
                    text: ''
                },
                tableData: {
                    height: 0,
                    loading: true,
                    body: [],
                    pageSize: 25,
                    total: 0,
                    curPage: 1
                },
                tagCondition : {
                    visible : false,
                    name : '',
                    member: { id: 0,kind:'',},
                    dateRange: ["",""],
                }
            }
        },
        mounted() {
            this.tableHeight()
            this.getData()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.tableData.height = window.innerHeight - this.$refs.table.$el.offsetTop - 75;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableData.height = window.innerHeight - self.$refs.table.$el.offsetTop - 75;
                    }
                })
            },
            // 获取table数据
            getData() {
                this.tableData.loading = true
                this.$axios({
                    url: `/admin/logs/${this.tableData.curPage}-${this.tableData.pageSize}/filter`,
                    method: 'get',
                    params: {kind: this.tagCondition.member.kind, id: this.tagCondition.member.id, startTime: this.tagCondition.dateRange[0], endTime: this.tagCondition.dateRange[1]}
                }).then(res => {
                    this.tableData.body = res.ctt.list
                    this.tableData.total = res.ctt.total
                    this.tableData.loading = false
                    this.$nextTick(() => {
                        this.tableHeight()
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            search(){
                console.log(`欲提交的数据   日期:${this.searchForm.time}  下拉条件:${this.searchForm.type}  文本: ${this.searchForm.text}`)
            },
            setCondition(member, dateRange) {
                this.tagCondition.name = ''
                console.log(member)
                if(member !== null){
                    this.tagCondition.member = member
                    this.tagCondition.name = member.name + "  "
                }else{
                    this.tagCondition.id = 0
                }
                if(dateRange!== null && dateRange.length > 0){
                    this.tagCondition.dateRange = dateRange
                }else{
                    this.tagCondition.dateRange = ["",""]
                }
                if (dateRange && dateRange.length  === 2){
                    this.tagCondition.name += this.$moment(dateRange[0]).format("YYYY年MM月DD日") + '〜' + this.$moment(dateRange[1]).format("MM月DD日")
                }
                if (this.tagCondition.name !== '') this.tagCondition.visible = true

                this.refresh()
                //this.getData()
            },
            deleteCondition(dateRange) {
                this.$axios({
                    url: '/admin/deleteLogs',
                    method: 'delete',
                    params: { startTime: dateRange[0], endTime: dateRange[1]}
                }).then(res => {
                    if(res.c === 0){
                        this.$message.success("删除成功")
                        this.refresh()
                    }else{
                        this.$message.error(res.msg)
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            clearCondition(tagCondition) {
                this.tagCondition.visible = false
            },
            selectPage(val){
                this.tableData.curPage = val
                this.getData()
            },
            refresh(){
                this.tableData.total = 0
                this.tableData.body = []
                this.tableData.curPage = 1
                this.getData()
            },
        }
    }
</script>

<style scoped>

</style>

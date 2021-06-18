<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="OA"></app-svg>  OA导入批处理设定</h1>
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="tableApi.loading" :data="tableApi.data" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="tableApi.height">
                    <el-table-column prop="workflowid" label="流程ID"  width="128px"></el-table-column>
                    <el-table-column prop="workflowname" label="流程名称"></el-table-column>
                    <el-table-column prop="cron" label="执行时间cron配置"></el-table-column>
                    <el-table-column label="编辑执行时间" align="center" width="150px">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="编辑执行时间" placement="bottom">
                                    <el-button class="btnEdit" type="text" size="mini"
                                               icon="iconfont icon-edit-file"
                                               @click.native="$refs.job.handleEditSetting(scope.$index, scope.row)"
                                               circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="启动/暂停" align="center" width="150px">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="批处理已经启动，点击暂停" placement="bottom">
                                    <el-button v-if="scope.row.status === 1"
                                               type="danger" size="mini"
                                               @click.native="$refs.job.handlePauseSubmit(scope.$index, scope.row)">
                                        <span style="font-size: 12px">暂停</span></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="批处理已经暂停，点击启动" placement="bottom">
                                    <el-button v-if="scope.row.status === 9"
                                               type="primary" size="mini"
                                               @click.native="$refs.job.handleResumeSubmit(scope.$index, scope.row)">
                                        <span style="font-size: 12px">启动</span></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <edit-job ref="job" :add-job="getData" :edit-job="getData"></edit-job>
    </el-container>
</template>

<script>
    import EditJob from './EditJob'
    export default {
        components: {EditJob},
        name: 'oaapi',
        data() {
            return {
                tableApi: {
                    height: 0,
                    loading: false,
                    data: []
                },
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
                    this.tableApi.height = window.innerHeight - this.$refs.table.$el.offsetTop - 200;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableApi.height = window.innerHeight - self.$refs.table.$el.offsetTop - 200
                    }
                })
            },
            // 获取table数据
            getData() {
                this.tableApi.loading = true
                this.$axios({
                    url: `/relay/process/settings`,
                    method: 'get'
                }).then(res => {
                    this.tableApi.data = res.ctt
                    this.tableApi.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
        }
    }
</script>

<style scoped>

</style>

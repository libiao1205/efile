<template>
    <el-container v-loading.fullscreen.lock="backupForm.loading">
        <el-header height='79' class='pd0'>
            <div class='app-title fxb bb1 pd10'>
                <div class='fxr fnt-s20 pd10' >
                    <h1><app-svg iconClass='yunshujuku'></app-svg>  系统备份</h1>
                    <div class="fxr pl0" style="padding-left: 50px">
                        <el-tooltip class="item" effect="dark" :content="backupSetting.display" placement="bottom">
                            <!--<el-tag type="success"><b>当前备份时间设定：</b><span class="item">{{backupSetting.display | ellipsis}}</span></el-tag>-->
                            <span style="font-size: small"><b>当前备份时间：</b>{{backupSetting.display | ellipsis}}</span>
                        </el-tooltip>
                    </div>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button type="primary" size="mini" icon="el-icon-date"  @click="backupSQL" plain>  手动备份</el-button>
                    <el-button v-if="backupSetting.status === 1" type="success" size="mini" icon="el-icon-success" @click="stop()" plain>  有效状态</el-button>
                    <el-button v-if="backupSetting.status === 9" type="danger" size="mini" icon="el-icon-error" @click="start()" plain>  暂停状态</el-button>
                    <el-popover v-model="backupSetting.cronPopover" style="padding-left: 15px">
                        <cron @change="changeCron" @close="backupSetting.cronPopover = false" i18n="cn"></cron>
                        <el-input size="mini" :disabled="true" slot="reference"
                                  v-model="backupSetting.cron"
                                  placeholder="请输入定时策略"></el-input>
                    </el-popover>
                    <el-button type="primary" size="mini" icon="el-icon-time"  @click="editBackupConfig()" plain>  修改定时策略</el-button>

                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-table ref='table' v-loading='backupTable.loading' :data='backupTable.body' highlight-current-row
                      :cell-style="{padding:'5px'}" class='custom-table-cell'
                      :header-cell-style="{padding: '2px'}" :height="backupTable.height">
                <el-table-column :formatter="dateFormat" prop="time" label='同步时间' align='left' >
                </el-table-column>
                <el-table-column prop="result" label='同步结果' align='center'>
                    <template slot-scope="scope">
                        <span v-if="scope.row.result === '备份成功'" style="color: #13ce66">{{scope.row.result}}</span>
                        <span v-else style="color: red">{{scope.row.result}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label='操作' align='center' >
                    <template slot-scope="scope">
                        <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                            <el-button class="btnDel" type="text" size="mini" icon="iconfont icon-trash" @click="deleteBackup(scope.row)" circle></el-button>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
    </el-container>
</template>

<script>
    import { cron } from "vue-cron";
    export default {
        template: "<cron/>",
        components: { cron },
        name: 'backup',
        filters: {
            ellipsis (value) {
                if (!value) return ''
                if (value.length > 50) {
                    return value.slice(0,50) + '...'
                }
                return value
            }
        },
        data(){
            return {
                backupForm: {
                    backgroundImg: '',
                    logo: '',
                    loadText: '备份中...',
                    loading: false,
                },
                backupSetting: {
                    id: 0,
                    status : 0,
                    display: '',
                    cron: '',
                    cronPopover: false,
                    orgCron :'',
                },
                backupTable: {
                    height: 40,
                    loading: false,
                    body: [],
                    pageSize: 20,
                    total: 5,
                    curPage:1
                }
            }
        },
        mounted() {
            this.tableHeight()
            this.getData()
        },
        methods: {
            tableHeight(){
                this.$nextTick(function () {
                    this.backupTable.height = window.innerHeight - this.$refs.table.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.backupTable.height = window.innerHeight - self.$refs.table.$el.offsetTop
                    }
                })
            },
            dateFormat(row, column, cellValue){
                if (!cellValue) return ''
                return this.$moment(cellValue).format('YYYY年MM月DD日 HH:mm:ss')
            },
            refresh(){
              this.getData()
            },
            getData() {
                this.$axios({
                    url: '/backup/config/',
                    method: 'get'
                }).then(res => {
                    this.backupSetting.id = res.ctt.id
                    this.backupSetting.status = res.ctt.status
                    this.backupSetting.display = this.$cronstrue.toString(res.ctt.cron.trim(),{ use24HourTimeFormat: true,locale:"zh_CN"})
                    this.backupSetting.cron = res.ctt.cron.trim()
                    this.backupSetting.orgCron = res.ctt.cron.trim()
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                });
                this.$axios({
                    url: `/backup/list/${this.backupTable.curPage}-${this.backupTable.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.backupTable.body = res.ctt.list
                    this.backupTable.total = res.ctt.size
                    this.$nextTick(() => {
                        this.tableHeight()
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                });

            },
            nextPage($state){
                this.tableData.curPage += 1
                this.getData($state)
            },
            changeCron(val) {
                this.backupSetting.cron = val;
            },
            start(){
                this.$confirm('确认开始自动备份策略吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: '/backup/start',
                        method: 'put',
                        data: {
                            id: this.backupSetting.id
                        }
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已启动自动备份策略");
                            this.refresh();
                        }else{
                            this.$message.error(res.msg);
                        }
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });
            },
            stop(){
                this.$confirm('确认停止自动备份策略吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: '/backup/stop',
                        method: 'put',
                        data: {
                            id: this.backupSetting.id
                        }
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已停止自动备份策略");
                            this.refresh();
                        }else{
                            this.$message.error(res.msg);
                        }
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });
            },
            editBackupConfig(){
                if (this.backupSetting.cron === this.backupSetting.orgCron){
                    this.$message.warning("Cron表达式没有发现有更新！")
                    return
                }
                this.$confirm('确认开始自动备份策略吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: '/backup/config',
                        method: 'put',
                        data: {
                            id: this.backupSetting.id,
                            cron: this.backupSetting.cron
                        }
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已修改自动备份策略");
                            this.refresh();
                        }else{
                            this.$message.error(res.msg);
                        }
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });
            },
            backupSQL(){
                this.$confirm('确认开始数据备份?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.backupForm.loading = true;
                    this.$axios({
                        url: '/backup',
                        method: 'post',
                    }).then(res => {
                        this.backupForm.loading = false;
                        if(res.c === 0){
                            this.$message.success("数据备份成功");
                        }else{
                            this.$message.error(res.msg);
                        }
                        this.getData();
                    }).catch(err => {
                        this.backupForm.loading = false;
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });

            },
            deleteBackup(row){
                this.$confirm('确认删除这个备份?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: `/backup/${row.id}`,
                        method: 'delete',
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已经删除指定备份")
                            this.refresh()
                        }else {
                            this.$message.error(res.msg)
                        }
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }).catch(() => {

                });
            },
            downLoadBackUp(row){
                this.$axios({
                    url: `/backup/download/${row.id}`,
                    method: 'post',
                }).then(res => {
                    if (!res) {
                        this.$message({
                            message: '下载失败',
                            type: 'error'
                        });
                        this.getData();
                        return
                    }
                    const content = res
                    const blob = new Blob([content])
                    const filename = row.directory.split('/')
                    if ('download' in document.createElement('a')) { // 非IE下载
                        const elink = document.createElement('a')
                        elink.download = filename[filename.length - 1]
                        elink.style.display = 'none'
                        elink.href = URL.createObjectURL(blob)
                        document.body.appendChild(elink)
                        elink.click()
                        URL.revokeObjectURL(elink.href) // 释放URL 对象
                        document.body.removeChild(elink)
                    } else { // IE10+下载
                        navigator.msSaveBlob(blob, fileName)
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>

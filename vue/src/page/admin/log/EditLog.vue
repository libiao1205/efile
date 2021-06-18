<template>
    <div>
        <el-dialog :title="formOutPut.title" :visible.sync="formOutPut.visible" :close-on-click-modal="true" width="700px">
            <el-form :model="formOutPut.data" label-width="0px" :rules="formOutPut.rules" ref="formOutPut">
                <el-row :gutter="20" style="min-height: 400px; height:400px;">
                    <el-col :span="10">
                        <div style="box-shadow:0 0 1px #ccc;height:400px;">
                            <el-scrollbar >
                                <el-tree ref="memberTree" v-if="formOutPut.visible" :data="memberTree.data" highlight-current v-loading="memberTree.loading" :props="memberTree.props"
                                         :expand-on-click-node="false" lazy node-key = 'key' @node-click="memberSelect" :load="printMemberTree" :default-expanded-keys="['dept-1']">
                            <span class="custom-tree-node" slot-scope="{ node, data }">
                                <div v-if="data.id === 1">
                                    <div v-if="data.kind === 'dept'">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-zhuye11"></use>
                                        </svg>
                                        <span><b>{{node.label}}</b></span>
                                    </div>
                                    <div v-else>
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-user"></use>
                                        </svg>
                                        <span>{{node.label}}</span>
                                    </div>
                                </div>
                                <div v-else>
                                    <div v-if="data.type === 1">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-zuzhiguanli-"></use>
                                        </svg>
                                        <span>{{node.label}}</span>
                                    </div>
                                    <div v-else-if="data.type === 2">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-danweizuzhi"></use>
                                        </svg>
                                        <span>{{node.label}}</span>
                                    </div>
                                    <div v-else-if="data.type === 0">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-user"></use>
                                        </svg>
                                        <span>{{node.label}}</span>
                                    </div>
                                    <div v-else>
                                        <span>{{node.label}}</span>
                                    </div>
                                </div>
                            </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>
                    </el-col>

                    <el-col :span="14">
                        <el-form-item label="" prop="name">
                            <el-date-picker
                                v-model="formOutPut.data.dateRange"
                                type="daterange"
                                align="right"
                                unlink-panels
                                range-separator="至"
                                start-placeholder="开始日期"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                end-placeholder="结束日期"
                                :picker-options="pickerDateRange.Options">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item v-if="formOutPut.isOutPut">
                            <el-checkbox v-model="formOutPut.data.isDelete" >同时删除履历</el-checkbox>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" v-if="formOutPut.isOutPut" @click="handleOutputCSVSubmit" :loading="formOutPut.loading">确定</el-button>
                <el-button type="primary" v-if="!formOutPut.isOutPut" @click="handleConditionSubmit" :loading="formOutPut.loading">确定</el-button>
                <el-button @click.native="formOutPut.visible = false">取消</el-button>
            </div>
        </el-dialog>
        <el-dialog title="删除历史履历" :visible.sync="formDelete.visible" :close-on-click-modal="true" width="400px">
            <el-form :model="formDelete.data" label-width="0px" :rules="formDelete.rules" ref="formDelete">
                    <el-form-item label="" prop="name">
                        <el-date-picker
                            v-model="formDelete.data.dateRange"
                            type="daterange"
                            align="right"
                            unlink-panels
                            value-format="yyyy-MM-dd HH:mm:ss"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            :picker-options="pickerDateRange.Options">
                        </el-date-picker>
                    </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleDeleteSubmit" :loading="formDelete.loading">确定</el-button>
                <el-button @click.native="formDelete.visible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EditLog",
        props: {
            setCondition: {
                type: Function,
                default: null
            },
            editLog: {
                type: Function,
                default: null
            },
            deleteCondition: {
                type: Function,
                default: null
            },
            refresh: {
                type: Function,
                default: null
            }
        },
        data() {
            return {
                formDelete : {
                    data : {
                        dateRange : ''
                    },
                    rules : {

                    },
                    visible: false,
                    loading: false
                },
                formOutPut : {
                    isOutPut: false,
                    title: '',
                    data : {
                        isDelete: false,
                        dateRange : ''
                    },
                    rules : {

                    },
                    visible: false,
                    loading: false
                },
                memberTree:{
                    data: [],
                    loading: false,
                    props: {
                        children: 'children',
                        label: 'name'
                    },
                    selected:null,
                    vis: true,
                },
                pickerDateRange : {
                    Options: {
                        shortcuts: [{
                            text: '最近一周',
                            onClick(picker) {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                                picker.$emit('pick', [start, end]);
                            }
                        }, {
                            text: '最近一个月',
                            onClick(picker) {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                                picker.$emit('pick', [start, end]);
                            }
                        }, {
                            text: '最近三个月',
                            onClick(picker) {
                                const end = new Date();
                                const start = new Date();
                                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                                picker.$emit('pick', [start, end]);
                            }
                        }]
                    },
                },
            }
        },
        methods: {
            memberSelect(data) {
                this.memberTree.selected = data
            },
            handleOutputCSV() {
                this.formOutPut.title = '导出历史数据'
                this.formOutPut.visible = true
                this.formOutPut.isOutPut = true
            },
            handleCondition() {
                this.formOutPut.title = '选择检索条件'
                this.formOutPut.visible = true
                this.formOutPut.isOutPut = false
            },
            handleDelete() {
                this.formDelete.visible = true
            },
            printMemberTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    if (node.data.kind === 'dept'){
                        id = node.data.id
                    } else {
                        resolve([])
                        return
                    }
                }
                this.memberTree.loading = true
                this.$axios({
                    url: `/admin/dept/${id}/members`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.memberTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            handleDeleteSubmit() {
                this.$refs.formDelete.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认要删除吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'error'
                        }).then(() => {
                            this.deleteCondition(this.formDelete.data.dateRange)
                            this.clear()
                        })
                    }
                })
            },
            handleOutputCSVSubmit() {
                this.$refs.formOutPut.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            let kind = "";
                            let id = 0;
                            let start = "";
                            let end = "";
                            if(this.memberTree.selected !== null){
                                kind = this.memberTree.selected.kind
                                id = this.memberTree.selected.id
                            }
                            if(this.formOutPut.data.dateRange !== ""){
                                start = this.formOutPut.data.dateRange[0];
                                end = this.formOutPut.data.dateRange[1];
                            }
                            this.$axios({
                                url: `admin/logs/download`,
                                method: 'post',
                                responseType: 'blob',
                                params: {kind: kind,id: id, startTime: start, endTime: end, isDelete: this.formOutPut.data.isDelete}
                            }).then((res) => {
                                if (!res) {
                                    this.$message({
                                        message: '下载失败',
                                        type: 'error'
                                    });
                                    return
                                }
                                const content = res
                                const blob = new Blob([content])
                                const filename = "log.csv"
                                if ('download' in document.createElement('a')) { // 非IE下载
                                    const elink = document.createElement('a')
                                    elink.download = filename
                                    elink.style.display = 'none'
                                    elink.href = URL.createObjectURL(blob)
                                    document.body.appendChild(elink)
                                    elink.click()
                                    URL.revokeObjectURL(elink.href) // 释放URL 对象
                                    document.body.removeChild(elink)
                                } else { // IE10+下载
                                    window.navigator.msSaveBlob(blob, filename)
                                }
                                if(this.formOutPut.data.isDelete){
                                    this.refresh()
                                }
                                this.clear()
                            })
                        })
                    }
                })
            },
            handleConditionSubmit () {
                this.setCondition(this.memberTree.selected, this.formOutPut.data.dateRange)
                this.clear()
                this.formOutPut.visible = false
            },
            clear(){
                this.formDelete.data.dateRange = ''
                this.formDelete.loading = false
                this.formDelete.visible = false

                this.formOutPut.isOutPut = false
                this.formOutPut.title = ''
                this.formOutPut.data.isDelete = false
                this.formOutPut.data.dateRange = ''
                this.formOutPut.visible = false

            }
        }
    }
</script>

<style scoped>

</style>

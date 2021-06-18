<template>
    <el-dialog title="外链导出" :visible.sync="formOutPut.visible" :close-on-click-modal="true" width="700px" @open="getDept">
        <el-form :model="formOutPut.data" label-width="120px" :rules="formOutPut.rules" ref="formOutPut">
            <el-row :gutter="20" style="min-height: 400px; height:400px;">
                <el-col :span="10">
                    <div style="box-shadow:0 0 1px #ccc;height:400px;">
                        <el-scrollbar>
                            <el-tree class="mr20" ref="deptTree" :data="deptTree.data"
                                     highlight-current v-loading="deptTree.loading" :props="deptTree.props"
                                     :expand-on-click-node="false" @node-click="deptSelect" node-key="id" :default-expanded-keys="[1]">
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <div v-if="data.id === 1">
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-zhuye11"></use>
                                            </svg>
                                            <span><b>{{node.label}}</b></span>
                                        </div>
                                        <div v-else>
                                            <div v-if="data.type === 1">
                                                <svg class="icon" aria-hidden="true">
                                                    <use xlink:href="#icon-zuzhiguanli-"></use>
                                                </svg>
                                                <span>{{node.label}}</span>
                                            </div>
                                            <div v-else>
                                                <svg class="icon" aria-hidden="true">
                                                    <use xlink:href="#icon-danweizuzhi"></use>
                                                </svg>
                                                <span>{{node.label}}</span>
                                            </div>
                                        </div>
                                    </span>
                            </el-tree>
                        </el-scrollbar>
                    </div>
                </el-col>

                <el-col :span="14">
                    <el-date-picker
                        v-model="pickerDateRange.data"
                        type="daterange"
                        size="large"
                        align="right"
                        unlink-panels
                        value-format="yyyy-MM-dd"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        :picker-options="pickerDateRange.Options">
                    </el-date-picker>
                </el-col>

            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleOutputCSVSubmit" :loading="formOutPut.loading">确定</el-button>
            <el-button @click.native="formOutPut.visible = false">取消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "OutPutCSV",
        data() {
            return {
                formOutPut : {
                    data : {

                    },
                    rules : {

                    },
                    visible: false,
                    loading: false
                },
                deptTree:{
                    data: [],
                    props: {
                        children: 'children',
                        label: 'name'
                    },
                    loading: false,
                    selected:null,
                },
                pickerDateRange : {
                    data : '',
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
            getDept: function(){
                this.deptTree.loading = true
                this.$axios({
                    url: `/admin/depts`,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt)
                    this.deptTree.data = data
                    this.deptTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            deptSelect(data) {
                this.deptTree.selected = data
            },
            handleOutputCSV(){
                this.formOutPut.visible = true
                if (this.$refs.formOutPut) this.$refs.formOutPut.resetFields()
            },
            handleOutputCSVSubmit() {
                this.$refs.formOutPut.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formOutPut.loading = true
                            let id = 0;
                            if (this.deptTree.selected){
                                id = this.deptTree.selected.id;
                            }
                            let startDate = '0000-00-00';
                            let endDate = '9999-99-99';
                            if (this.pickerDateRange.data){
                                startDate = this.pickerDateRange.data[0];
                                endDate = this.pickerDateRange.data[1];
                            }
                            this.$axios({
                                url: `admin/dept/${id}/links/download/${startDate}/${endDate}`,
                                method: 'post',
                                responseType: 'blob'
                            }).then((res) => {
                                if (!res) {
                                    this.$message({
                                        message: '下载失败',
                                        type: 'error'
                                    });
                                    this.formOutPut.loading = false
                                    this.formOutPut.visible = false
                                    return
                                }
                                const content = res
                                const blob = new Blob([content])
                                const filename = "link_log.csv"
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
                                this.formOutPut.loading = false
                                this.formOutPut.visible = false
                            })
                        })
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>

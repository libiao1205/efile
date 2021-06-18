<template>
    <div>
        <el-dialog :title="formCondition.title" :visible.sync="formCondition.visible" :close-on-click-modal="true" width="700px" @open="getDept">
            <el-form :model="formCondition.data" label-width="0px" :rules="formCondition.rules" ref="formCondition">
                <el-row :gutter="20" style="min-height: 400px; height:400px;">
                    <el-col :span="10">
                        <div style="box-shadow:0 0 1px #ccc;height:400px;">
                            <el-scrollbar >
                                <el-tree class="mr20" ref="deptTree" :data="deptTree.data"
                                         highlight-current v-loading="deptTree.loading" :props="deptTree.props"
                                         :expand-on-click-node="false" @node-click="selectDept" node-key="id" :default-expanded-keys="[1]">
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
                        <el-form-item label="" prop="name">
                            <el-date-picker
                                v-model="formCondition.data.dateRange"
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
                        <el-form-item v-if="formCondition.isDelete">
                            <el-checkbox v-model="formCondition.data.isDelete" >同时删除检索结果数据</el-checkbox>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="handleConditionSubmit" :loading="formCondition.loading">确定</el-button>
                <el-button @click.native="formCondition.visible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "DeptAndTimeCondition",
        props: {
            setCondition: {
                type: Function,
                default: null
            },
        },
        data() {
            return {
                formCondition : {
                    title: '',
                    isDelete : false,
                    data : {
                        selected : null,
                        dateRange : '',
                        isDelete: false,
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
            handleCondition() {
                this.formCondition.title = '选择检索条件'
                this.formCondition.visible = true
            },
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
            selectDept(data) {
                this.deptTree.selected = data
                this.formCondition.data.selected = data
            },
            handleConditionSubmit () {
                this.setCondition(this.formCondition.data)
                this.formCondition.visible = false
            }
        }
    }
</script>

<style scoped>

</style>

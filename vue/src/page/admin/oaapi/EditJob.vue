<template>
    <el-dialog :title="formJob.title" :visible.sync="formJob.visible" :close-on-click-modal="false" width="600px">
        <el-form :model="formJob.data" label-width="120px" :rules="formJob.rules" ref="formJob">
            <el-form-item label="执行流程名称" prop="workflowname" >
                <el-input v-model="formJob.data.workflowname" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="执行程序" prop="classname">
                <el-input v-model="formJob.data.classname" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="执行时间表达式" prop="cron">
                <el-input v-model="formJob.data.cron" auto-complete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button v-if="formJob.isNew" type="primary" icon="iconfont icon-bianji2" @click.native="addSubmit" :loading="formJob.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button v-if="!formJob.isNew" type="primary" icon="iconfont icon-bianji2" @click.native="handleEditSettingSubmit" :loading="formJob.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button icon="iconfont icon-jieshu" @click.native="formJob.visible = false">
                <span style="padding-left: 10px;"><b>关闭</b></span>
            </el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "EditJob",
        props: {
            addJob: {
                type: Function,
                default: null
            },
            editJob: {
                type: Function,
                default: null
            },

        },
        data() {
            return {
                formJob: {
                    isNew: false,
                    title:'',
                    visible: false,
                    loading: false,
                    data :{
                        id: 0,
                        workflowname: '',
                        classname: '',
                        cron: '',
                    },
                    rules:{
                        cron: [
                            { required: true, message: '执行时间', trigger: 'blur' }
                        ]
                    },
                }
            }
        },
        methods :{
            handleAdd() {
                if (this.$refs.formJob) this.$refs.formJob.resetFields()
                this.formJob.isNew = true
                this.formJob.title = '新增接口设定'
                this.formJob.data = {
                    id: 0,
                    workflowname: '',
                    classname: '',
                    cron: '',
                }
                this.formJob.visible = true
            },
            addSubmit() {
                this.$refs.formJob.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认新增吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formJob.loading = true
                            let param = Object.assign({}, this.formJob.data)
                            this.$axios({
                                url: `/task/job?name=${param.name}&group=${param.group}&cron=${param.cron}`,
                                method: 'post',
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("添加接口成功")
                                    this.formJob.visible = false
                                    this.addJob()
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formJob.loading = false
                            })
                        })
                    }
                })
            },
            handleEditSetting(index, row) {
                if (this.$refs.formJob) this.$refs.formJob.resetFields()
                this.formJob.isNew = false
                this.formJob.title = '编辑接口执行时间(Cron表达式编辑)'
                this.formJob.data = {
                    id : row.id,
                    workflowname : row.workflowname,
                    classname : row.classname,
                    cron : row.cron,
                }
                this.formJob.visible = true
            },
            handleEditSettingSubmit() {
                this.$refs.formJob.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交新的执行时间吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formJob.loading = true
                            let param = Object.assign({}, this.formJob.data)
                            this.$axios({
                                url: `/relay/process/cron`,
                                method: 'put',
                                data: param
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("编辑执行时间成功")
                                    this.formJob.visible = false
                                    this.editJob()
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formJob.loading = false
                            })
                        })
                    }
                })
            },
            handlePauseSubmit(index, row) {
                this.$confirm('确认暂停批处理吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.formJob.loading = true
                    let param = {
                        id : row.id,
                    }
                    this.$axios({
                        url: `/relay/process/pause`,
                        method: 'put',
                        data: param
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("暂停执行批处理")
                            this.formJob.visible = false
                            this.editJob()
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.formJob.loading = false
                    })
                })
            },handleResumeSubmit(index, row) {
                this.$confirm('确认开始批处理吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.formJob.loading = true
                    let param = {
                        id : row.id,
                    }
                    this.$axios({
                        url: `/relay/process/resume`,
                        method: 'put',
                        data: param
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("开始执行批处理")
                            this.formJob.visible = false
                            this.editJob()
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.formJob.loading = false
                    })
                })
            }
        }
    }
</script>

<style scoped>

</style>

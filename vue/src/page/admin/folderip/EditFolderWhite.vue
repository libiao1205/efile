<template>
    <div>
        <el-dialog title="设置文件夹访问IP白名单" :visible.sync="fromIpInput.visible" :close-on-click-modal="false" @open="handleWhiteList" width="500px" >
            <el-form :model="fromIpInput" label-width="80px" ref="ipInputForm" :rules="fromIpInput.rules">
                <el-form-item label="目录名：" style="font-style: oblique;font-weight:bold;">
                    <div>
                        <app-svg iconClass="wenjianjia3"></app-svg>
                        <span style="font-style: normal;font-weight: normal;">{{fromIpInput.folderName}}</span>
                    </div>
                </el-form-item>
                <el-form-item label="IP白名单" prop="ipInfo">
                    <el-input
                        type="textarea"
                        v-model="fromIpInput.ipInfo"
                        :rows="6"
                        placeholder="请输入白名单规则，规则之间用';'号隔开"
                    >
                    </el-input>
                </el-form-item>
                <div style="margin: 15px 0 0 15px; padding: 5px; display: flex; height: 100px;border-radius:5px;border: 1px solid #dcdfe6;">
                        <span style="color: #606266;font-weight:bold;">说明：<br/>
                            1、输入需要开通的IP白名单列表，IP与IP直接用';'号间隔。<br/>
                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                            如：10.0.4.5;10.0.4.6<br/>
                            2、支持IP段的设置，设置方式为起始IP与结束IP间用'-'号连接。<br/>
                            &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                            如：10.0.4.5-10.0.4.15<br/>
                        </span>
                </div>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleWhiteListSubmit" :loading="fromIpInput.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="fromIpInput.visible = false">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EditFolderWhite",
        props: {
            editJob: {
                type: Function,
                default: null
            },
        },
        data() {
          return {
              /*文件夹IP白名单处理 Start*/
              fromIpInput: {
                  folderId:0,
                  folderName:'',
                  ipInfo:'',
                  visible: false,
                  loading: false,
                  rules: {
                      ipInfo: [
                          { required: true, message: '请输入ip白名单', trigger: 'blur' }
                      ]
                  },
              },
              /*文件夹IP白名单处理 End*/
          }
        },
        methods:{
            //设置文件夹IP白名单--Start
            handleWhiteList(node){
                if (this.$refs.fromIpInput) this.$refs.fromIpInput.resetFields()

                this.$axios({
                    url: `/admin/folder/${node.data.id}/ip`,
                    method: 'get'
                }).then(res => {
                    if (res.ctt){
                        this.fromIpInput.ipInfo = res.ctt.ipInfo
                    }else{
                        this.fromIpInput.ipInfo = ''
                    }
                    this.fromIpInput.folderId = node.data.id
                    this.fromIpInput.folderName = node.data.name
                    this.fromIpInput.visible = true
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            handleEditWhiteList(row){
                if (this.$refs.fromIpInput) this.$refs.fromIpInput.resetFields()

                this.$axios({
                    url: `/admin/folder/${row.id}/ip`,
                    method: 'get'
                }).then(res => {
                    if (res.ctt){
                        this.fromIpInput.ipInfo = res.ctt.ipInfo
                    }else{
                        this.fromIpInput.ipInfo = ''
                    }
                    this.fromIpInput.folderId = row.id
                    this.fromIpInput.folderName = row.name
                    this.fromIpInput.visible = true
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            handleWhiteListSubmit(){
                this.$refs.ipInputForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认添加这些IP白名单吗?', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            let para = Object.assign({}, this.fromIpInput);
                            this.$axios({
                                url: `/admin/folder/${this.fromIpInput.folderId}/ip`,
                                method: 'put',
                                data: para
                            }).then((res) => {
                                if (res.c === 0) {
                                } else {
                                    this.$message.error(res.msg)
                                }
                                this.fromIpInput.visible = false
                            })
                        }).catch(() => {

                        });
                    }
                })
            },
            //设置文件夹IP白名单--End
        }
    }
</script>

<style scoped>

</style>

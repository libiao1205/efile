<template>
    <div>
        <el-dialog title="设置文件夹访问IP白名单" :visible.sync="fromIpInput.visible" :close-on-click-modal="false" width="1000px" >
            <el-form :model="fromIpInput" label-width="80px" ref="ipInputForm" :rules="fromIpInput.rules">
                <el-row :gutter="30" style="min-height: 500px; height:500px;">
                    <el-col :span="13">
                        <div style="box-shadow:0 0 1px #ccc;">
                            <el-scrollbar style="height:450px;">
                                <el-tree class="mr20" ref="folderTree" v-if="fromIpInput.visible" :accordion="true" :highlight-current="true"
                                         :data="folderTree.body" :props="folderTree.props"  v-loading="folderTree.loading"
                                         :default-expand-all="false" :expand-on-click-node="false" node-key='id' :default-expanded-keys="defaultKey"
                                         lazy :load="printFolderTree" @node-click="folderSelect" >
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <div v-if="data.id === 1" >
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-zhuye11"></use>
                                            </svg>
                                            <b>{{node.label}}</b>
                                        </div>
                                        <div v-else>
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-wenjianjia"></use>
                                            </svg>
                                            <b>{{node.label}}</b>
                                        </div>
                                        <span v-if="data.id === 1">
                                            <el-tooltip class="item" effect="dark" content="新增下级文件夹" placement="bottom">
                                                <el-button class="btnAdd" type="text" size="mini"
                                                           icon="iconfont icon-add"
                                                           @click.native="$refs.editFolder.handleFolderAdd(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                        </span>
                                        <span v-else>
                                            <el-tooltip class="item" effect="dark" content="新增下级文件夹" placement="bottom">
                                                <el-button class="btnAdd" type="text" size="mini"
                                                           icon="iconfont icon-add"
                                                           @click.native="$refs.editFolder.handleFolderAdd(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="编辑文件夹" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnEdit"
                                                           type="text" size="mini"
                                                           icon="iconfont icon-edit-node"
                                                           @click.native="$refs.editFolder.handleFolderEdit(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="删除文件夹" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnDel" type="text" size="mini"
                                                           icon="iconfont icon-trash"
                                                           @click.native="$refs.editFolder.handleFolderDelete(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                        </span>
                                    </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>
                    </el-col>
                    <el-col :span="11">
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
                                :rows="10"
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
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleWhiteListSubmit" :loading="fromIpInput.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="close">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <edit-folder ref="editFolder" :add-folder="addFolder" :edit-folder="editFolder" :delete-folder="deleteFolder"></edit-folder>
    </div>
</template>

<script>
    import EditFolder from '../doc/EditFolder'
    export default {
        components:{EditFolder},
        name: "AddFolderWhite",
        props: {
            addJob: {
                type: Function,
                default: null
            },
        },
        data() {
          return {
              defaultKey: [1],
              /*文件夹树 Start*/
              folderTree:{
                  visible: false,
                  loading: false,
                  props:{
                      label: 'name',
                      children: 'children',
                  },
                  body: [],
                  selected:null,
              },
              /*文件夹树 End*/
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
            handleAddWhiteList(){
                this.fromIpInput.visible = true
            },
            printFolderTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    id = node.data.id
                }
                this.folderTree.loading = true
                this.$axios({
                    url: `/admin/folder/${id}/children`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.folderTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderSelect(data) {
                this.folderTree.selected = data
                this.getWhiteList(data)
            },
            addFolder(res) {
                this.$refs.folderTree.append(res.ctt, res.ctt.parentId)
            },
            editFolder(para) {
                let node = this.$refs.folderTree.getNode(para)
                this.$refs.folderTree.remove(para)
                this.$refs.folderTree.append(para, para.parentId)
                node.data.name = para.name;
            },
            deleteFolder(para) {
                this.$refs.folderTree.remove(para)
            },
            getWhiteList(node){
                if (this.$refs.fromIpInput) this.$refs.fromIpInput.resetFields()
                this.$axios({
                    url: `/admin/folder/${node.id}/ip`,
                    method: 'get'
                }).then(res => {
                    if (res.ctt){
                        this.fromIpInput.ipInfo = res.ctt.ipInfo
                    }else{
                        this.fromIpInput.ipInfo = ''
                    }
                    this.fromIpInput.folderId = node.id
                    this.fromIpInput.folderName = node.name

                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            handleWhiteListSubmit(){
                if (!this.folderTree.selected){
                    this.$message.warning("没有选择文件夹！")
                    return
                }
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
                                if(res.c === 0){
                                    this.$message.success("编辑配置成功")
                                    this.clear()
                                    this.fromIpInput.visible = false
                                    this.addJob()
                                }else{
                                    this.$message.error(res.msg)
                                }
                            })
                        }).catch(() => {

                        });
                    }
                })
            },
            clear(){
                this.folderTree.body = []
                this.folderTree.selected = null
                this.fromIpInput.folderId = 0
                this.fromIpInput.folderName = ''
                this.fromIpInput.ipInfo = ''
            },
            close(){
                this.clear()
                this.fromIpInput.visible = false
            }
        }
    }
</script>

<style scoped>

</style>

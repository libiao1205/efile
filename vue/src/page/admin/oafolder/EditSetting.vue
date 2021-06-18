<template>
    <div>
    <el-dialog :title="formFolderSetting.title" :visible.sync="formFolderSetting.visible" :close-on-click-modal="false" width="800px">
        <el-form :model="formFolderSetting.data" label-width="120px" ref="formFolderSetting">
            <el-row :gutter="30" style="min-height: 500px; height:500px;">
                <el-col :span="12">
                    <el-form-item label="流程名称：" prop="name" style="font-weight:bold;">
                        <el-select v-model="formFolderSetting.workflowData.key" placeholder="请选择">
                            <el-option style="margin-bottom: 10px"
                                       v-for="item in formFolderSetting.workflowData.options"
                                       :key="item.key"
                                       :label="item.value"
                                       :value="item.key">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="基地：" prop="name" style="font-weight:bold;">
                        <el-select v-model="formFolderSetting.deptData.key" placeholder="请选择">
                            <el-option style="margin-bottom: 10px"
                                v-for="item in formFolderSetting.deptData.options"
                                :key="item.key"
                                :label="item.value"
                                :value="item.key">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="合同类型" prop="group" style="font-weight:bold;">
                        <el-select v-model="formFolderSetting.kindData.key" placeholder="请选择">
                            <el-option style="margin-bottom: 10px"
                                       v-for="item in formFolderSetting.kindData.options"
                                       :key="item.key"
                                       :label="item.value"
                                       :value="item.key">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="合同年份" prop="cron" style="font-weight:bold;">
                        <el-date-picker
                            v-model="formFolderSetting.dateData.value"
                            type="year"
                            placeholder="选择年">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <div style="box-shadow:0 0 1px #ccc;">
                        <el-scrollbar style="height:450px;">
                            <el-tree class="mr20" ref="folderTree" v-if="formFolderSetting.visible" :accordion="true" :highlight-current="true"
                                     :data="formFolderSetting.folderTree.body" :props="formFolderSetting.folderTree.props"  v-loading="formFolderSetting.folderTree.loading"
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
            </el-row>

        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button v-if="formFolderSetting.isNew" type="primary" icon="iconfont icon-bianji2" @click.native="addSubmit" :loading="formFolderSetting.loading">
                <span style="padding-left: 10px;"><b>确定</b></span>
            </el-button>
            <el-button v-if="!formFolderSetting.isNew" type="primary" icon="iconfont icon-bianji2" @click.native="editSubmit" :loading="formFolderSetting.loading">
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
        name: "EditSetting",
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
        mounted() {
            this.getRestOption()
        },
        data() {
            return {
                defaultKey: [1],
                formFolderSetting: {
                    isNew: false,
                    title:'',
                    visible: false,
                    loading: false,
                    id: 0,
                    workflowData:{
                        id : 0,
                        key: '',
                        options: [],
                    },
                    deptData:{
                        id : 0,
                        key : '',
                        options: [],
                    },
                    kindData:{
                        id : 0,
                        key: '',
                        options: [],
                    },
                    dateData:{
                        value : ''
                    },
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
                    submitData:{
                        id: 0,
                        workflowid : '',
                        firstdictcode : '',
                        seconddictcode : '',
                        thirddictcode : '',
                        folderid : '',
                        foldername : '',
                        fullpath : '',
                        fullname : '',
                    },
                },

            }
        },
        methods :{
            printFolderTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    id = node.data.id
                }
                this.formFolderSetting.folderTree.loading = true
                this.$axios({
                    url: `/admin/folder/${id}/children`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.formFolderSetting.folderTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderSelect(data) {
                this.formFolderSetting.folderTree.selected = data
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
            handleAddSetting() {
                if (this.$refs.formFolderSetting) this.$refs.formFolderSetting.resetFields()
                this.formFolderSetting.isNew = true
                this.formFolderSetting.title = '新增导入流程配置'
                this.formFolderSetting.dateData = {
                    value: '',
                }
                this.$axios({
                    url: `/relay/setting/relay_setting/workflow`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.workflowData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })

                this.$axios({
                    url: `/relay/setting/relay_setting/dept`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.deptData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })

                this.$axios({
                    url: `/relay/setting/relay_setting/kind`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.kindData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                this.formFolderSetting.visible = true
            },
            addSubmit() {
                if (!this.formFolderSetting.workflowData.key) {
                    this.$message.warning("流程没有指定！")
                    return
                }
                if (!this.formFolderSetting.deptData.key) {
                    this.$message.warning("基地没有指定！")
                    return
                }
                if (!this.formFolderSetting.kindData.key) {
                    this.$message.warning("合同类型没有指定！")
                    return
                }
                if (!this.formFolderSetting.dateData.value) {
                    this.$message.warning("合同年份没有指定！")
                    return
                }
                if (!this.formFolderSetting.folderTree.selected) {
                    this.$message.warning("导入目录没有指定！")
                    return
                }

                this.$confirm('确认新增吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.formFolderSetting.loading = true
                    let workflow = this.formFolderSetting.workflowData.key
                    let dept = this.formFolderSetting.deptData.key
                    let kind = this.formFolderSetting.kindData.key
                    let year = this.$moment(this.formFolderSetting.dateData.value).format("YYYY")
                    let folder = this.formFolderSetting.folderTree.selected
                    this.formFolderSetting.submitData = {
                        workflowid : workflow,
                        firstdictcode : dept,
                        seconddictcode : kind,
                        thirddictcode : year,
                        folderid : folder.id,
                        foldername : folder.name,
                        fullpath : folder.fullPath,
                        fullname : folder.fullName,
                    }
                    this.$axios({
                        url: `/relay/setting`,
                        method: 'post',
                        data: this.formFolderSetting.submitData
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("添加配置成功")
                            this.formFolderSetting.visible = false
                            this.clear()
                            this.addJob()
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.formFolderSetting.loading = false
                    })
                })
            },
            handleEdit(index,data) {
                if (this.$refs.formFolderSetting) this.$refs.formFolderSetting.resetFields()
                this.formFolderSetting.isNew = false
                this.formFolderSetting.title = '编辑导入流程配置'
                // this.formFolderSetting.data = data
                this.formFolderSetting.visible = true
                this.formFolderSetting.dateData = {
                    value: '',
                }
                this.formFolderSetting.workflowData.key = data.workflowid.toString()
                this.formFolderSetting.deptData.key = data.firstdictcode.toString()
                this.formFolderSetting.kindData.key = data.seconddictcode.toString()
                this.formFolderSetting.dateData.value = data.thirddictcode.toString()
                this.formFolderSetting.id = data.id
                this.$nextTick(() => {
                    let list = []
                    list.push(data.folderid)
                    this.defaultKey = list
                    this.$refs.folderTree.setCurrentKey(data.folderid)
                })
            },
            editSubmit() {
                this.formFolderSetting.folderTree.selected = this.$refs.folderTree.getCurrentNode()
                if (!this.formFolderSetting.workflowData.key) {
                    this.$message.warning("流程没有指定！")
                    return
                }
                if (!this.formFolderSetting.deptData.key) {
                    this.$message.warning("基地没有指定！")
                    return
                }
                if (!this.formFolderSetting.kindData.key) {
                    this.$message.warning("合同类型没有指定！")
                    return
                }
                if (!this.formFolderSetting.dateData.value) {
                    this.$message.warning("合同年份没有指定！")
                    return
                }
                if (!this.formFolderSetting.folderTree.selected) {
                    this.$message.warning("导入目录没有指定！")
                    return
                }

                this.$confirm('确认修改吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.formFolderSetting.loading = true
                    let workflow = this.formFolderSetting.workflowData.key
                    let dept = this.formFolderSetting.deptData.key
                    let kind = this.formFolderSetting.kindData.key
                    let year = this.$moment(this.formFolderSetting.dateData.value).format("YYYY")
                    let folder = this.formFolderSetting.folderTree.selected
                    this.formFolderSetting.submitData = {
                        id: this.formFolderSetting.id,
                        workflowid : workflow,
                        firstdictcode : dept,
                        seconddictcode : kind,
                        thirddictcode : year,
                        folderid : folder.id,
                        foldername : folder.name,
                        fullpath : folder.fullPath,
                        fullname : folder.fullName,
                    }
                    this.$axios({
                        url: `/relay/editSetting`,
                        method: 'post',
                        data: this.formFolderSetting.submitData
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("修改配置成功")
                            this.formFolderSetting.visible = false
                            this.clear()
                            this.addJob()
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.formFolderSetting.loading = false
                    })
                })
            },
            handleDeleteSettingSubmit(index, row){
                this.$confirm('确认删除配置?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: '/relay/setting',
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '配置已删除',
                            type: 'success'
                        });
                        this.addJob();
                    })
                }).catch(() => {

                });
            },
            getRestOption(){
                this.$axios({
                    url: `/relay/setting/relay_setting/workflow`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.workflowData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })

                this.$axios({
                    url: `/relay/setting/relay_setting/dept`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.deptData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })

                this.$axios({
                    url: `/relay/setting/relay_setting/kind`,
                    method: 'get'
                }).then(res => {
                    this.formFolderSetting.kindData = {
                        id: 0,
                        key: '',
                        options : res.ctt,
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            clear(){
                this.formFolderSetting.folderTree.body = []
                this.formFolderSetting.folderTree.selected = null
                // this.formFolderSetting.workflowData = null
                // this.formFolderSetting.deptData = null
                // this.formFolderSetting.kindData = null
                // this.formFolderSetting.dateData = null
            },
            close(){
                this.clear()
                this.formFolderSetting.visible = false
            }
        }
    }
</script>

<style scoped>

</style>

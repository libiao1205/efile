<template>
    <div>
        <!--新增文件夹界面-->
        <el-dialog :title="formAddFolder.title" :visible.sync="formAddFolder.visible" :close-on-click-modal="false" width="400px">
            <el-form :model="formAddFolder.data" label-width="120px" :rules="formAddFolder.rules" ref="formAddFolder">
                <el-form-item label="上级文件夹名称" prop="parentname">
                    <el-input v-model="formAddFolder.data.parentName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="文件夹名称" prop="name">
                    <el-input v-model="formAddFolder.data.name" ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleFolderAddSubmit" :loading="formAddFolder.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="clear">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--新增文件夹界面-->
        <!--文件夹上传文件大小界面-->
        <el-dialog :title="formSizeFolder.title" :visible.sync="formSizeFolder.visible" :close-on-click-modal="false" width="400px">
            <el-form :model="formSizeFolder.data" label-width="120px" :rules="formSizeFolder.rules" ref="formSizeFolder">
                <el-form-item label="文件夹名称" prop="parentname">
                    <el-input v-model="formSizeFolder.data.name" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="文件大小(MB)" prop="name">
                    <el-input v-model="formSizeFolder.data.size" ></el-input>
                </el-form-item>
            </el-form>
            <div style="margin: 15px 0 0 15px; padding: 5px; display: flex; height: 70px;border-radius:5px;border: 1px solid #dcdfe6;">
                    <span style="color: #606266;font-weight:bold;">说明：<br/>
                        上传文件大小限定最大为1024MB。<br/>
                    </span>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-add-new" @click.native="handleFolderSizeSubmit" :loading="formSizeFolder.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="clear">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--文件夹上传文件大小界面-->
        <!--编辑界面-->
        <el-dialog :title="formEditFolder.title" :visible.sync="formEditFolder.visible" :close-on-click-modal="true" width="800px">
            <el-form :model="formEditFolder.data" label-width="140px" :rules="formEditFolder.rules" ref="formEditFolder">
                <el-row :gutter="30" style="min-height: 500px; height:500px;">
                    <el-col :span="12">
                        <el-form-item label="原名称" prop="orgName">
                            <el-input v-model="formEditFolder.data.orgName" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="输入新的名称" prop="name">
                            <el-input v-model="formEditFolder.data.name" auto-complete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="原上级文件夹" prop="orgParentName">
                            <el-input v-model="formEditFolder.data.orgParentName" :disabled="true"></el-input>
                        </el-form-item>
                        <el-form-item label="选择新的上级文件夹" prop="parentName">
                            <el-input v-model="formEditFolder.data.parentName" :disabled="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <div style="box-shadow:0 0 1px #ccc;">
                            <el-scrollbar style="height:450px;">
                                <el-tree class="mr20" ref="folderTree" v-if="formEditFolder.visible" :data="folderTree.body" :props="folderTree.props"
                                         v-loading="folderTree.loading" highlight-current :accordion="true"
                                         :expand-on-click-node="false" @node-click="folderSelect" node-key="id" :default-expanded-keys="defaultKey">
                                    <span class="custom-tree-node" slot-scope="{node,data}">
                                        <div v-if="data.id === 1" >
                                            <app-svg iconClass="zhuye11"></app-svg>
                                            <span :title="node.label" class="span-ellipsis">{{node.label}}</span>
                                        </div>
                                        <div v-else>
                                            <app-svg iconClass="wenjianjia"></app-svg>
                                            <span :title="node.label" class="span-ellipsis">{{node.label}}</span>
                                        </div>
                                    </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" icon="iconfont icon-bianji2" @click.native="handleFolderEditSubmit" :loading="formEditFolder.loading">
                    <span style="padding-left: 10px;"><b>确定</b></span>
                </el-button>
                <el-button icon="iconfont icon-jieshu" @click.native="clear">
                    <span style="padding-left: 10px;"><b>关闭</b></span>
                </el-button>
            </div>
        </el-dialog>
        <!--编辑界面-->
    </div>
</template>

<script>
    export default {
        name: "EditFolder",
        props: {
            addFolder: {
                type: Function,
                default: null
            },
            editFolder: {
                type: Function,
                default: null
            },
            deleteFolder: {
                type: Function,
                default: null
            },
        },
        data() {
            return {
                /*文件夹树 Start*/
                defaultKey:[1],
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

                formAddFolder:{
                    title: '新建文件夹',
                    visible: false,
                    loading: false,
                    data: {
                        id : 0,
                        name : '',
                        parentId : 0,
                        parentName : '',
                    },
                    rules: {
                        name: [
                            { required: true, message: '请输入文件夹名', trigger: 'blur' }
                        ]
                    },
                },

                formSizeFolder:{
                    title: '编辑可上传文件大小',
                    visible: false,
                    loading: false,
                    data: {
                        id : 0,
                        name : '',
                        size : 0,
                    },
                    rules: {
                        name: [
                            { required: true, message: '请输入上传文件大小', trigger: 'blur' }
                        ]
                    },
                },

                formEditFolder:{
                    title:'编辑文件夹',
                    visible: false,
                    loading: false,
                    data:{
                        id:0,
                        orgName:'',
                        name:'',
                        suffix:'',
                        orgParentId:0,
                        orgParentName:'',
                        parentId:0,
                        parentName: '',
                    },
                    rules: {
                        name: [
                            {required: true, message: '请输入新的名称', trigger: 'blur'}
                        ],
                    },
                },
            }
        },
        methods:{
            clear(){
                this.folderTree.body = []
                this.folderTree.selected = null
                this.formAddFolder.visible = false
                this.formEditFolder.visible = false
                this.formSizeFolder.visible = false
            },
            handleFolderAdd: function (node) {
                this.formAddFolder.visible = true;
                this.formAddFolder.data = {
                    id: 0,
                    name: '',
                    parentId: node.data.id,
                    parentName: node.data.name,
                }
                if (this.$refs.formAddFolder) this.$refs.formAddFolder.resetFields()
            },
            handleFolderEdit: function (node) {
                this.formEditFolder.data = {
                    id:node.data.id,
                    orgName:node.data.name,
                    name:node.data.name,
                    suffix:'',
                    orgParentId:node.parent.data.id,
                    orgParentName:node.parent.data.name,
                    parentId:node.parent.data.id,
                    parentName: node.parent.data.name,
                }
                this.printFolderTree()
                this.formEditFolder.visible = true
            },
            handleFolderSize: function (node) {
                let size = 0
                if (!node.data.fileSize || node.data.fileSize === 0){
                    size = this.$maxFileSize
                }else{
                    size = node.data.fileSize
                }
                this.formSizeFolder.data = {
                    id: node.data.id,
                    name: node.data.name,
                    size:size,
                }
                this.formSizeFolder.visible = true;
                if (this.$refs.formSizeFolder) this.$refs.formSizeFolder.resetFields()
            },
            handleFolderAddSubmit: function () {
                this.$refs.formAddFolder.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认新增文件夹吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formAddFolder.loading = true
                            let pram = Object.assign({}, this.formAddFolder.data)
                            this.$axios({
                                url: '/admin/folder',
                                method: 'post',
                                data: pram
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("新增成功")
                                    this.$refs.formAddFolder.resetFields()
                                    this.formAddFolder.visible = false
                                    res.ctt.key = 'folder-' + res.ctt.id
                                    res.ctt.kind = 'folder'
                                    this.addFolder(res)
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formAddFolder.loading = false
                            })
                        })
                    }
                })
            },
            handleFolderEditSubmit: function () {
                this.$refs.formEditFolder.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交编辑吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formEditFolder.loading = true
                            let param = Object.assign({}, this.formEditFolder.data);
                            this.$axios({
                                url: '/user/folder',
                                method: 'put',
                                data: param
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("编辑成功")
                                    this.$refs.formEditFolder.resetFields()
                                    this.formEditFolder.visible = false
                                    this.editFolder(param)
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formEditFolder.loading = false
                            })
                        })
                    }
                })
            },
            handleFolderSizeSubmit: function () {
                this.$refs.formSizeFolder.validate((valid) => {
                    if (valid) {
                        if (!this.formSizeFolder.data){
                            this.$message.error("请设定一个正整数！")
                            return
                        }
                        let r = /^\+?[1-9][0-9]*$/;
                        if (!r.test(this.formSizeFolder.data.size)){
                            this.$message.error("请设定一个正整数！")
                            return
                        }
                        if (this.formSizeFolder.data.size > 1024){
                            this.$message.error("设定大小请不要超过1024MB！")
                            return
                        }
                        this.$confirm('确认提交编辑吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formSizeFolder.loading = true
                            let param = Object.assign({}, this.formSizeFolder.data);
                            this.$axios({
                                url: `/admin/folder/${param.id}/filesize/${param.size}`,
                                method: 'put',
                                data: param
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("编辑成功")
                                    this.$refs.formSizeFolder.resetFields()
                                    this.formSizeFolder.visible = false
                                    //this.editFolder(param)
                                }else{
                                    this.$message.error(res.msg)
                                }
                                this.formSizeFolder.loading = false
                            })
                        })
                    }
                })
            },
            handleFolderDelete: function (node) {
                this.$confirm('确认删除该文件夹吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para =  Object.assign({}, node.data)
                    this.$axios({
                        url: '/admin/folder',
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("删除成功")
                            this.deleteFolder(para)
                        }else{
                            this.$message.error(res.msg)
                        }
                    })
                }).catch(() => {

                });
            },
            printFolderTree: function () {
                this.$axios({
                    url: `/admin/folder/tree`,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt)
                    data[0].isRoot = true
                    this.folderTree.body = data

                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderSelect(data) {
                this.folderTree.selected = data
                this.formEditFolder.data.parentId = data.id
                this.formEditFolder.data.parentName = data.name
            },
        }
    }
</script>

<style scoped>
    .span-ellipsis {
        min-width: 100%;
        width: 100%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
</style>

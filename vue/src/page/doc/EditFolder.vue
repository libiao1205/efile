<template>
    <div>
        <!--文件夹新建界面-->
        <el-dialog :title="formFolder.title" :visible.sync="formFolder.visible" :close-on-click-modal="true" width="500px">
            <el-form :model="formFolder.data" label-width="120px" :rules="formFolder.rules" ref="formFolderData">
                <el-form-item label="上级文件夹名称" prop="parentName">
                    <el-input v-model="formFolder.data.parentName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="新建文件夹名称" prop="name">
                    <el-input v-model="formFolder.data.name" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click.native="handleAddFolderSubmit" :loading="formFolder.loading">确定</el-button>
                <el-button @click.native="formFolder.visible = false">取消</el-button>
            </div>
        </el-dialog>
        <!--文件夹新建界面-->

        <!--重命名界面-->
        <el-dialog :title="formReNameFolder.title" :visible.sync="formReNameFolder.visible" :close-on-click-modal="true" width="500px">
            <el-form :model="formReNameFolder.data" label-width="120px" :rules="formReNameFolder.rules" ref="formReNameData">
                <el-form-item label="原名称" prop="orgName">
                    <el-input v-model="formReNameFolder.data.orgName" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="输入新的名称" prop="name">
                    <el-input v-model="formReNameFolder.data.name" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click.native="handleReNameSubmit" :loading="formReNameFolder.loading">重命名</el-button>
                <el-button @click.native="formReNameFolder.visible = false">取消</el-button>
            </div>
        </el-dialog>
        <!--重命名界面-->

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
                                <el-tree ref="folderTree" v-if="formEditFolder.visible" :data="folderTree.body" highlight-current v-loading="folderTree.loading" :props="folderTree.props"
                                         :default-expand-all="false" :expand-on-click-node="false" node-key='id' :default-expanded-keys="[1]"
                                         lazy :load="printFolderTree" @node-click="folderSelect">
                                <span class="custom-tree-node"  slot-scope="{node, data}">
                                    <div v-if="data.parentId === 0">
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
                <el-button type="primary" @click.native="handleEditFolderSubmit" :loading="formEditFolder.loading">确定</el-button>
                <el-button @click.native="clear">取消</el-button>
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
            renameFolder: {
                type: Function,
                default: null
            },
            deleteFolder: {
                type: Function,
                default: null
            },
            deleteFolders: {
                type: Function,
                default: null
            }
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

                formFolder:{
                    title:'新建文件夹',
                    data:{
                        id: 0 ,
                        name:'',
                        parentId:'',
                        parentName: '',
                    },
                    visible: false,
                    loading: false,
                    rules: {
                        name: [
                            {required: true, message: '请输入文件夹名', trigger: 'blur'}
                        ],
                    },
                },
                formEditFolder:{
                    title:'编辑文件夹',
                    data:{
                        id:0,
                        kind:'',
                        orgName:'',
                        name:'',
                        suffix:'',
                        orgParentId:0,
                        orgParentName:'',
                        parentId:0,
                        parentName: '',
                    },
                    visible: false,
                    loading: false,
                    rules: {
                        name: [
                            {required: true, message: '请输入新的名称', trigger: 'blur'}
                        ],
                    },
                },
                formReNameFolder:{
                    title:'文件夹重命名',
                    data:{
                        id:0,
                        kind:'',
                        orgName:'',
                        name:'',
                        suffix:'',
                        parentId:'',
                    },
                    visible: false,
                    loading: false,
                    rules: {
                        name: [
                            {required: true, message: '请输入新的名称', trigger: 'blur'}
                        ],
                    },
                }
            }
        },
        methods:{
            clear(){
                this.folderTree.body = []
                this.folderTree.selected = null
                this.formEditFolder.visible = false
            },
            //新建文件夹
            handleAddFolderSubmit() {
                this.$refs.formFolderData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formFolder.loading = true
                            let para = Object.assign({}, this.formFolder.data)
                            this.$axios({
                                url: '/user/folder',
                                method: 'post',
                                data: para
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("提交成功")
                                    // this.formFolder.loading = false
                                    this.formFolder.visible = false
                                    this.addFolder(res)

                                    // this.$refs.formFolder.resetFields()
                                    // this.formFolder.visible = false
                                    // this.addFolder(res)
                                }else {
                                    this.$message.error(res.msg)
                                }
                                this.formFolder.loading = false
                            })
                        })
                    }
                })
            },
            //新建文件夹界面
            handleAddFolder(selectFolder) {
                this.formFolder.visible = true
                this.formFolder.data = {
                    id: 0 ,
                    name:'',
                    parentName:selectFolder.name,
                    parentId:selectFolder.id,
                }
                if (this.$refs.formFolderData) this.$refs.formFolderData.resetFields()
            },
            //编辑文件夹界面
            handleEditFolder(selectFolder,row) {
                this.formEditFolder.title = row.kind === 'folder' ?  '文件夹重命名 文件夹路径编辑':'文件重命名 文件路径编辑'
                this.formEditFolder.data = {
                    id: row.id,
                    orgName:row.name,
                    name:row.name,
                    kind:row.kind,
                    suffix:row.suffix,
                    orgParentId:selectFolder.id,
                    orgParentName:selectFolder.name,
                    parentId:selectFolder.id,
                    parentName: selectFolder.name,
                }
                this.formEditFolder.visible = true;
            },
            //编辑文件夹
            handleEditFolderSubmit: function () {
                this.$refs.formEditFolder.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交编辑吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formEditFolder.loading = true
                            let param = Object.assign({}, this.formEditFolder.data);
                            this.$axios({
                                url: `user/${param.kind === 'folder'?'folder':'file'}`,
                                method: 'put',
                                data: param
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("编辑成功")
                                    this.$refs.formEditFolder.resetFields()
                                    this.folderTree.body = []
                                    this.folderTree.selected = null
                                    this.formEditFolder.visible = false
                                    this.renameFolder(param)
                                }else {
                                    this.$message.error(res.msg)
                                }
                                this.formEditFolder.loading = false
                            })
                        })
                    }
                })
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
                    url: `/user/folder/${id}/children`,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        let permissionBody = [];
                        for( let i = 0; i < res.ctt.length; i++){
                            //if (this.isShowAll === false && res.ctt[i].disabled === true ) continue
                            if (res.ctt[i].permissionMap.visible){
                                permissionBody.push(res.ctt[i])
                            }
                        }
                        resolve(permissionBody)
                        this.folderTree.loading = false
                        this.$nextTick(() => {
                            let list = []
                            list.push(id)
                            this.defaultKey = list
                            this.$refs.folderTree.setCurrentKey(id)
                            this.folderTree.selected = this.$refs.folderTree.getCurrentNode()
                        });
                    }else{
                        resolve([])
                        this.$message.error(res.msg)
                        this.folderTree.loading = false
                        // this.refresh();
                    }

                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            folderSelect(data) {
                this.folderTree.selected = data
                this.formEditFolder.data.parentId = data.id
                this.formEditFolder.data.parentName = data.name
            },

            //显示重命名界面
            handleReName(row) {
                this.formReNameFolder.title = row.kind === 'folder' ?  '文件夹重命名':'文件重命名'
                this.formReNameFolder.data = {
                    id: row.id ,
                    orgName:row.name ,
                    name:'',
                    kind:row.kind,
                    suffix:row.suffix,
                    parentId:row.parentId,
                }
                this.formReNameFolder.visible = true;
            },
            //重命名
            handleReNameSubmit: function () {
                this.$refs.formReNameData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认重新命名吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formReNameFolder.loading = true
                            let para = Object.assign({}, this.formReNameFolder.data);
                            this.$axios({
                                url: `user/${para.kind === 'folder'?'folder':'file'}`,
                                method: 'put',
                                data: para
                            }).then((res) => {
                                if(res.c === 0){
                                    this.$message.success("重命名成功")
                                    this.$refs.formReNameData.resetFields()
                                    this.formReNameFolder.visible = false
                                    this.renameFolder(para)
                                }else {
                                    this.$message.error(res.msg)
                                }
                                this.formReNameFolder.loading = false
                            })
                        })
                    }
                })
            },
            //删除
            handleDeleteSubmit: function (row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `user/${para.kind === 'folder'?'folder':'file'}`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("删除成功")
                            this.deleteFolder(para);
                        }else {
                            this.$message.error(res.msg)
                        }
                    })
                }).catch(() => {
                });
            },
            //复数删除
            handleMultiDeleteSubmit:function (multipleSelection){
                if (multipleSelection.length === 0){
                    this.$message.error("没有选择需要删除的文件或者文件夹，请重新选择！")
                    return
                }
                this.$confirm('确认删除这些记录吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = multipleSelection;
                    this.$axios({
                        url: `user/docs`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("删除成功")
                            this.deleteFolders(para);
                        }else {
                            this.$message.error(res.msg)
                        }
                    })
                }).catch(() => {
                });
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

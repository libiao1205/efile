<template>
    <div>
        <!--上传文件夹界面-->
        <el-dialog :title="formFolder.title" :visible.sync="formFolder.visible" :close-on-click-modal="false"  width="900px">
            <el-row :gutter="20">
                <el-col :span="14">
                    <div style="padding-bottom: 10px;font-style: italic; font-size:12px;color:#999999">为保证上传的稳定，整个目录大小请不要超过{{this.upload.size}}MB</div>
                    <input ref="folderSelect" v-if="folderClear" id="openFolder" type="file"  style=display:none webkitdirectory @change.stop="onChangeStopProcess()" size="0"/>
                    <el-button type="success" size="small"  @click="handleOpenFolder">选择文件夹</el-button>
                    <div style="height: 10px"></div>

                    <el-table :data="formFolder.fileList" :show-header="false" :height="200"
                              class="custom-table-row" style="width: 100%" >
                        <el-table-column :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <div>
                                    <p>{{scope.row.name}}</p>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" width="200px">
                            <template slot-scope="scope">
                                <el-progress :percentage='scope.row.progress' :status="scope.row.progress === -100 ? 'exception':(scope.row.progress === 100 ? 'success':'')"></el-progress>
                                <!--<div>-->
                                <!--<el-button type="text" size="mini" icon="el-icon-delete" circle></el-button>-->
                                <!--</div>-->
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
                <el-col :span="10">
                    <el-table ref="settingTable" class="custom-table-cell" v-loading="settingTable.loading" :data="settingTable.body"
                              :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                              height="300px" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="50"></el-table-column>
                        <el-table-column prop="name" label="选择通知成员组" >
                            <template slot-scope="scope">
                                <div>
                                    <i class="iconfont icon-geren"></i>
                                    <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="name" width="90px">
                            <template slot="header" slot-scope="scope">
                                <el-tooltip class="item" effect="dark" content="新增用户组" placement="right">
                                    <el-button class="btnAdd" type="text" size="mini" icon="el-icon-circle-plus-outline"
                                               style="float:right;padding:5px"
                                               @click = "handleEditSendUser()"
                                               circle></el-button>
                                </el-tooltip>
                            </template>
                            <template slot-scope="scope">
                                <div>
                                    <el-tooltip class="item" effect="dark" content="编辑用户组" placement="right">
                                        <el-button class="btnEdit" type="text" size="mini" icon="el-icon-edit"
                                                   @click="handleEditSendUser(scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="删除用户组" placement="right">
                                        <el-button class="btnDel" type="text" size="mini" icon="el-icon-delete"
                                                   @click="handleDeleteSendUser(scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </div>
                            </template>
                        </el-table-column>

                    </el-table>
                </el-col>
            </el-row>

            <el-form :model="formFolder.data" ref="formFolderData">
                <el-row>
                    <el-col :span="6"><div class="grid-content bg-purple-dark">
                            <P style=" line-height: 50px; color:#2F5A74; font-size:16px">上传目录：</P>
                    </div></el-col>
                    <el-col :span="18"><div class="grid-content bg-purple-dark">
                        <P style="line-height: 50px; color:#E51C23; font-size:20px">{{formFolder.data.folderName}}</P>
                    </div></el-col>
                </el-row>
                <el-row>
                    <el-col :span="24"><div class="grid-content bg-purple-dark">
                        <el-form-item label="版本描述" prop="comment">
                            <el-input
                                type="textarea"
                                :rows="2"
                                placeholder="请输入内容"
                                v-model="formFolder.data.comment">
                            </el-input>
                        </el-form-item>
                    </div></el-col>
                </el-row>
            </el-form>

            <el-dialog title="通知用户组编辑" :visible.sync="settingTable.settingDetailTable.visible" :modal="false" @open="printDept" width="1000px">
                <el-form :model="settingTable.settingDetailTable.data" label-width="80px" :rules="settingTable.settingDetailTable.rules" ref="settingDetail">
                    <el-row :gutter="20" style="min-height: 460px; height:460px;">
                        <el-col :span="8">
                            <div style="box-shadow:0 0 1px #ccc;height:460px;">
                                <el-scrollbar>
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

                        <el-col :span="16">
                            <el-row >
                                <el-form-item label-width="120px" label="编辑用户组名称" prop="name">
                                    <el-input v-model="settingTable.settingDetailTable.data.name" auto-complete="off"></el-input>
                                </el-form-item>
                            </el-row>
                            <el-row  :gutter="20" >
                                <el-col :span="12">
                                    <el-table ref="memberTable" class="custom-table-cell" v-loading="memberTable.loading" :data="memberTable.body"
                                              :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                                              height="400px">
                                        <el-table-column prop="name" label="用户姓名" >
                                            <template slot-scope="scope">
                                                <div>
                                                    <i class="iconfont icon-geren"></i>
                                                    <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                                </div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="name" label="操作" width="60px">
                                            <template slot-scope="scope">
                                                <div>
                                                    <el-tooltip class="item" effect="dark" content="增加成员" placement="right">
                                                        <el-button class="btnAdd" type="text" size="mini"
                                                                   icon="el-icon-d-arrow-right"
                                                                   @click="addUserToGroup(scope.row)"
                                                                   circle></el-button>
                                                    </el-tooltip>
                                                </div>
                                            </template>
                                        </el-table-column>

                                    </el-table>
                                </el-col>

                                <el-col :span="12">
                                    <el-table ref="selectedTable" class="custom-table-cell" v-loading="selectedTable.loading" :data="selectedTable.body"
                                              :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                                              height="400px">
                                        <el-table-column prop="name" label="已选择用户">
                                            <template slot-scope="scope">
                                                <div>
                                                    <i class="iconfont icon-geren"></i>
                                                    <span style="padding-left: 5px;">{{scope.row.name}}</span>
                                                </div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="name" label="操作" width="60px">
                                            <template slot-scope="scope">
                                                <div>
                                                    <el-tooltip class="item" effect="dark" content="删除成员" placement="right">
                                                        <el-button class="btnDel" type="text" size="mini"
                                                                   icon="iconfont icon-yonghushanchu"
                                                                   @click="removeUserFromGroup(scope.row)"
                                                                   circle></el-button>
                                                    </el-tooltip>
                                                </div>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-col>
                            </el-row>
                        </el-col>

                    </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click = "handleSaveSendUser()" type="primary">确定</el-button>
                    <el-button @click = "handleCloseSendUser()">关闭</el-button>
                </div>
            </el-dialog>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click.native="handleUploadFolderSubmit" :loading="formFolder.loading">上传</el-button>
                <el-button @click.native="formFolder.visible = false">关闭</el-button>
            </div>
        </el-dialog>
        <!--上传文件夹界面-->
    </div>
</template>

<script>
    export default {
        name: "UploadFolder",
        /*pdf路径*/
        props: {
            uploaded: {
                type: Function,
                default: null
            },
        },
        computed: {
            token() {
                return {Authorization :'Bearer ' + this.$store.state.auth.token}
            }
        },
        data(){
            return{
                folderClear: true,
                parentFolder:{
                    files:[]
                },
                /*文件上传服务器路径 Start*/
                upload:{
                    url:this.$upload,
                    size: this.$maxFileSize
                },
                /*文件上传服务器路径 End*/
                /*通知用户设定 Start*/
                settingTable:{
                    height: 0,
                    body: [],
                    total: 0,
                    loading: false,
                    selected:null,
                    multipleSelection: [],
                    settingDetailTable:{
                        isNew:true,
                        title:'编辑送信用户组',
                        data:{
                            id:0,
                            name:'',
                        },
                        rules:{
                            name: [
                                { required: true, message: '请输入用户组名称', trigger: 'blur' }
                            ]
                        },
                        visible:false,
                    },
                    submit:{
                        id:0,
                        name:'',
                        kind:'',
                        status:0,
                        details:[],
                    },
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
                memberTable:{
                    height: 0,
                    body: [],
                    total: 0,
                    loading: false,
                    selected:null,
                },
                selectedTable:{
                    height:0,
                    body:[],
                    loading:false,
                    selected:null,
                    changeSelectedDept:false,
                },
                paginationData: {
                    pageSize: 1000,
                    total: 0,
                    curPage:1
                },
                /*通知用户设定 End*/
                /*新建、更新文件数据处理 Start*/
                formFolder: {
                    title:'',
                    action:"",
                    visible: false,
                    loading: false,
                    data:{
                        id: 0 ,
                        fileName:'',
                        folderId:0,
                        folderName:'',
                        comment:'',
                    },
                    fileList: [{
                        name:'',
                        data:null,
                        progress:0,
                    }],
                },
                /*新建、更新文件数据处理 End*/
            }
        },
        methods:{
            //文件夹上传画面
            handleUploadFolder(selectFolder,size) {
                this.upload.size = size;
                this.formFolder.title = '文件夹上传'
                this.formFolder.isNew = true
                this.formFolder.data = {
                    id: 0 ,
                    fileName:'',
                    folderName:selectFolder.name,
                    folderId:selectFolder.id,
                    comment: '',
                }
                this.printUserSetting()

                if (this.$refs.formFolder) this.$refs.formFolder.resetFields()
                if (this.$refs.uploadFolder) this.$refs.uploadFolder.clearFiles()
                this.formFolder.fileList = [];
                this.formFolder.visible = true
            },
            //点击上传文件夹按钮
            handleOpenFolder(){
                this.folderClear = false;
                this.$nextTick(() => {
                    this.folderClear = true;
                });

                setTimeout(() => {
                    document.getElementById('openFolder').click();
                }, 500);
            },
            //选择要上传文件夹后回调
            onChangeStopProcess(){
                this.formFolder.fileList = [];
                for (let i = 0; i < this.$refs.folderSelect.files.length; i++) {
                    let file = {
                        name:this.$refs.folderSelect.files[i].name,
                        data:this.$refs.folderSelect.files[i],
                        percentage:0,
                    }
                    this.formFolder.fileList.push(file)
                }
            },
            //更新进度条
            handleUpdateProgress(file,progress){
                this.$forceUpdate()
                this.$set(this.formFolder.fileList.filter(_file => _file.name === file.name)[0],"progress",progress)
            },
            //开始文件夹上传
            async handleUploadFolderSubmit(){
                if (!this.folderValidate(this.formFolder.fileList)) return
                let send = this.createSendUserSetting()
                let result = 0;
                this.formFolder.loading = true
                for (let i = 0; i < this.formFolder.fileList.length; i++) {
                    let formData = new FormData()
                    formData.append('file', this.formFolder.fileList[i].data)
                    formData.append('id', this.formFolder.data.folderId)
                    formData.append('send', send)
                    formData.append('comment', this.formFolder.data.comment)
                    result = result + await this.onUploadFolderProcess(this.formFolder.fileList[i].data,formData,progress =>{
                        this.handleUpdateProgress(this.formFolder.fileList[i].data,progress)
                    })
                }
                if (result === 0){
                    this.$message.success("文件夹上传完成！")
                    this.uploaded()
                    this.formFolder.loading = false
                    this.formFolder.visible = false
                }else{
                    this.$message.warning("文件夹上传完成！但可能有部分文件上传失败，请确认。")
                    this.uploaded()
                    this.formFolder.loading = false
                    this.formFolder.visible = false
                }
            },
            //文件夹上传过程
            onUploadFolderProcess(file,formData,callbackProgress){
                const http = require('http');
                const p = new Promise((resolve, reject) => {
                    setTimeout(() => {
                        this.$axios({
                            url: `${this.upload.url}/user/folder/new`,
                            method: 'post',
                            data: formData,
                            headers: {
                                'Content-Type': 'multipart/form-data;boundary = ' + new Date().getTime()
                            },
                            httpAgent: new http.Agent({ keepAlive: true }),

                            onUploadProgress: progressEvent => {
                                let complete = (progressEvent.loaded / progressEvent.total * 100 | 0)
                                let progress = parseInt(complete)
                                callbackProgress(progress)
                            },

                        }).then(() => {
                            resolve(0)
                        }).catch(() => {
                            callbackProgress(-100)
                            resolve(1)
                            // reject(1)
                        });
                    }, 500)
                })
                return p
            },
            createSendUserSetting(){
                let requestParam = ''
                for(let i = 0; i < this.settingTable.multipleSelection.length ; i++){
                    requestParam += this.settingTable.multipleSelection[i].id + '_'
                }
                return requestParam
            },
            //取得用户设定
            printUserSetting(){
                this.$axios({
                    url: `/user/setting/senduser`,
                    method: 'get'
                }).then(res => {
                    this.settingTable.body = res.ctt
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //启动编辑送信用户组画面
            handleEditSendUser:function(row){
                this.settingTable.settingDetailTable.visible = true
                this.settingTable.settingDetailTable.isNew = true
                if (row){
                    this.settingTable.settingDetailTable.data.id = row.id
                    this.settingTable.settingDetailTable.data.name = row.name
                    this.settingTable.settingDetailTable.isNew = false
                    this.$axios({
                        url: `/user/setting/${row.id}/detail`,
                        method: 'get'
                    }).then(res => {
                        this.selectedTable.body = res.ctt
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }
            },
            //关闭编辑送信用户组画面
            handleCloseSendUser(){
                this.settingTable.settingDetailTable.data.id = 0
                this.settingTable.settingDetailTable.data.name = ''
                this.memberTable.body = []
                this.memberTable.selected = null
                this.selectedTable.body = []
                this.selectedTable.selected = null
                this.settingTable.settingDetailTable.visible = false
            },
            //保存送信用户组
            handleSaveSendUser(){
                if (!this.settingTable.settingDetailTable.data.name.trim()){
                    this.$message.error("请输入用户组名称！")
                    return
                }
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.settingTable.settingDetailTable.data.name.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                if ((!this.selectedTable.body)||this.selectedTable.body.length === 0) {
                    this.$message.error("请选择用户组成员！")
                    return
                }

                let details = [];
                for( let i = 0; i < this.selectedTable.body.length; i++){
                    details.push({
                        code:this.selectedTable.body[i].id,
                        value:this.selectedTable.body[i].name,
                    })
                }
                this.settingTable.submit ={
                    id:this.settingTable.settingDetailTable.data.id,
                    name:this.settingTable.settingDetailTable.data.name,
                    kind:'senduser',
                    status:1,
                    details:details,
                };
                let msg = ''
                let method = ''
                if (this.settingTable.settingDetailTable.isNew){
                    msg = "确认新增["+ this.settingTable.submit.name +"]用户组吗?"
                    method = 'post'
                }else{
                    msg = "确认编辑["+ this.settingTable.submit.name +"]用户组吗?"
                    method = 'put'
                }
                this.$confirm(msg, '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        url: `/user/setting`,
                        method: method,
                        data: this.settingTable.submit
                    }).then((res) => {
                        if (res.c === 0) {
                            this.$message.success("新增用户组成功！")
                        } else {
                            this.$message.error(res.msg)
                        }
                        this.printUserSetting()
                        this.handleCloseSendUser()
                    })
                }).catch(() => {

                });

            },
            //删除送信用户组
            handleDeleteSendUser:function(row){
                this.$confirm('确认删除该用户组?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `/user/setting`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message({
                                message: '用户组已删除',
                                type: 'success'
                            });
                        }else{
                            this.$message.error(res.msg)
                        }
                        this.printUserSetting()
                    })
                }).catch(() => {

                });
            },
            //取得部门树
            printDept: function(){
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
            //点击部门节点
            selectDept(data) {
                this.deptTree.selected = data
                this.printUser(data)
                this.selectedTable.changeSelectedDept = true
            },
            //取得部门用户
            printUser(data) {
                this.memberTable.loading = true
                this.$axios({
                    url: `/admin/dept/${data.id}/users/${this.paginationData.curPage}-${this.paginationData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.memberTable.body = res.ctt.list
                    this.paginationData.total = res.ctt.total
                    this.memberTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //追加用户到用户组
            addUserToGroup(row) {
                for (var i=0;i<this.selectedTable.body.length;i++) {
                    if (this.selectedTable.body[i].id === row.id){
                        this.$message.warning("成员已在组内，不能再次添加！")
                        return
                    }
                }
                this.selectedTable.body.push(row)
            },
            //把用户从用户组中删除
            removeUserFromGroup(row){
                for (var i=0;i<this.selectedTable.body.length;i++) {
                    if (this.selectedTable.body[i].id === row.id){
                        this.selectedTable.body.splice(i, 1);
                        return
                    }
                }
            },
            handleSelectionChange(val) {
                this.settingTable.multipleSelection = val;
            },
            checkFileSize(file) {
                return file.size / 1024 / 1024 <= this.upload.size
            },
            folderValidate(filelist) {
                if (filelist.length ===0)
                {
                    this.$message.error("选择的目录中没有发现有任何文件！")
                    return false
                }
                let fullSize = 0;
                for (let i = 0; i < filelist.length; i++) {
                    fullSize = fullSize + filelist[i].data.size
                    if (!this.checkFileSize(filelist[i].data)) {
                        this.$message.error(`上传文件大小不能超过 ${this.upload.size}MB!  文件名：` + filelist[i].name);
                        return false
                    }
                }
                let mbSize = fullSize / 1024 / 1024
                if (mbSize > 1024){
                    this.$message.error(`上传文件的总大小不能超过1024MB! `);
                    return false
                }
                return true
            }
        }
    }
</script>

<style lang="scss">
    .custom-table-row {
        .el-table__body tr,
        .el-table__body td {
            padding: 0;
            height: 30px;
            font-size:14px;
        }
    }
    .btn-custom-cancel {
        float: right;
        margin-left: 10px;
    }

</style>

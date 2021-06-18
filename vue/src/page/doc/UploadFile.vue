<template>
    <div>
        <!--上传文件界面-->
        <el-dialog :title="formFile.title" :visible.sync="formFile.visible" :close-on-click-modal="false"  width="400px">
            <el-form :model="formFile.data" ref="formFileData">
                <el-row :gutter="20">
                    <el-col :span="12"><div class="grid-content bg-purple-dark">
                        <el-upload
                            class="upload-demo"
                            drag
                            multiple
                            ref="uploadFile"
                            :auto-upload="false"
                            :headers = "token"
                            :file-list="formFile.fileList"
                            :action="formFile.action"
                            :on-change	="onFileChange"
                            :on-remove="onFileRemove"
                            :onSuccess="onUploadSuccess"
                            :onError="onUploadError"
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                            <div class="el-upload__tip" slot="tip">文件大小不能超过{{this.upload.size}}MB</div>
                        </el-upload>
                    </div></el-col>
                    <!-- <el-col :span="12">
                        <el-table ref="settingTable" class="custom-table-cell" v-loading="settingTable.loading" :data="settingTable.body"
                                  :cell-style="{padding:'5px'}" :header-cell-style="{padding: '2px'}" highlight-current-row border
                                  height="200px" @selection-change="handleSelectionChange">
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
                    </el-col> -->
                </el-row>
                <el-row>
                    <el-col :span="6">
                        <div class="grid-content bg-purple-dark">
                            <P style=" line-height: 50px; color:#2F5A74; font-size:16px">上传目录：</P>
                        </div></el-col>
                    <el-col :span="18">
                        <div class="grid-content bg-purple-dark">
                            <P style="line-height: 50px; color:#E51C23; font-size:20px">{{formFile.data.folderName}}</P>
                            <!--<el-input v-model="formFileData.folderName" auto-complete="off"></el-input>-->
                        </div></el-col>
                </el-row>
                <el-row v-if="!formFile.isNew" >
                    <el-col :span="24">
                        <div class="grid-content bg-purple-dark">
                            <P style=" line-height: 40px; color:#2F5A74; font-size:16px">文件名：</P>
                            <P style="line-height: 40px; color:#20A0FF; font-size:20px">{{formFile.data.fileName}}</P>
                            <!--<el-input v-model="formFileData.folderName" auto-complete="off"></el-input>-->
                        </div></el-col>
                </el-row>
                <el-row>
                    <el-col :span="24"><div class="grid-content bg-purple-dark">
                        <el-form-item label="版本描述" prop="name">
                            <el-input
                                type="textarea"
                                :rows="2"
                                placeholder="请输入内容"
                                v-model="formFile.data.comment">
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
                <el-button type="primary" @click.native="handleAddFileSubmit" v-if="formFile.isNew" :loading="formFile.loading">上传</el-button>
                <el-button type="primary" @click.native="handleEditFileSubmit" v-if="!formFile.isNew" :loading="formFile.loading">提交</el-button>
                <el-button @click.native="formFile.visible = false">关闭</el-button>
            </div>
        </el-dialog>
        <!--上传文件界面-->
    </div>
</template>

<script>
    export default {
        name: "UploadFile",
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
                formFile: {
                    isNew: false,
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
                    fileList: [],
                    uploadFileList: [],
                },
                /*新建、更新文件数据处理 End*/
            }
        },
        methods:{
            //文件上传画面
            handleUploadFile (selectFolder,resourceList,size) {
                this.formFile.title = '文档上传'
                this.formFile.isNew = true
                for (let i = 0; i < resourceList.length; i++) {
                    if (resourceList[i].kind === 'file')
                        this.parentFolder.files.push(resourceList[i].name + '.' + resourceList[i].suffix)
                }
                this.upload.size = size;
                this.formFile.data = {
                    id: 0 ,
                    fileName:'',
                    folderName:selectFolder.name,
                    folderId:selectFolder.id,
                    comment: '',
                }

                this.printUserSetting()

                if (this.$refs.formFileData) this.$refs.formFileData.resetFields()
                if (this.$refs.uploadFile) this.$refs.uploadFile.clearFiles()

                this.formFile.visible = true
            },
            createRequestParam(){
                let requestParam = '?send='
                for(let i = 0; i < this.settingTable.multipleSelection.length ; i++){
                    requestParam += this.settingTable.multipleSelection[i].id + '_'
                }
                requestParam += `&comment=${this.formFile.data.comment}`
                return requestParam
            },
            //开始文件上传
            handleAddFileSubmit(){
                if (!this.fileValidate()) return
                let hasSame = false;
                for (let i = 0;i < this.formFile.uploadFileList.length; i++){
                    for (let y = 0; y < this.parentFolder.files.length; y++) {
                        if (this.formFile.uploadFileList[i].name === this.parentFolder.files[y]) {
                            hasSame = true;
                            break;
                        }
                    }
                }

                if (hasSame){
                    this.$confirm('发现有同名的文件在作为新文件上传！<br>选择【版本更新】将更新同名文件的最新版本<br>选择【新文件】将作为新文件上传。', '提示', {
                        dangerouslyUseHTMLString:true,
                        cancelButtonClass: 'btn-custom-cancel',
                        confirmButtonText: '版本更新',
                        cancelButtonText: '新文件',
                        type: 'warning'
                    }).then(() => {
                        this.onUploadFileProcess('确认更新同名文件的最新版本吗？',`${this.upload.url}/user/folder/${this.formFile.data.folderId}/update` + this.createRequestParam())
                    }).catch(() => {
                        this.onUploadFileProcess('确认把同名文件作为新文件上传吗？',`${this.upload.url}/user/folder/${this.formFile.data.folderId}/file` + this.createRequestParam())
                    });
                }else {
                    this.onUploadFileProcess('确认上传新文件吗？',`${this.upload.url}/user/folder/${this.formFile.data.folderId}/file` + this.createRequestParam())
                }
            },
            //文件上传过程
            onUploadFileProcess(message,url) {
                this.formFile.action = url
                this.$refs.formFileData.validate((valid) => {
                    if (valid) {
                        this.$confirm(message, '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formFile.loading = true
                            this.$refs.uploadFile.submit();
                        }).catch(() => {
                        });
                    }
                })
            },
            //文件更新画面
            handleEditFile (row, selectFolder) {
                this.formFile.title = '文档更新'
                this.formFile.isNew = false
                this.formFile.visible = true
                this.formFile.data = {
                    id: row.id ,
                    fileName:row.name + '.' + row.suffix,
                    folderName:selectFolder.name,
                    folderId:selectFolder.id,
                    comment: '',
                }
                this.printUserSetting()
                if (this.$refs.formFileData) this.$refs.formFileData.resetFields()
                if (this.$refs.uploadFile) this.$refs.uploadFile.clearFiles()
            },
            //文件更新画面
            handleEditFile (row) {
                this.formFile.title = '文档更新'
                this.formFile.isNew = false
                this.formFile.visible = true
                this.formFile.data = {
                    id: row.id ,
                    fileName:row.name + '.' + row.suffix,
                    folderName:row.parentPathName,
                    comment: '',
                }
                this.printUserSetting()
                if (this.$refs.formFileData) this.$refs.formFileData.resetFields()
                if (this.$refs.uploadFile) this.$refs.uploadFile.clearFiles()
            },
            //文件更新
            handleEditFileSubmit:function(){
                if (!this.fileValidate()) return
                this.formFile.action = `${this.upload.url}/user/file/${this.formFile.data.id}/upload` + this.createRequestParam()
                this.$refs.formFileData.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认更新同名文件的最新版本吗？', '提示', {
                            cancelButtonClass: 'btn-custom-cancel',
                            type: 'success'
                        }).then(() => {
                            this.formFile.loading = true
                            this.$refs.uploadFile.submit();
                        }).catch(() => {
                        });
                    }
                })
            },
            //选择文件后的回调
            onFileChange(file, fileList) {
                if (!this.checkFileSize(file)) {
                    this.$message.error(`上传文件大小不能超过 ${this.upload.size}MB!`);
                    this.$refs['uploadFile'].uploadFiles.pop()
                    return;
                }
                this.formFile.uploadFileList = fileList
            },
            //上传文件删除回调
            onFileRemove(file, fileList) {
                this.formFile.uploadFileList = fileList
            },
            // 上传成功后的回调
            onUploadSuccess (response, file, fileList) {
                this.$message.success('文件上传完成')
                this.uploaded()
                this.formFile.loading = false
                this.formFile.visible = false
                console.log('上传文件', response)
            },
            // 上传错误的回调
            onUploadError (response, file, fileList) {
                this.$message.error(`上传失败，请重试！`)
                this.uploaded()
                this.formFile.loading = false
                this.formFile.visible = false
                console.log('上传失败')
            },
            //下载
            handleDownloadSubmit(row){
                this.$confirm('开始下载吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.$axios({
                        url: `user/file/${row.id}/seq/0/download`,
                        method: 'get',
                        //responseType: 'blob'
                    }).then((res) => {
                        if (!res) {
                            this.$message({
                                message: '下载失败',
                                type: 'error'
                            });
                            return
                        }
                        window.location.href = `${this.$upload}/user/file/download/${row.id}/${res.guid}`;
                        // const blob = new Blob([res])
                        // const filename = row.name + '.' + row.suffix
                        // if ('download' in document.createElement('a')) { // 非IE下载
                        //     const elink = document.createElement('a')
                        //     elink.download = filename
                        //     elink.style.display = 'none'
                        //     elink.href = URL.createObjectURL(blob)
                        //     document.body.appendChild(elink)
                        //     elink.click()
                        //     URL.revokeObjectURL(elink.href) // 释放URL 对象
                        //     document.body.removeChild(elink)
                        // } else { // IE10+下载
                        //     window.navigator.msSaveBlob(blob, filename)
                        // }
                    })
                })
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
            fileValidate() {
                if (this.formFile.uploadFileList.length ===0)
                {
                    this.$message.error("请选择上传文件")
                    return false
                }
                this.formFile.uploadFileList.forEach(c => {
                    if (!this.checkFileSize(c)) {
                        this.$message.error(`上传文件大小不能超过 ${this.upload.size}MB!`);
                        return false
                    }
                })
                return true
            },
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

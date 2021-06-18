<template>
    <el-container>
        <el-header height="79" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr">
                    <el-button-group>
                        <el-button size="small" icon="el-icon-arrow-left" @click="curFolderAction"></el-button>
                        <el-button size="small" @click="nextFolderAction"><i class="el-icon-arrow-right el-icon--right"></i></el-button>
                    </el-button-group>
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="pl10">
                        <el-breadcrumb-item>文档通知</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="ib">
                    <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt">
                        <el-button @click="search" slot="append" icon="el-icon-search"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button size="mini" icon="iconfont icon-shuaxin" @click="refresh"> 刷新</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon-trash" @click="handleMultiDeleteSubmit" plain>删除消息</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon-system-extension" @click="handleMultiReadSubmit" plain>全部已读</el-button>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <span style="font-weight: bold; letter-spacing: 3px; padding-left: 10px;">最新标签：</span>
                    <el-tag v-for="(project,id) in tagBar.body" closable type="warning" :key="project.id"
                            :disable-transitions="false" @click.native="tagSearch(project.name)" @close="handleClose(project)">
                        <a href=" javascript:void(0)" style="margin-left:10px;text-decoration-line:none;">{{project.name}}</a>
                    </el-tag>
                    <el-input :span="1" class="input-new-tag" v-if="tagBar.inputVisible" v-model="tagBar.inputValue"
                              ref="saveTagInput" size="small"
                              @keyup.enter.native="handleInputConfirm"
                              @blur="handleInputConfirm"></el-input>
                    <el-button :span="3" v-else class="button-new-tag" size="small" @click="showInput" >+ 点击添加新标签
                    </el-button>
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0">
            <el-scrollbar >
                <el-table ref="table" v-loading="messageTable.loading" :data="messageTable.body" @selection-change="handleSelectionChange" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="messageTable.height">
                    <el-table-column type="selection" width="50"></el-table-column>
                    <el-table-column prop="resourceName" label="文件名" width="500">
                        <template slot-scope="scope">
                            <div v-if="scope.row.kind === 'folder'">
                                <el-badge is-dot class="item"><app-svg iconClass="wenjianjia3"></app-svg></el-badge>
                                <a href="#" target="_self"  style="margin-left:10px;text-decoration-line:none;" >
                                    <b>{{scope.row.resourceName}}</b>
                                </a>
                            </div>
                            <div v-else>
                                <div v-if="scope.row.status === 1">
                                    <el-badge is-dot class="item">
                                        <app-svg :iconText="scope.row.resourceSuffix"></app-svg>
                                    </el-badge>
                                    <a href="#" @click="handlePreview(scope.$index, scope.row)" style="margin-left: 10px;text-decoration-line:none;">
                                        <b>{{scope.row.resourceName}}.{{scope.row.resourceSuffix}}</b>
                                    </a>
                                </div>
                                <div v-else>
                                    <app-svg :iconText="scope.row.resourceSuffix"></app-svg>
                                    <a href="#" @click="handlePreview(scope.$index, scope.row)" style="margin-left: 10px;text-decoration-line:none;">
                                        {{scope.row.resourceName}}.{{scope.row.resourceSuffix}}
                                    </a>
                                </div>
                            </div>
                        </template></el-table-column>
                    <el-table-column label="消息发送时间" align="center"><template slot-scope="scope">
                        <div v-if="scope.row.status === 1">
                            <el-tooltip class="item" effect="dark" placement="bottom">
                                <div slot="content">{{dateFormat(scope.row.fromDate)}}</div>
                                <p><b>{{dateShortFormat(scope.row.fromDate)}}</b></p>
                            </el-tooltip>
                        </div>
                        <div v-else>
                            <el-tooltip class="item" effect="dark" placement="bottom">
                                <div slot="content">{{dateFormat(scope.row.fromDate)}}</div>
                                <p>{{dateShortFormat(scope.row.fromDate)}}</p>
                            </el-tooltip>
                        </div>
                    </template></el-table-column>
                    <el-table-column label="修改者" align="center" ><template slot-scope="scope">
                        <div v-if="scope.row.status === 1">
                            <p><b>{{scope.row.fromUserName}}</b></p>
                        </div>
                        <div v-else>
                            <p>{{scope.row.fromUserName}}</p>
                        </div>
                    </template></el-table-column>
                    <el-table-column label="修改内容" align="center" ><template slot-scope="scope">
                        <div v-if="scope.row.status === 1">
                            <p><b>{{scope.row.info}}</b></p>
                        </div>
                        <div v-else>
                            <p>{{scope.row.info}}</p>
                        </div>
                    </template></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <div>
                                <el-tooltip class="item" effect="dark" content="文件下载" placement="bottom">
                                    <el-button class="btnAdd" type="text" size="mini" icon="iconfont icon-caozuo-shangchuan-download"
                                               @click="handleDownloadSubmit(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="删除消息" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini" icon="iconfont icon-trash"
                                               @click="handleDeleteSubmit(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <preview ref="preview"></preview>
    </el-container>
</template>

<script>
    import preview from '../doc/Preview'
    import {mapActions } from 'vuex'
    export default {
        name: "message",
        components:{ preview },
        data() {
            return {
                searchTxt: '',
                messageTable: {
                    loading: true,
                    body: [],
                    height:0,
                    pageSize: 1000,
                    total: 0,
                    curPage: 1,
                    multipleSelection: [],
                },
                tagBar:{
                    loading: true,
                    body: [],
                    inputVisible: false,
                    inputValue: '',
                },
            }
        },
        mounted() {
            this.getData()
            this.getTag()
            this.tableHeight()
        },
        methods: {
            ...mapActions({
                curFolderAction: 'folderHistory/curFolder',
                nextFolderAction: 'folderHistory/nextFolder',
            }),
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.messageTable.height = window.innerHeight - this.$refs.table.$el.offsetTop - 254;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.messageTable.height = window.innerHeight - self.$refs.table.$el.offsetTop - 254
                    }
                })
            },
            // 获取table数据
            getData() {
                this.messageTable.loading = true
                this.$axios({
                    url: `/user/messages/${this.messageTable.curPage}-${this.messageTable.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.messageTable.body = res.ctt.list
                    this.messageTable.total = res.ctt.total
                    this.messageTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //画面刷新
            refresh(){
                this.getData()
            },
            // 获取table数据
            getTag() {
                this.tagBar.loading = true
                this.$axios({
                    url: `/user/tags`,
                    method: 'get'
                }).then(res => {
                    this.tagBar.body = res.ctt
                    this.tagBar.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            sizeChange(num){
                this.messageTable.pageSize = num
                this.getData()
            },
            pageChange(num){
                this.messageTable.curPage = num
                this.getData()
            },
            //画面表格多选赋值操作
            handleSelectionChange(val) {
                this.messageTable.multipleSelection = val;
            },
            //日期格式化
            dateFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm");
            },
            //日期格式化
            dateShortFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("MM月DD日 HH:mm");
            },
            //下载
            handleDownloadSubmit:function(index, row){
                this.$axios({
                    url: `user/file/${row.resourceId}`,
                    method: 'get'
                }).then(res => {
                    if (!res) {
                        this.$message.error(`文件没有找到！`)
                        return
                    }
                    if(res.c === 6001){
                        this.$message.error(`文件已经被删除，不能下载！`)
                        return
                    }
                    if(res.ctt.permissionMap.visible === false || res.ctt.permissionMap.download === false){
                        this.$message.error(`您没有文件的下载权限！请联系文件夹管理员！`)
                        return
                    }
                    this.$confirm('开始下载最新版本的文件吗？', '提示', {
                        cancelButtonClass: 'btn-custom-cancel',
                        type: 'success'
                    }).then(() => {
                        this.$axios({
                            url: `user/message/${row.id}/seq/0/download`,
                            method: 'post',
                            responseType: 'blob'
                        }).then((res) => {
                            if (!res) {
                                this.$message({
                                    message: '下载失败',
                                    type: 'error'
                                });
                                this.getData();
                                return
                            }
                            //const content = res
                            const blob = new Blob([res])
                            const filename = row.resourceName + '.' + row.resourceSuffix
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
                        })
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //批量已读
            handleMultiReadSubmit:function(){
                this.$confirm('把全部消息设为已读?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.messageTable.loading = true;
                    this.$axios({
                        url: `user/messages/read`,
                        method: 'put',
                    }).then(() => {
                        this.$message({
                            message: '设置成功',
                            type: 'success'
                        });
                        this.getData();
                        this.messageTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //批量删除
            handleMultiDeleteSubmit:function(){
                if (this.messageTable.multipleSelection.length === 0){
                    this.$message.error("没有选择需要删除的消息，请重新选择！")
                    return
                }
                this.$confirm('确认删除这些外链吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.messageTable.loading = true;
                    let para = this.messageTable.multipleSelection;
                    this.$axios({
                        url: `user/messages`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                        this.messageTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //删除
            handleDeleteSubmit: function (index, row) {
                this.$confirm('确认删除该消息吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.messageTable.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `user/message`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                        this.messageTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            tagSearch(tag){
                this.messageTable.loading = true
                this.$axios({
                    url: `/user/messagesbysearchkey/${this.messageTable.curPage}-${this.messageTable.pageSize}?key=${tag}`,
                    method: 'get'
                }).then(res => {
                    this.messageTable.body = res.ctt.list
                    this.messageTable.total = res.ctt.total
                    this.messageTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //检索界面
            search(){
                if(!this.searchTxt || !this.searchTxt.trim()) {
                    this.$message.success("请输入搜索关键词")
                    return
                }
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.searchTxt.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                this.messageTable.loading = true
                this.$axios({
                    url: `/user/messagesbysearchkey/${this.messageTable.curPage}-${this.messageTable.pageSize}?key=${this.searchTxt}`,
                    method: 'get'
                }).then(res => {
                    this.messageTable.body = res.ctt.list
                    this.messageTable.total = res.ctt.total
                    this.messageTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            //添加新标签
            showInput() {
                this.tagBar.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs["saveTagInput"].$refs.input.focus();
                });
            },
            //提交新标签
            handleInputConfirm() {
                let newValue = {
                    id: 0,
                    name: this.tagBar.inputValue,
                    type:2
                } ;
                if (newValue.name) {
                    this.tagBar.loading = true
                    this.$axios({
                        url: `/user/tag`,
                        method: 'post',
                        data: newValue
                    }).then(res => {
                        if(res.c === 0){
                            this.$message.success("已添加新标签")
                            newValue.id = res.ctt.id;
                            this.tagBar.body.push(newValue);
                            this.tagBar.loading = false
                        }else {
                            this.$message.error(res.msg)
                        }
                    }).catch(err => {

                    })
                }
                this.tagBar.inputVisible = false;
                this.tagBar.inputValue = '';
            },
            //删除标签
            handleClose(tag) {
                this.$confirm('删除这个标签?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.tagBar.loading = true;
                    let para = tag;
                    this.$axios({
                        url: `user/tag`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.tagBar.body.splice(this.tagBar.body.indexOf(tag), 1);
                        this.tagBar.loading =false;
                    })
                }).catch(() => {

                });
            },
            handlePreview:function(index, row) {
                if (row.type === 3){
                    this.$message.warning('文件已被删除，不能查看！')
                    return
                }else{
                    this.$refs.preview.handlePreview(row);
                }
                row.status = 0;
                let para = Object.assign({}, row);

                this.$axios({
                    url: `user/message`,
                    method: 'put',
                    data: para
                }).then((res) => {
                    console.log(res)
                })
            },
        }
    }
</script>

<style lang="scss">
    .custom-table-cell {
        .el-button.is-circle{
            padding:0px;
            font-size:20px;
            margin-left:5px;
        }
        .btnAdd{
            color: #67c23a;
        }
        .btnEdit{
            color: #e6a23c;
        }
        .btnDel{
            color: #f56c6c;
        }
    }
</style>

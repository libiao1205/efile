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
                        <el-breadcrumb-item>我分享的文件</el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="ib">
                    <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "search" clearable>
                        <el-button @click="search" slot="append" icon="el-icon-search"></el-button>
                    </el-input>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <el-button size="mini" icon="iconfont icon-shuaxin" @click="refresh" > 刷新</el-button>
                    <el-button type="primary" size="mini" icon="iconfont icon--duankailianjie" @click="handleMultiDeleteSubmit" plain> 断开连接</el-button>
                </div>
                <div class="fxr pd5">
                    <el-checkbox v-model="onlyShowValid" @change="onlyValid">只表示有效外链</el-checkbox>
                </div>
            </div>
            <div class="fxb bb1" >
                <div class="fxr pd5">
                    <span style="font-weight: bold; letter-spacing: 3px; padding-left: 10px;">最新标签：</span>
                    <el-tag v-for="(project,id) in tagBar.body" closable type="warning" :key="project.id"
                            :disable-transitions="false" @click.native="tagSearch(project.name)"  @close="handleClose(project)">
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
                <el-table ref="table" v-loading="linkTable.loading" :data="linkTable.body" @selection-change="handleSelectionChange" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="linkTable.height">
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <template v-for="res in props.row.resources">
                                <el-row style="padding-left: 25px" >
                                    <el-col :span="1" align="right">
                                        <div v-if="res.kind === 'folder'">
                                            <app-svg iconClass="wenjianjia3"></app-svg>
                                        </div>
                                        <div v-else style="margin-top: 4px">
                                            <img v-if="!res.suffix" style="width: 18px;height: auto" :src="viewImageUrl" alt=""/>
                                            <app-svg v-if="res.suffix" :iconText="res.suffix"></app-svg>
                                        </div>
                                    </el-col>
                                    <el-col :span="7">
                                        <P style="line-height: 30px; font-size:15px;padding-left: 3px;">
                                            <b v-if="res.suffix">{{res.name}}.{{res.suffix}}</b>
                                            <b v-if="!res.suffix">{{res.name}}</b>
                                        </P>
                                    </el-col>
                                </el-row>
                            </template>
                        </template>
                    </el-table-column>
                    <el-table-column type="selection" min-width="10%"></el-table-column>
                    <el-table-column prop="name" label="外链">
                        <template slot-scope="props">
                            <app-svg iconClass="gongxiangwenjianjia"></app-svg>
                            <router-link :to="{ path: 'linkpreview', query: { link: props.row.randomFolderName }}" target="_blank"
                                         style="margin-left:10px;text-decoration-line:none;">{{props.row.name}}
                            </router-link>
                        </template>
                    </el-table-column>
                    <el-table-column label="有效期" align="center" min-width="10%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.days === 0"><p>无期限</p></div>
                            <div v-else><p>{{scope.row.days}}天</p></div>
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间"  align="center" min-width="20%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.days === 0"></div>
                            <div v-else><p>{{dateFormat(scope.row.endDate)}}截至</p></div>
                        </template>
                    </el-table-column>
                    <el-table-column label="外链状态"  align="center" min-width="15%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.status === 'EXPIRE'">
                                <p>已失效</p>
                            </div>
                            <div v-else-if="scope.row.status === 'DELETE'">
                                <p>已删除</p>
                            </div>
                            <div v-else>
                                <p>有效</p>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" min-width="15%">
                        <template slot-scope="scope">
                            <!--<el-tooltip class="item" effect="dark" content="编辑外链" placement="bottom">-->
                            <!--<el-button class="btnEdit" type="text" size="mini" icon="iconfont icon-edit-row"-->
                            <!--@click="handleCopyClipboard(scope.$index, scope.row)" circle></el-button>-->
                            <!--</el-tooltip>-->
                            <div v-if="scope.row.status === 'EXPIRE'">
                                <el-tooltip class="item" effect="dark" content="复制链接信息到剪贴板" placement="bottom">
                                    <el-button class="btnCopy" type="text" size="mini" icon="iconfont icon-wenjian"
                                               @click="handleCopyClipboard(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini" icon="iconfont icon-shanchu"
                                               @click="handleDeleteSubmit(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                            </div>
                            <div v-else>
                                <el-tooltip class="item" effect="dark" content="复制链接信息到剪贴板" placement="bottom">
                                    <el-button class="btnCopy" type="text" size="mini" icon="iconfont icon-wenjian"
                                               @click="handleCopyClipboard(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="断开外链" placement="bottom">
                                    <el-button class="btnDel" type="text" size="mini" icon="iconfont icon--duankailianjie"
                                               @click="handleDeleteSubmit(scope.$index, scope.row)" circle></el-button>
                                </el-tooltip>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-scrollbar>
        </el-main>
        <!--<link-file ref="linkFile" :linked="getData"></link-file>-->
    </el-container>
</template>

<script>
    // import linkFile from '../doc/LinkFile'
    import {mapActions } from 'vuex'
    export default {
        // components:{linkFile},
        name: "link",
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                copyBtn:null,
                onlyShowValid:true,
                searchTxt: '',
                linkTable: {
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
                    this.linkTable.height = window.innerHeight - this.$refs.table.$el.offsetTop - 254;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.linkTable.height = window.innerHeight - self.$refs.table.$el.offsetTop - 254
                    }
                })
            },
            // 获取table数据
            getData() {
                this.linkTable.loading = true
                this.$axios({
                    url: `/user/links/${this.linkTable.curPage}-${this.linkTable.pageSize}`,
                    method: 'get'
                }).then(res => {
                    if (this.onlyShowValid){
                        let valid = [];
                        if (res.ctt.list.length > 0) {
                            for( let i = 0; i < res.ctt.list.length; i++){
                                if (res.ctt.list[i].status === 'NORMAL'){
                                    valid.push(res.ctt.list[i])
                                }
                            }
                        }else {
                            valid = res.ctt.list;
                        }
                        this.linkTable.body = valid
                        this.linkTable.total = res.ctt.total
                        this.linkTable.loading = false
                    }else {
                        this.linkTable.body = res.ctt.list
                        this.linkTable.total = res.ctt.total
                        this.linkTable.loading = false
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                this.linkTable.loading = false
            },
            sizeChange(num){
                this.linkTable.pageSize = num
                this.getData()
            },
            pageChange(num){
                this.linkTable.curPage = num
                this.getData()
            },
            handleSelectionChange(val) {
                this.linkTable.multipleSelection = val;
            },
            onlyValid(val){
                this.onlyShowValid = val
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
            //日期格式化
            dateShortFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("MM月DD日 HH:mm");
            },
            //日期格式化
            dateFormat(data){
                if (data === undefined || data ===null) {
                    return "";
                }
                return this.$moment(data).format("YYYY年MM月DD日");
            },
            //画面刷新
            refresh(){
                this.getData()
            },
            //外链信息复制
            handleCopyClipboard(index, row) {
                let text = '外链地址:' + row.fullUrl + ' 密码:' + row.password
                this.copyBtn = new this.$clipboard(
                    '.btnCopy',{
                        text: function() {
                            return text;
                        }
                    }
                );
                let _this = this;
                let clipboard = _this.copyBtn;
                clipboard.on('success', function() {
                    _this.$message({
                        message: '已复制外链信息到剪贴板',
                        type: 'success'
                    });
                });
                clipboard.on('error', function() {
                    _this.$message({
                        message: '复制失败，请手动选择复制',
                        type: 'error'
                    });
                });
            },
            //外链批量删除
            handleMultiDeleteSubmit: function () {
                if (this.linkTable.multipleSelection.length === 0){
                    this.$message.error("没有选择需要断开的外链，请重新选择！")
                    return
                }
                this.$confirm('确认删除这些外链吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.linkTable.loading = true;
                    let para = this.linkTable.multipleSelection;
                    this.$axios({
                        url: `user/links`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                        this.linkTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            //删除
            handleDeleteSubmit: function (index, row) {
                this.$confirm('确认删除此外链吗?', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    this.linkTable.loading = true;
                    let para = Object.assign({}, row);
                    this.$axios({
                        url: `user/link`,
                        method: 'delete',
                        data: para
                    }).then((res) => {
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getData();
                        this.linkTable.loading = false;
                    })
                }).catch(() => {

                });
            },
            tagSearch(tag){
                this.linkTable.loading = true
                this.$axios({
                    url: `/user/linksbysearchkey/${this.linkTable.curPage}-${this.linkTable.pageSize}?key=${tag}`,
                    method: 'get'
                }).then(res => {
                    this.linkTable.body = res.ctt.list
                    this.linkTable.total = res.ctt.total
                    this.linkTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                this.linkTable.loading = false
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
                this.linkTable.loading = true
                this.$axios({
                    url: `/user/linksbysearchkey/${this.linkTable.curPage}-${this.linkTable.pageSize}?key=${this.searchTxt}`,
                    method: 'get'
                }).then(res => {
                    this.linkTable.body = res.ctt.list
                    this.linkTable.total = res.ctt.total
                    this.linkTable.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                this.linkTable.loading = false
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
                        this.tagBar.loading = false;
                    })
                }).catch(() => {

                });
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

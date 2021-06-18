<template>
    <el-container>
        <el-header height="106" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr  pd10" >
                    <div class="fnt-s20"><h1><app-svg iconClass="wenjianjia3"></app-svg>  <b>{{folder.name}}</b></h1></div>
                    <div v-if="folder.name !== ''" class="fxr pl10">
                        <el-tag>文件夹管理员：<b>{{folder.user}}</b></el-tag>
                        <el-tag>上传文件大小限制：<b>{{folder.size}}MB</b></el-tag>
<!--                        <el-tag>IP白名单：<b>{{folder.ip}}</b></el-tag>-->
                    </div>
                </div>
            </div>
        </el-header>
            <split-pane split="vertical" :className="splitClass" :defaultPercent=50  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
                <template slot="paneL">
                    <el-scrollbar>
                        <el-tree class="mr20" ref="folderTree" :data="folderTree.body" highlight-current v-loading="folderTree.loading" :props="folderTree.props"
                                 :default-expand-all="false" :expand-on-click-node="false" node-key='id' :default-expanded-keys="[1]"
                                 lazy :load="printFolderTree" @node-click="folderSelect" >
                                <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <div v-if="data.id === 1" >
<!--                                            <svg class="icon" aria-hidden="true">-->
<!--                                                <use xlink:href="#icon-zhuye11"></use>-->
<!--                                            </svg>-->
                                            <img style="width: 20px;height: auto" :src="viewImageUrl" alt=""/>
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
                                            <el-tooltip class="item" effect="dark" content="设置上传文件大小" placement="bottom">
                                                <el-button class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-bingzhuangtu"
                                                           @click.native="$refs.editFolder.handleFolderSize(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="设置允许访问IP" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-_IPbaimingdan"
                                                           @click.native="$refs.folderWhite.handleWhiteList(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="设置文件夹管理员" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-guanli_danse"
                                                           @click.native="$refs.folderAdmin.handleFolderAdmin(node)"
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
                                            <el-tooltip class="item" effect="dark" content="设置上传文件大小" placement="bottom">
                                                <el-button class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-bingzhuangtu"
                                                           @click.native="$refs.editFolder.handleFolderSize(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="设置允许访问IP" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-_IPbaimingdan"
                                                           @click.native="$refs.folderWhite.handleWhiteList(node)"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="设置文件夹管理员" placement="bottom">
                                                <el-button v-if="!data.isRoot"
                                                           class="btnEdit" type="text" size="mini"
                                                           icon="iconfont icon-guanli_danse"
                                                           @click.native="$refs.folderAdmin.handleFolderAdmin(node)"
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
                </template>
                <template slot="paneR">
                    <el-scrollbar >
                        <el-table ref="table" v-loading="fileListData.loading" :data="fileListData.body" highlight-current-row
                                  :cell-style="{padding:'5px'}" class="custom-table-cell"
                                  :header-cell-style="{padding: '2px'}" :height="fileListData.height">
                            <el-table-column prop="name" label="文件名" :show-overflow-tooltip="true" >
                                <template slot-scope="scope">
                                    <div>
                                        <app-svg :iconText="scope.row.suffix"></app-svg>
                                        <a href="#" @click="handlePreview(scope.$index, scope.row)" style="margin-left: 10px;text-decoration-line:none;">
                                            <b v-if="scope.row.suffix">{{scope.row.name}}.{{scope.row.suffix}}</b>
                                            <b v-if="!scope.row.suffix">{{scope.row.name}}</b>
                                        </a>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="最后修改者" align="center" width="120px">
                                <template slot-scope="scope">
                                    <span>{{scope.row.lastUpdateUserName}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="最后修改时间" align="center" width="200px">
                                <template slot-scope="scope"  >
                                    <el-tooltip class="item" effect="dark" placement="bottom">
                                        <div slot="content">{{dateFormat(scope.row.lastUpdateDate)}}</div>
                                        <span >{{dateShortFormat(scope.row.lastUpdateDate)}}</span>
                                    </el-tooltip>
                                </template>
                            </el-table-column>
                            <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                                <span slot="no-more"></span>
                            </infinite-loading>
                        </el-table>
                    </el-scrollbar>
                </template>
            </split-pane>
        <folder-admin ref="folderAdmin"></folder-admin>
        <!--<edit-permission ref="editPermission"></edit-permission>-->
        <edit-folder ref="editFolder"  :add-folder="addFolder" :edit-folder="editFolder" :delete-folder="deleteFolder" ></edit-folder>
        <folder-white ref="folderWhite"></folder-white>
    </el-container>
</template>

<script>
    import splitPane from 'vue-splitpane'
    // import editPermission from '../roledept/EditPermission'
    import editFolder from './EditFolder'
    import folderAdmin from '../admininfo/FolderAdmin'
    import folderWhite from '../folderip/EditFolderWhite'
    export default {
        components: {splitPane, editFolder, folderAdmin, folderWhite},
        name: 'doc',
        data() {
            return {
                /*文件夹信息 Start*/
                folder:{
                    name:'',
                    size:'',
                    user:'',
                    ip:'',
                },
                /*文件夹信息 End*/
                /*文件夹树数据 Start*/
                folderTree:{
                    body: [],
                    props: {
                        label: 'name',
                        children: 'children'
                    },
                    loading: false,
                    selected:null,
                },
                /*文件夹树数据 End*/
                /*文件列表数据 Start*/
                fileListData: {
                    height: 0,
                    loading: false,
                    body: [],
                    pageSize: 20,
                    total: 0,
                    curPage:0
                },
                /*文件列表数据 End*/
                splitClass:'',
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
            }
        },
        mounted() {
            //this.getData()
            this.tableHeight()
        },
        methods: {
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.fileListData.height = window.innerHeight - this.$refs.split.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.fileListData.height = window.innerHeight - self.$refs.split.$el.offsetTop
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
                this.$axios({
                    url: `/admin/folder/${data.id}`,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        this.folder.name = res.ctt.name
                        if (!res.ctt.fileSize || res.ctt.fileSize === 0){
                            this.folder.size = this.$maxFileSize
                        }else{
                            this.folder.size = res.ctt.fileSize
                        }
                        if (!res.ctt.folderIpDto){
                            this.folder.ip = '无限制'
                        }else{
                            this.folder.ip = res.ctt.folderIpDto.ipInfo
                        }
                        if (res.ctt.admins && res.ctt.admins.length > 0){
                            let users = '';
                            for (let i = 0; i < res.ctt.admins.length; i++) {
                                users += res.ctt.admins[i].name + '、'
                            }
                            if (users.length > 0){
                                this.folder.user = users.substring(0,users.length - 1)
                            }else{
                                this.folder.user = '未设置专属文件夹管理员'
                            }
                        }else{
                            this.folder.user = '未设置专属文件夹管理员'
                        }
                        this.selectFolder = data
                        this.refresh()
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getFileData($state) {
                if (!this.selectFolder){
                    return $state.complete()
                }

                this.fileListData.loading = this.$refs.infinite.isFirstLoad
                this.$axios({
                    url: `/admin/folder/${this.selectFolder.id}/files/${this.fileListData.curPage}-${this.fileListData.pageSize}`,
                    method: 'get'
                }).then(res => {
                    this.fileListData.body = this.fileListData.body.concat(res.ctt.list)
                    this.fileListData.total = res.ctt.total
                    this.fileListData.loading = false

                    if (res.ctt.list.length === 0){
                        if($state) $state.complete()
                    }
                    else{
                        if($state) $state.loaded()
                    }
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
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm");
            },

            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
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
            nextPage($state){
                this.fileListData.curPage += 1
                this.getFileData($state)
            },
            //画面刷新
            refresh(){
                this.fileListData.total = 0
                this.fileListData.body = []
                this.fileListData.curPage = 0
                this.$refs.infinite.stateChanger.reset()
            },
        }
    }
</script>

<style scoped>

</style>

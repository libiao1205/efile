<template>
    <el-container>
        <el-header height="106" class="pd0">
            <div class="app-title fxb bb1 pd10">
                <div class="fxr fnt-s20 pd10" >
                    <h1><app-svg iconClass="drxx95"></app-svg>  组织架构权限管理</h1>
                </div>
                <div class="ib pd10">
                    <el-input placeholder="请输入用户姓名" class="input-with-select" v-model="searchData.text" @keyup.enter.native = "search" clearable @clear="clearSearchTxt">
                        <el-button @click="search" slot="append" icon="el-icon-search" ></el-button>
                    </el-input>
                </div>
            </div>
        </el-header>
        <split-pane split="vertical" :className="splitClass" :defaultPercent=20  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
            <template slot="paneL">
                <el-scrollbar v-show="!searchData.isSearch">
                    <el-tree  class="mr20" ref="memberTree" :data="memberTree.data" highlight-current v-loading="memberTree.loading" :props="memberTree.props"
                              :expand-on-click-node="false" lazy :load="printMemberTree" @node-click="memberSelect" :default-expanded-keys="['dept-1']" node-key = 'key'>
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                            <div v-if="data.id === 1">
                                <div v-if="data.kind === 'dept'">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-zhuye11"></use>
                                    </svg>
                                    <span><b>{{node.label}}</b></span>
                                </div>
                                <div v-else>
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-user"></use>
                                    </svg>
                                    <span>{{node.label}}</span>
                                </div>
                            </div>
                            <div v-else>
                                <div v-if="data.type === 1">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-zuzhiguanli-"></use>
                                    </svg>
                                    <span>{{node.label}}</span>
                                </div>
                                <div v-else-if="data.type === 2">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-danweizuzhi"></use>
                                    </svg>
                                    <span>{{node.label}}</span>
                                </div>
                                <div v-else-if="data.type === 0">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-user"></use>
                                    </svg>
                                    <span>{{node.label}}</span>
                                </div>
                                <div v-else>
                                    <span>{{node.label}}</span>
                                </div>
                            </div>
                        </span>
                    </el-tree>
                </el-scrollbar>
                <el-table v-show="searchData.isSearch" ref="table" v-loading="tableData.loading" :data="tableData.body" highlight-current-row
                          :cell-style="{padding:'5px'}" class="custom-table-cell"
                          :header-cell-style="{padding: '2px'}" :height="tableData.height" @current-change="currentChange">
                    <el-table-column prop="name" label="用户姓名" align="left" width="180" >
                        <template slot-scope="scope">
                            <div>
                                <i class="iconfont icon-geren"></i>
                                <span style="padding-left: 5px;">{{scope.row.name}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="departments" label="用户组织" align="center">
                        <template slot-scope="scope">
                            <div v-for = "item in scope.row.departments">
                                <div v-if="item.id === 1">
                                    <svg class="icon" aria-hidden="true">
                                        <use xlink:href="#icon-zhuye11"></use>
                                    </svg>
                                    {{item.name}}
                                </div>
                                <div v-else>
                                    <div v-if="item.type === 1">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-zuzhiguanli-"></use>
                                        </svg>
                                        {{ item.name }}
                                    </div>
                                    <div v-else>
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-danweizuzhi"></use>
                                        </svg>
                                        {{ item.name }}
                                    </div>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="status" label="状态" min-width="20%">
                        <template slot-scope="scope">
                            <div v-if="scope.row.status === 8"><span>离职</span></div>
                            <div v-else-if="scope.row.status === 4"><span>账户封禁</span></div>
                            <div v-else><span>正常</span></div>
                        </template>
                    </el-table-column>
                    <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                        <span slot="no-more"></span>
                    </infinite-loading>
                </el-table>
            </template>
            <template slot="paneR">
                <split-pane split="vertical" :className="splitClass" :defaultPercent=40  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
                    <template slot="paneR">
                    <el-scrollbar >
                        <el-tree ref="documentTree" :data="documentTree.data" highlight-current
                                 v-loading="documentTree.loading" :props="documentTree.props"
                                 :default-expand-all="false" node-key = 'key' :expand-on-click-node="false" lazy
                                 :load="printDocumentTree" :default-expanded-keys="documentTree.defaultNodes">
                            <span class="custom-tree-node" slot-scope="{ node, data }">
                                <span>
                                    <div v-if="data.id === 1">
                                        <div v-if="data.kind === 'folder'">
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-zhuye11"></use>
                                            </svg>
                                            <span><b>{{node.label}}</b></span>
                                        </div>
                                        <div v-else>
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-document"></use>
                                            </svg>
                                            <span>{{node.label}}</span>
                                        </div>
                                    </div>
                                    <div v-else>
                                        <div v-if="data.kind === 'folder'">
                                            <svg class="icon" aria-hidden="true">
                                                <use xlink:href="#icon-wenjianjia3"></use>
                                            </svg>
                                            <span><b>{{node.label}}</b></span>
                                        </div>
                                        <div v-else>
                                            <app-svg :iconText="data.suffix"></app-svg>
                                            <el-tooltip class="item" effect="dark" :content="node.label" placement="bottom">
                                                <span class="ib" style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;max-width: 150px;" >
                                                    {{node.label}}
                                                </span>
                                            </el-tooltip>
                                        </div>
                                    </div>
                                </span>
                                <span class="mr20" >
                                    <span class="mr20 ib" style="width: 120px;">
                                        <span v-if="data.roles.visible">
                                            <el-tooltip class="item" effect="dark" content="可见" placement="bottom">
                                                <i  class="iconfont icon-liulan"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.read">
                                            <el-tooltip class="item" effect="dark" content="预览" placement="bottom">
                                                <i class="iconfont icon-wenjian"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.update">
                                            <el-tooltip class="item" effect="dark" content="更新文件" placement="bottom">
                                                <i v-if="data.roles.visible" class="iconfont icon-caozuo-shangchuan-upload"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.new">
                                            <el-tooltip class="item" effect="dark" content="新增文件" placement="bottom">
                                                <i class="iconfont icon-add" ></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.modify">
                                            <el-tooltip class="item" effect="dark" content="重命名" placement="bottom">
                                                <i class="iconfont icon-edit-file"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.download">
                                            <el-tooltip class="item" effect="dark" content="下载" placement="bottom">
                                                <i class="iconfont icon-caozuo-shangchuan-download"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.link">
                                            <el-tooltip class="item" effect="dark" content="外链" placement="bottom">
                                                <i class="iconfont icon-wailian"></i>
                                            </el-tooltip>
                                        </span>

                                        <span v-if="data.roles.delete">
                                            <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                                                <i class="iconfont icon-trash"></i>
                                            </el-tooltip>
                                        </span>
                                    </span>
                                    <span style="width: 75px" class="ib">
                                        <el-tooltip v-if="memberTree.selected" class="item" effect="dark" content="设定权限" placement="right">
                                            <el-button type="text" size="mini"
                                                       icon="iconfont icon-drxx74"
                                                       @click.native="$refs.editPermission.handlePermissionEdit(data, memberTree.selected, data.roles)"
                                                       circle></el-button>
                                        </el-tooltip>
                                        <el-dropdown v-if="data.kind === 'folder'" trigger="click" @command="handleCommand">
                                            <el-tooltip class="item" effect="dark" content="文件夹操作" placement="right">
                                                <el-button v-if="data.kind === 'folder'" class="btnEdit" type="text"
                                                           size="mini"
                                                           icon="iconfont icon-guanli_danse"
                                                           circle></el-button>
                                            </el-tooltip>
                                            <el-dropdown-menu  slot="dropdown">
                                                <el-dropdown-item :command="{name: 'FolderAdd', node: node}">新增下级菜单</el-dropdown-item>
                                                <el-dropdown-item v-if="data.key !== 'folder-1'" :command="{name: 'FolderEdit', node: node}">文件夹编辑</el-dropdown-item>
<!--                                                <el-dropdown-item v-if="data.key !== 'folder-1'" :command="{name: 'WhiteList', node: node}">设置允许访问IP</el-dropdown-item>-->
                                                <el-dropdown-item v-if="data.key !== 'folder-1'" :command="{name: 'FolderAdmin', node: node}">设置文件夹管理员</el-dropdown-item>
                                                <el-dropdown-item v-if="data.key !== 'folder-1'" :command="{name: 'FolderDelete', node: node}">删除文件夹</el-dropdown-item>
                                            </el-dropdown-menu>
                                        </el-dropdown>
                                    </span>
                                </span>
                            </span>
                        </el-tree>
                    </el-scrollbar>
                    </template>
                    <template slot="paneL">
                <el-scrollbar >
                    <el-table ref="table2" v-loading="permissionTable.data.loading" :data="permissionTable.data.body" highlight-current-row
                              :cell-style="{padding:'5px'}" class="custom-table-cell" width="500px"
                              :header-cell-style="{padding: '2px'}" :height="tableData.height" @current-change="permissionChange">
                        <el-table-column prop="inherit" label="类型" :show-overflow-tooltip="true" width="65px">
                            <template slot-scope="scope">
                                <div v-if="scope.row.inherit === 1">
                                    <el-tag size="mini" type="warning" disable-transitions>自身</el-tag>
                                </div>
                                <div v-else-if="scope.row.inherit === 2">
                                    <el-tag size="mini" type="success" disable-transitions>继承</el-tag>
                                </div>
                                <div v-else>
                                </div>
                            </template>

                        </el-table-column>
                        <el-table-column prop="resourceName" label="文件/文件夹" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                <div v-if="scope.row.code === 'folder'">
                                    <app-svg iconClass="wenjianjia3"></app-svg>
                                    <b>{{scope.row.resourceName}}</b>
                                </div>
                                <div v-else>
                                    <app-svg :iconText="scope.row.resourceSuffix"></app-svg>
                                    <b>{{scope.row.resourceName}}</b>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="权限" width="170px">
                            <template slot-scope="scope">

                                <el-tooltip class="item" effect="dark" content="可见" placement="bottom">
                                    <i v-if="scope.row.explication.visible" class="iconfont icon-liulan"></i>
                                </el-tooltip>

                                <el-tooltip class="item" effect="dark" content="预览" placement="bottom">
                                    <i v-if="scope.row.explication.read" class="iconfont icon-wenjian"></i>
                                </el-tooltip>

                                <el-tooltip class="item" effect="dark" content="更新文件" placement="bottom">
                                    <i v-if="scope.row.explication.update" class="iconfont icon-caozuo-shangchuan-upload"></i>
                                </el-tooltip>

                                <el-tooltip class="item" effect="dark" content="新增文件" placement="bottom">
                                    <i v-if="scope.row.explication.new" class="iconfont icon-add" ></i>
                                </el-tooltip>

                                <el-tooltip class="item" effect="dark" content="重命名" placement="bottom">
                                    <i v-if="scope.row.explication.modify" class="iconfont icon-edit-file"></i>
                                </el-tooltip>


                                <el-tooltip class="item" effect="dark" content="下载" placement="bottom">
                                    <i v-if="scope.row.explication.download" class="iconfont icon-caozuo-shangchuan-download"></i>
                                </el-tooltip>

                                <el-tooltip class="item" effect="dark" content="外链" placement="bottom">
                                    <i v-if="scope.row.explication.link" class="iconfont icon-wailian"></i>
                                </el-tooltip>


                                <el-tooltip class="item" effect="dark" content="删除" placement="bottom">
                                    <i v-if="scope.row.explication.delete" class="iconfont icon-trash"></i>
                                </el-tooltip>

                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="60px">
                            <template slot-scope="scope">
                                <div v-if="scope.row.inherit === 1">
                                    <el-tooltip class="item" effect="dark" content="删除权限" placement="bottom">
                                        <el-button class="btnDel" type="text" size="mini"
                                                   icon="iconfont icon-trash"
                                                   @click="handlePermissionDeleteSubmit(scope.$index, scope.row)"
                                                   circle></el-button>
                                    </el-tooltip>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-scrollbar>
                    </template>
                </split-pane>
                </template>
        </split-pane>

        <edit-permission ref="editPermission" :edit-permission="refreshPermissionTable"></edit-permission>
        <edit-folder ref="editFolder"  :add-folder="addFolder" :edit-folder="editFolder" :delete-folder="deleteFolder"></edit-folder>
        <folder-admin ref="folderAdmin"></folder-admin>
        <folder-white ref="folderWhite"></folder-white>
    </el-container>
</template>

<script>
    import editPermission from './EditPermission'
    import editFolder from '../doc/EditFolder'
    import folderAdmin from '../admininfo/FolderAdmin'
    import folderWhite from '../folderip/EditFolderWhite'
    import splitPane from 'vue-splitpane'
    const RoleOptions = {
        visible: false,
        new: false,
        modify: false,
        read: false,
        update: false,
        link: false,
        download: false,
        delete: false,
    };
    export default {
        name: "roledept",
        components: {editPermission, editFolder, folderAdmin, folderWhite, splitPane},
        data(){
            return {
                memberTree:{
                    data: [],
                    loading: false,
                    props: {
                        children: 'children',
                        label: 'name'
                    },
                    selected:null,
                },

                documentTree:{
                    data: [],
                    loading: false,
                    props: {
                        children: 'children',
                        label: 'name',
                    },
                    selected:null,
                    defaultNodes: ['folder-1'],
                    currentKey : ''
                },
                splitClass:'',
                searchData: {
                    isSearch:false,
                    text:'',
                    data:'',
                },
                tableData: {
                    height: 0,
                    loading: true,
                    body: [],
                    pageSize: 20,
                    total: 0,
                    curPage:0
                },
                permissionTable:{
                    height: 0,
                    data: {
                        loading: false,
                        body: []
                    },
                    page: {
                        pageSize: 1000,
                        total: 0,
                        curPage:1
                    },
                },
            }
        },
        methods:{
            tableHeight(){
                this.$nextTick(function () {
                    this.tableData.height = window.innerHeight - this.$refs.split.$el.offsetTop;
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.tableData.height = window.innerHeight - self.$refs.split.$el.offsetTop
                    }
                })
            },
            printMemberTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    if (node.data.kind === 'dept'){
                        id = node.data.id
                    } else {
                        resolve([])
                        return
                    }
                }
                this.memberTree.loading = true
                this.$axios({
                    url: `/admin/dept/${id}/members`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.memberTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            memberSelect(data) {
                this.memberTree.selected = data
                this.updatePermission()
                if(!data) return
                if (data.kind === 'dept') {
                    this.getDeptPermission(data)
                } else {
                    this.getUserPermission(data)
                }
            },
            printDocumentTree(node, resolve){
                let id;
                if (node.level === 0) {
                    id = 0
                } else {
                    if (node.data.kind === 'folder'){
                        id = node.data.id
                    } else {
                        resolve([])
                        return
                    }
                }
                this.documentTree.loading = true
                this.$axios({
                    url: `/admin/folder/${id}/docs`,
                    method: 'get'
                }).then(res => {
                    let isSetCurKey = false
                    res.ctt.forEach(c => {
                        c.roles = RoleOptions
                        if(c.key === this.documentTree.currentKey) isSetCurKey = true
                    });
                    resolve(res.ctt)
                    this.updatePermission()
                    this.$nextTick(() => {
                        this.tableHeight()
                        if (isSetCurKey){
                            this.$refs.documentTree.setCurrentKey(this.documentTree.currentKey)
                            this.documentTree.currentKey = ''
                        }
                    })
                    this.documentTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            updatePermission(data){
                let nodes
                if (!this.memberTree.selected){
                    nodes = this.getAllTreeNodes(this.$refs.documentTree.root)
                    nodes.forEach(function(c){
                        c.data.roles = RoleOptions
                    });
                    return
                }

                let url;
                if (this.memberTree.selected.kind === 'dept') {
                    url = `/admin/dept/${this.memberTree.selected.id}/docs/rps`
                } else {
                    url = `/admin/user/${this.memberTree.selected.id}/docs/rps`
                }

                if (!data){
                    nodes = this.getAllTreeNodes(this.$refs.documentTree.root)
                }
                else {
                    nodes = [data]
                }

                if(nodes.length === 0) return
                let keys = []
                nodes.forEach(c => {
                    keys.push(c.data.key)
                })
                this.$axios({
                    url: url,
                    method: 'put',
                    data: keys
                }).then(res => {
                    let that = this
                    Object.keys(res.ctt).forEach(c => {
                        that.$refs.documentTree.getNode(c).data.roles = res.ctt[c]
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getAllTreeNodes(node){
                let nodes = []
                let that = this
                if(node.data.id) nodes.push(node)
                node.childNodes.forEach(c => {
                    nodes = nodes.concat(that.getAllTreeNodes(c))
                })
                return nodes
            },
            addFolder(res) {
                res.ctt.roles = RoleOptions
                this.$refs.documentTree.append(res.ctt, 'folder-'+ res.ctt.parentId)
                this.$nextTick(() => {
                    this.updatePermission(this.$refs.documentTree.getNode(res.ctt.key))
                });
            },
            editFolder(para) {
                para.key = 'folder-' + para.id
                let node = this.$refs.documentTree.getNode(para)
                this.$refs.documentTree.remove(node)
                node.roles = RoleOptions
                this.$refs.documentTree.append(node.data, 'folder-'+ para.parentId)
                this.$nextTick(() => {
                    this.updatePermission(this.$refs.documentTree.getNode(node.key))
                });
                node.data.name = para.name;
            },
            deleteFolder(para) {
                this.$refs.documentTree.remove(para)
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            search(){
                if(!this.searchData.text || !this.searchData.text.trim()) {
                    this.$message.success("请输入搜索关键词")
                    return
                }
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.searchData.text.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                this.searchData.data = this.searchData.text
                this.searchData.isSearch = true
                this.refresh()
            },
            getData($state) {
                if(!this.searchData.data){
                    $state.complete()
                    return
                }
                this.tableData.loading = this.$refs.infinite.isFirstLoad
                this.$axios({
                    url: `/admin/search/users/${this.tableData.curPage}-${this.tableData.pageSize}?name=${this.searchData.data}&islove=true`,
                    method: 'get'
                }).then(res => {
                    let data = []
                    data.push(res.ctt.list)
                    if (data[0].length > 0 && data[0][0].id === 1){
                        data[0][0].isRoot = true
                    }
                    this.tableData.body = this.tableData.body.concat(res.ctt.list)
                    this.tableData.total = res.ctt.total
                    this.tableData.loading = false

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
            refresh(){
                this.tableData.total = 0
                this.tableData.body = []
                this.tableData.curPage = 0
                this.memberSelect(null)
                if(this.$refs.infinite) this.$refs.infinite.stateChanger.reset()
            },
            clearSearchTxt(){
                this.searchData.isSearch = false
                this.searchData.data = ''
                this.$nextTick(() => {
                    this.memberSelect(this.$refs.memberTree.getCurrentNode())
                })
                this.refresh()
            },
            nextPage($state){
                this.tableData.curPage += 1
                this.getData($state)
            },
            currentChange(row){
                this.memberSelect(row)
            },
            handlePermissionDeleteSubmit: function (index, row) {
                this.$confirm('确认删除选中权限吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'warning'
                }).then(() => {
                    let fromPermissionData
                    fromPermissionData = {
                        rolePermissions:[{
                            id:row.id,
                        }]
                    }
                    let pram = Object.assign({}, fromPermissionData)
                    this.$axios({
                        url: '/admin/role',
                        method: 'delete',
                        data: pram
                    }).then((res) => {
                        if(res.c === 0){
                            this.$message.success("权限删除成功")
                            //this.fromPermissionData.visible.modify = false
                            //this.handleResetPermission()
                            this.memberSelect(this.$refs.memberTree.getCurrentNode())
                        }else {
                            this.$message.error(res.msg)
                        }
                    })
                })
            },
            getUserPermission(data){
                this.permissionTable.data.loading = true
                this.$axios({
                    url: `/admin/user/${data.id}/rps`,
                    method: 'get'
                }).then(res => {
                    this.permissionTable.data.body = res.ctt
                    this.permissionTable.page.total = res.ctt.total
                    this.permissionTable.data.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            getDeptPermission(data){
                this.permissionTable.data.loading = true
                this.$axios({
                    url: `/admin/dept/${data.id}/rps`,
                    method: 'get'
                }).then(res => {
                    this.permissionTable.data.body = res.ctt
                    this.permissionTable.page.total = res.ctt.total
                    this.permissionTable.data.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            permissionChange(row) {
                if(!row) return
                for(var i=0;i<this.$refs.documentTree.store._getAllNodes().length;i++){
                    this.$refs.documentTree.store._getAllNodes()[i].expanded=false
                }
                let idList = row.fullPath.split('/')
                let nodes = []
                idList.forEach((c, i) => {
                    if(c === '') return
                    if (i === idList.length - 1) return
                    nodes.push('folder-' + c)
                })
                this.$nextTick(() => {
                    let currentKey;
                    if (row.code === 'file'){
                        currentKey = 'file-' + row.resourceId
                    } else {
                        currentKey = 'folder-' + row.resourceId
                    }
                    if (this.$refs.documentTree.getNode(currentKey)){
                        this.$refs.documentTree.setCurrentKey(currentKey)
                    } else {
                        this.documentTree.currentKey = currentKey
                    }
                })
                this.documentTree.defaultNodes = nodes
            },
            refreshPermissionTable(){
                if (this.memberTree.selected.kind === 'dept') {
                    this.getDeptPermission(this.memberTree.selected)
                } else {
                    this.getUserPermission(this.memberTree.selected)
                }
            },
            handleCommand(command){
                switch (command.name) {
                    case 'PermissionEdit':
                        this.$refs.editPermission.handlePermissionEdit(command.node.data, this.memberTree.selected, command.node.data.roles)
                        break
                    case 'FolderAdd':
                        this.$refs.editFolder.handleFolderAdd(command.node)
                        break
                    case 'FolderEdit':
                        this.$refs.editFolder.handleFolderEdit(command.node)
                        break
                    case 'WhiteList':
                        this.$refs.folderWhite.handleWhiteList(command.node)
                        break
                    case 'FolderAdmin':
                        this.$refs.folderAdmin.handleFolderAdmin(command.node)
                        break
                    case 'FolderDelete':
                        this.$refs.editFolder.handleFolderDelete(command.node)
                        break
                }
            }
        }
    }
</script>

<style scoped>

</style>

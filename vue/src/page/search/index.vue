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
                        <el-breadcrumb-item>关键词 <span style="color: red;font-weight: bold;">" {{searchKey}} "</span> 的相关搜索结果  </el-breadcrumb-item>
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
                    <span style="font-weight: bold; letter-spacing: 3px; padding-left: 10px;">最新标签：</span>
                    <el-tag v-for="(project, id) in tagBar.body" closable type="warning" :key="project.id"
                            :disable-transitions="false" @click.native="tagSearch(project.name)" @close="handleClose(project)">
                        <a href=" javascript:void(0)" :key="id" style="margin-left:10px;text-decoration-line:none;">{{project.name}}</a>
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
        <el-container style="height: calc(100% - 105px);">
            <el-main class="sys-content pd0" style="width: calc(100% - 300px);float: left">
                <div>
                    <!-- <div v-for="it in searchTable.body" class="custom-search-table-cell">
                        <div v-if="it.kind === 'folder'">
                            <div v-if="it.permissionMap.read">
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <a href=" javascript:void(0) " style="margin-left:5px;text-decoration-line:none;"><b>{{it.name}}</b></a>
                            </div>
                            <div v-else>
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <span style="margin-left:5px;">{{it.name}}</span>
                            </div>
                            <div>
                                <p v-html='it.highLight'></p>
                            </div>
                        </div>
                        <div v-else>
                            <div v-if="it.permissionMap.read">
                                <app-svg :iconText="it.suffix"></app-svg>
                                <a href=" javascript:void(0) " @click="$refs.preview.handlePreview(it)"
                                   style="margin-left:5px;text-decoration-line:none;font-size:16px;" >
                                    <b>{{it.name}}.{{it.suffix}}</b>
                                </a>
                                <span style="padding-left: 50px; font-size: 12px;color: #969896">{{dateShortFormat(it.lastUpdateDate)}}</span>
                                <span style="padding-left: 20px; font-size: 12px;color: #969896">{{it.size}}</span>
                                <span style="padding-left: 20px; font-size: 12px;color: #969896">{{it.lastUpdateUserName}}</span>
                                <el-tooltip class="item" effect="dark" content="文件删除" placement="bottom">
                                    <el-button v-if="it.permissionMap.delete"
                                               class="btnDel"  type="text" size="mini" style="float:right; margin-right: 25px;"
                                               icon="iconfont icon-trash"
                                               @click="$refs.editFolder.handleDeleteSubmit(it)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="文件版本履历" placement="bottom">
                                    <el-button v-if="it.permissionMap.read"
                                               class="btnEdit"  type="text" size="mini" style="float:right; margin-right: 5px;"
                                               icon="iconfont icon-wenzhangpailie-"
                                               @click="$refs.preview.handleHistory(it)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="重命名" placement="bottom">
                                    <el-button v-if="it.permissionMap.modify"
                                               class="btnEdit"  type="text" size="mini" style="float:right; margin-right: 5px;"
                                               icon="iconfont icon-edit-row"
                                               @click="$refs.editFolder.handleReName(it)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="文件更新" placement="bottom">
                                    <el-button v-if="it.permissionMap.update"
                                               class="btnEdit" type="text" size="mini" style="float:right; margin-right: 5px;"
                                               icon="iconfont icon-caozuo-shangchuan-upload"
                                               @click="$refs.uploadFile.handleEditFile(it)"
                                               circle></el-button>
                                </el-tooltip>
                                <el-tooltip class="item" effect="dark" content="文件下载" placement="bottom">
                                    <el-button v-if="it.permissionMap.download"
                                                class="btnAdd" type="text" size="mini" style="float:right; margin-right: 5px;"
                                                icon="iconfont icon-caozuo-shangchuan-download"
                                                @click="$refs.uploadFile.handleDownloadSubmit(it)"
                                               circle></el-button>
                                </el-tooltip>
                            </div>
                            <div v-else>
                                <app-svg :iconText="it.suffix"></app-svg>
                                <span style="margin-left:-5px;">
                                    {{it.name}}.{{it.suffix}}
                                </span>
                                <span style="padding-left: 50px; font-size: 12px;color: #969896">{{dateShortFormat(it.lastUpdateDate)}}</span>
                                <span style="padding-left: 20px; font-size: 12px;color: #969896">{{it.size}}</span>
                                <span style="padding-left: 20px; font-size: 12px;color: #969896">{{it.lastUpdateUserName}}</span>
                            </div>
                            <div style="padding: 5px">
                                <p v-html='it.highLight'></p>
                            </div>
                            <div style="padding: 2px">
                                <p style="font-size: 12px;color: #969896" v-html='it.parentPathName'></p>
                            </div>
                        </div>

                    </div> -->
                    <div class="custom-search-table-cell  doc-item-file-container" v-for="(item, index) in searchTable.body" :key="index">
                        <el-collapse ref="collapsible" v-model="activeCollapse">
                            <div v-if="item.kind === 'folder'">
                                <div v-if="item.permissionMap.read">
                                    <app-svg iconClass="wenjianjia3"></app-svg>
                                    <a href="javascript:void(0)" style="margin-left:5px;text-decoration-line:none;"><b>{{item.name}}</b></a>
                                </div>
                                <div v-else>
                                    <app-svg iconClass="wenjianjia3"></app-svg>
                                    <span style="margin-left:5px;">{{item.name}}</span>
                                </div>
                                <div>
                                    <p v-html='item.highLight'></p>
                                </div>
                            </div>
                            <div v-else class="doc-item-file-container">
                                <el-collapse-item :name="item.key">
                                    <template slot="title">
                                        <div class="doc-item-file-title-container" @click="stopProp">
                                            <div class="doc-item-file-title">
                                                <div class="doc-item-file-title-main">
                                                    <img v-if="item.docType === 20" style="width: 20px;height: auto" :src="viewImageUrl" alt=""/>
                                                    <app-svg v-else :iconText="item.suffix"></app-svg>
                                                    <a href="javascript:void(0)"
                                                        @click="handleClickFile(item)"
                                                        style="margin-left:5px;text-decoration-line:none;font-size:14px;" >
                                                        <b v-if="item.docType === 20">{{item.name}}</b>
                                                        <b v-else>{{item.name}}.{{item.suffix}}</b>
                                                    </a>
                                                    <span style="padding-left: 50px; font-size: 12px;color: #969896">{{dateShortFormat(item.lastUpdateDate)}}</span>
                                                    <span style="padding-left: 20px; font-size: 12px;color: #969896">{{item.size}}</span>
                                                    <span style="padding-left: 20px; font-size: 12px;color: #969896">{{item.lastUpdateUserName}}</span>
                                                    <span style="padding-left: 20px; font-size: 12px;">
                                                        <template v-if="item.hasDatabase">
                                                            <el-tooltip class="item" effect="dark" content="预览数据库" placement="bottom">
                                                                <el-button v-if="item.permissionMap.modify"
                                                                        class="btnEdit" type="text" size="mini" style="color: black"
                                                                        icon="iconfont icon-wenzhangpailie-"
                                                                        @click="handleOpenReviewDB(item)"
                                                                        circle></el-button>
                                                            </el-tooltip>
                                                        </template>
                                                    </span>
                                                </div>
                                                <div class="doc-item-file-icon-list" v-if="item.permissionMap.read">
                                                    <el-tooltip class="item" effect="dark" content="文件下载" placement="bottom" v-if="item.docType !== 20">
                                                        <el-button v-if="item.permissionMap.download"
                                                            class="btnAdd" type="text" size="mini"
                                                            icon="iconfont icon-caozuo-shangchuan-download"
                                                            @click="$refs.uploadFile.handleDownloadSubmit(item)"
                                                            circle></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="文件更新" placement="bottom">
                                                        <el-button v-if="item.permissionMap.modify"
                                                                class="btnEdit" type="text" size="mini"
                                                                icon="iconfont icon-caozuo-shangchuan-upload"
                                                                @click="$refs.uploadFile.handleEditFile(item)"
                                                                circle></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="重命名" placement="bottom">
                                                        <el-button v-if="item.permissionMap.modify"
                                                                class="btnEdit" type="text" size="mini"
                                                                icon="iconfont icon-edit-row"
                                                                @click="$refs.editFolder.handleReName(item)"
                                                                circle></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="文件版本履历" placement="bottom">
                                                        <el-button v-if="item.permissionMap.read"
                                                                class="btnEdit" type="text" size="mini"
                                                                icon="iconfont icon-wenzhangpailie-"
                                                                @click="$refs.preview.handleHistory(item)"
                                                                circle></el-button>
                                                    </el-tooltip>
                                                    <el-tooltip class="item" effect="dark" content="文件删除" placement="bottom">
                                                        <el-button v-if="item.permissionMap.delete"
                                                                class="btnDel" type="text" size="mini"
                                                                icon="el-icon-close"
                                                                @click="$refs.editFolder.handleDeleteSubmit(item)"
                                                                circle></el-button>
                                                    </el-tooltip>
                                                </div>
                                            </div>
                                            <div class="doc-item-file-content">
                                                <div style="padding: 5px">
                                                    <p v-html='item.highLight' style="color: #333333"></p>
                                                </div>
                                                <div style="padding: 2px">
                                                    <p style="font-size: 12px;color: #969896">
                                                        <span v-html="item.parentPathName" @click="handleCollapse(item)"></span>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </template>
                                    <div>
                                        <el-form label-position="left" inline class="doc-item-content-expand">
                                            <template v-for="(it, idx) in item.expandData">
                                                <el-form-item :key="idx" :label="it.title">
                                                    <span>{{ it.content }}</span>
                                                </el-form-item>
                                            </template>
                                        </el-form>
                                        <template>
                                            <div v-for="(it, idx) in item.appendFiles" :key="idx" class="resource-file-list">
                                                <el-button type="text" @click="handlePreviewAppendFile(item, it.id)">
                                                    <app-svg :iconText="it.suffix"></app-svg>
                                                    <span>{{it.name}}.{{it.suffix}}</span>
                                                </el-button>
                                                <el-tooltip class="item" effect="dark" content="下载文件" placement="bottom">
                                                    <el-button v-if="item.permissionMap.download"
                                                        class="btnAdd" type="text" size="mini" style="margin-left: 10px;"
                                                        icon="iconfont icon-caozuo-shangchuan-download"
                                                        @click="handleDownloadResource(it)"
                                                        circle></el-button>
                                                </el-tooltip>
                                            </div>
                                        </template>
                                    </div>
                                </el-collapse-item>
                            </div>
                        </el-collapse>
                    </div>
                    <infinite-loading ref="infinite" @infinite="nextPage" >
                        <span slot="no-more">
                            已经没有啦~~
                        </span>
                    </infinite-loading>
                </div>
                <!--<el-table ref="table" v-loading="searchTable.loading" :data="searchTable.body" highlight-current-row-->
                          <!--:cell-style="{padding:'5px'}" class="custom-table-cell"-->
                          <!--:header-cell-style="{padding: '2px'}" :height="searchTable.height">-->
                    <!--<el-table-column type="expand">-->
                        <!--<template slot-scope="props">-->
                            <!--<template v-if="props.row.highLight">-->
                                <!--<el-row style="padding-left: 25px" >-->
                                    <!--<el-col :span="24"><p v-html='props.row.highLight'></p>-->
                                        <!--<span></span>-->
                                    <!--</el-col>-->
                                <!--</el-row>-->
                            <!--</template>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column prop="name" label="文件名" width="375px" show-overflow-tooltip=true>-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'">-->
                                <!--<div v-if="scope.row.permissionMap.read">-->
                                    <!--<app-svg iconClass="wenjianjia3"></app-svg>-->
                                    <!--<a href=" javascript:void(0) "-->
                                       <!--style="margin-left:10px;text-decoration-line:none;"><b>{{scope.row.name}}</b></a>-->
                                <!--</div>-->
                                <!--<div v-else>-->
                                    <!--<app-svg iconClass="wenjianjia3"></app-svg>-->
                                    <!--<span style="margin-left:10px;">{{scope.row.name}}</span>-->
                                <!--</div>-->
                            <!--</div>-->
                            <!--<div v-else>-->
                                <!--<div v-if="scope.row.permissionMap.read">-->
                                    <!--<app-svg :iconText="scope.row.suffix"></app-svg>-->
                                    <!--<a href=" javascript:void(0) " @click="$refs.preview.handlePreview(scope.row)"-->
                                       <!--style="margin-left:10px;text-decoration-line:none;" ><b>{{scope.row.name}}.{{scope.row.suffix}}</b></a>-->
                                <!--</div>-->
                                <!--<div v-else>-->
                                    <!--<app-svg :iconText="scope.row.suffix"></app-svg>-->
                                    <!--<span style="margin-left:10px;">{{scope.row.name}}.{{scope.row.suffix}}</span>-->
                                <!--</div>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="文件路径" align="left" show-overflow-tooltip=true>-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'">-->
                            <!--</div>-->
                            <!--<div v-else>-->
                                <!--<p>{{scope.row.parentPathName}}</p>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="最后修改时间" align="center" width="200px">-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'">-->
                            <!--</div>-->
                            <!--<div v-else>-->
                                <!--<el-tooltip class="item" effect="dark" placement="bottom">-->
                                    <!--<div slot="content">{{dateFormat(scope.row.lastUpdateDate)}}</div>-->
                                    <!--<p>{{dateShortFormat(scope.row.lastUpdateDate)}}</p>-->
                                <!--</el-tooltip>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="文件大小" align="center" width="120px">-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'">-->
                            <!--</div>-->
                            <!--<div v-else>-->
                                <!--<p>{{scope.row.size}}</p>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="最后修改者" align="center" width="120px">-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'">-->
                            <!--</div>-->
                            <!--<div v-else>-->
                                <!--<p>{{scope.row.lastUpdateUserName}}</p>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<el-table-column label="操作" align="center" width="225px">-->
                        <!--<template slot-scope="scope">-->
                            <!--<div v-if="scope.row.kind === 'folder'" style="float:right; margin-right: 25px;" >-->
                                <!--<el-tooltip class="item" effect="dark" content="重命名" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.modify"-->
                                               <!--class="btnEdit" type="text" size="mini"-->
                                               <!--icon="iconfont icon-edit-row"-->
                                               <!--@click="handleReName(scope.$index, scope.row)"></el-button>-->
                                <!--</el-tooltip>-->
                                <!--<el-tooltip class="item" effect="dark" content="文件夹删除" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.delete"-->
                                               <!--class="btnDel" type="text" size="mini"-->
                                               <!--icon="iconfont icon-trash"-->
                                               <!--@click="handleDeleteSubmit(scope.$index, scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                            <!--</div>-->
                            <!--<div v-else  style="float:right; margin-right: 25px;" >-->
                                <!--<el-tooltip class="item" effect="dark" content="文件下载" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.download"-->
                                                <!--class="btnAdd" type="text" size="mini"-->
                                               <!--icon="iconfont icon-caozuo-shangchuan-download"-->
                                               <!--@click="$refs.uploadFile.handleDownloadSubmit(scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                                <!--<el-tooltip class="item" effect="dark" content="重命名" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.modify"-->
                                               <!--class="btnEdit" type="text" size="mini"-->
                                               <!--icon="iconfont icon-edit-row"-->
                                               <!--@click="$refs.editFolder.handleReName(scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                                <!--<el-tooltip class="item" effect="dark" content="文件更新" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.update"-->
                                               <!--class="btnAdd" type="text" size="mini"-->
                                               <!--icon="iconfont icon-caozuo-shangchuan-upload"-->
                                               <!--@click="$refs.uploadFile.handleEditFile(scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                                <!--<el-tooltip class="item" effect="dark" content="文件版本履历" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.read"-->
                                               <!--class="btnEdit" type="text" size="mini"-->
                                               <!--icon="iconfont icon-wenzhangpailie-"-->
                                               <!--@click="$refs.preview.handleHistory(scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                                <!--<el-tooltip class="item" effect="dark" content="文件删除" placement="bottom">-->
                                    <!--<el-button v-if="scope.row.permissionMap.delete"-->
                                               <!--class="btnDel" type="text" size="mini"-->
                                               <!--icon="iconfont icon-trash"-->
                                               <!--@click="$refs.editFolder.handleDeleteSubmit(scope.row)"-->
                                               <!--circle></el-button>-->
                                <!--</el-tooltip>-->
                            <!--</div>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                    <!--<infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">-->
                        <!--<span slot="no-more"></span>-->
                    <!--</infinite-loading>-->
                <!--</el-table>-->
            </el-main>
            <el-aside width="300px" style="background-color: rgb(242, 246, 249);height: 100%;">
                <div style="padding:20px 10px 20px 20px;background-color: rgb(242, 246, 249)">
                    <span style="font-size: 14px;color: gray">{{searchTable.total}}条检索结果</span><br><br>
                    <span style="font-size: 16px;"><b>聚类</b></span><br><br>
                    <div class="cluster_param" v-show="clusterParamShow">
                        <div v-for="(item,index) in clusterParamList">
                            <div class="cluster_param_item fxb" v-for="(value,key) in item">
                                <span class="word-hidden">
                                    <b>{{getClusterName(key)}}：</b>
                                    <el-tooltip :content="value" placement="bottom" effect="light">
                                        <span>{{value}}</span>
                                    </el-tooltip>
                                </span>
                                <i class="el-icon-error finger" @click="removeClusterSearch(key,value)"></i>
                            </div>
                        </div>
                    </div>
                    <el-collapse :accordion="false" v-model="activeName" @change="collapseHandleChange" >
                        <div class="collapse-item" v-for="(item,index) in clusterList">
                            <el-collapse-item  v-for="(value,key) in item" :name="key">
                                <template slot="title">
                                    {{getClusterName(key)}}
                                </template>
<!--                                <div v-for="(value,key) in item">-->
<!--                                    <div style="width: 100%" v-for="(clusterValue,clusterKey) in value">-->
<!--                                        <div class="cluster-item fx-lr finger" v-for="(valueCount,keyName) in clusterValue" @click="clusterSearch(key,keyName)" v-if="valueCount > 0">-->
<!--                                            <div style="font-size: 14px">{{keyName}}</div>-->
<!--                                            <div class="cluster-tag">{{valueCount}}</div>-->
<!--                                        </div>-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                                <div :id="'clusterCharts_'+index" style="width:100%;height:100%">{{index}}</div>-->
                                <div class="chartsBox">
                                    <div :id="'clusterCharts_'+index" class="charts"></div>
                                </div>
                            </el-collapse-item>
                        </div>
                    </el-collapse>
                </div>
            </el-aside>
        </el-container>
        <preview-database ref="previewDatabase"></preview-database>
        <edit-folder ref="editFolder" :rename-folder="renameFolder" :delete-folder="deleteFolder"  ></edit-folder>
        <upload-file ref="uploadFile" :uploaded="refresh"></upload-file>
        <link-file ref="linkFile" :linked="refresh"></link-file>
        <preview ref="preview"></preview>
    </el-container>
</template>

<script>
    import PreviewDatabase from '../doc/PreviewDatabase';
    import editFolder from '../doc/EditFolder'
    import uploadFile from '../doc/UploadFile'
    import linkFile from '../doc/LinkFile'
    import preview from '../doc/Preview'
    import {checkTime} from '../../util/initData';
    import {mapActions, mapGetters } from 'vuex'
    export default {
        components:{editFolder, uploadFile, linkFile, preview, PreviewDatabase},
        computed:{
            ...mapGetters({
                getClusterName:'constant/getClusterName',
            }),
        },
        name: "message",
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                searchTxt: '',
                searchKey: '',
                searchTable: {
                    loading: false,
                    data: [],
                    height:0,
                    pageSize: 5,
                    total: 0,
                    curPage: 0,
                    multipleSelection: [],
                    body: [],
                },
                tagBar:{
                    loading: true,
                    body: [],
                    inputVisible: false,
                    inputValue: '',
                },
                activeCollapse: [],
                clusterList: [],
                clusterParamList: [],
                paramStr:'',
                clusterParamShow:false,
                activeName: ['author','authorUnit','projectName','keyword','docType'],
                yLabel:'',
            }
        },
        mounted() {
            this.searchKey = this.$route.query.searchKey
            this.getTag()
        },
        methods: {
            ...mapActions({
                curFolderAction: 'folderHistory/curFolder',
                nextFolderAction: 'folderHistory/nextFolder',
            }),
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    if(this.$refs.table){
                        this.searchTable.height = window.innerHeight - this.$refs.table.$el.offsetTop - 60;
                        let self = this;
                        window.onresize = function() {// 监听窗口大小变化
                            self.searchTable.height = window.innerHeight - self.$refs.table.$el.offsetTop - 60
                        }
                    }
                })
            },
            tagSearch(tag){
                this.searchKey = tag
                this.refresh()
            },
            getData($state) {
                this.searchTable.loading = this.$refs.infinite.isFirstLoad
                let encodeURIComponentString = encodeURIComponent(this.searchKey)
                this.$axios({
                    url: `/search/key/${this.searchTable.curPage}-${this.searchTable.pageSize}?key=` + encodeURIComponentString+"&paramStr="+this.paramStr,
                    method: 'get'
                }).then(res => {
                    this.clusterList = res.ctt.solrFacet;
                    let permissionBody = [];
                    for( let i = 0; i < res.ctt.pageList.list.length; i++){
                        if (res.ctt.pageList.list[i].permissionMap.visible){
                            permissionBody.push(res.ctt.pageList.list[i])
                        }
                    }
                    this.searchTable.body = this.searchTable.body.concat(permissionBody).map((it, idx) => {
                        let expandData = [];
                        let hasDatabase = false;
                        let appendFiles = [];
                        let dbId = null;
                        let dbName = '';
                        if (it.fileAccessoryDto) {
                            appendFiles = it.fileAccessoryDto.filter((i) => i.type !== 2);
                            // console.log(appendFiles);
                            let isDb = it.fileAccessoryDto.find((i) => i.type === 2);
                            if (isDb) {
                                hasDatabase = true;
                                dbId = isDb.saveId;
                                dbName = isDb.name;
                            }
                        }
                        if (it.fileMetadataDto) {
                            expandData = it.fileMetadataDto.map((i) => {
                                let content = i.value;
                                if (i.dataType === 7) {
                                    let date = new Date(i.value);
                                    content = `${date.getFullYear()}年${checkTime(date.getMonth() + 1)}月${checkTime(date.getDate())}日`;
                                }
                                if (i.dataType === 6) {
                                    content = i.value === 1 ? '是' : '否';
                                }
                                return {
                                    title: i.name,
                                    content,
                                };
                            });
                        }
                        return {
                            ...it,
                            key: `${it.kind}-${idx}-${it.id}`,
                            expandData,
                            hasDatabase,
                            dbId,
                            dbName,
                            appendFiles,
                        };
                    });
                    this.searchTable.total = res.ctt.pageList.total
                    this.searchTable.loading = false

                    if (res.ctt.pageList.list.length == 0){
                        if($state) $state.complete()
                    }
                    else{
                        if($state) $state.loaded()
                    }
                    this.$nextTick(() => {
                        this.tableHeight()
                        this.initCharts(null)
                    })
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            initCharts(i){
                if(i == null){
                    for(let i = 0;i < this.clusterList.length; i++){
                        this.setOption(i);
                    }
                }else{
                    this.setOption(i);
                }
            },
            setOption(i){
                let colors =
                    [
                        ['#81B587','#77AB7D','#6DA173','#639769','#598D5F','#4F8355','#45794B'],
                        ['#6195FD','#578BF3','#4D81E9','#4377DF','#396DD5','#2F62CB','#2558C1'],
                        ['#FFB4C1','#FAAAB7','#F0A0AD','#E696A3','#DC8C99','#D2828F','#C16F7B'],
                        ['#A07FD5','#9675CB','#8C6BC1','#8261B7','#50398F','#7857AD','#6E4DA3'],
                        ['#FAB650','#F0AC46','#E6A23C','#DC9832','#D28E28','#C8841E','#BE7A14']
                    ];
                let id = 'clusterCharts_'+i;
                let dom = document.getElementById(id)
                let myChart = this.$echarts.init(dom);
                let source = [['amount', 'product']]
                for(let key in this.clusterList[i]){
                    let newArry = this.clusterList[i][key];

                    let clusterItem = newArry.sort(function(a, b) {
                        for(let keyA in a){
                            for(let keyB in b){
                                return a[keyA] - b[keyB];
                            }
                        }
                    });
                    for(let j = 0; j < clusterItem.length; j++){
                        let sourceItem = [];
                        for(let keyItem in clusterItem[j]){
                            if(clusterItem[j][keyItem] > 0){
                                sourceItem.push(clusterItem[j][keyItem])
                                sourceItem.push(keyItem)
                            }
                        }
                        if(sourceItem.length > 0){
                            source.push(sourceItem)
                        }
                    }
                }
                let option = {
                    dataset: {
                        source: source
                    },
                    grid: {
                        containLabel: false,
                        //去除外层边框
                        borderWidth:0,
                        //top:'-30%',
                        left: '26%',
                        bottom: '20%',
                    },
                    xAxis: {
                        show : false,
                        name: 'amount',
                        splitLine:{
                            show:false
                        },

                    },
                    yAxis: {
                        type: 'category',
                        triggerEvent:true,
                        axisLabel: {
                            show:  true ,
                            interval:  0,
                            rotate: 0,
                            textStyle: {        // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color:  '#333'
                            },
                            formatter: function (params) {
                                if(params === undefined){
                                    return '';
                                }
                                if(params.length > 4){
                                    return params.substring(0,4)+"...";
                                }else{
                                    return params;
                                }
                            }
                        },
                        //隐藏行内分割线
                        splitLine:{
                            show:false
                        },
                        //隐藏坐标轴坐标线
                        axisLine: {
                            show:false
                        },
                        //隐藏坐标轴小标记
                        axisTick: {
                            show: false
                        },
                    },
                    series: [
                        {
                            type: 'bar',
                            encode: {
                                // Map the "amount" column to X axis.
                                x: 'amount',
                                // Map the "product" column to Y axis
                                y: 'product'
                            },
                            // 实现数字展示在柱状图
                            label: {
                                show: true,
                                position: 'inside',
                                color:'white'
                            },
                            // //设置柱状图的宽度
                            barWidth:16,
                            itemStyle:{
                                normal: {
                                    //这里是重点
                                    color: function (params) {
                                        //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                                        let colorList = colors[i];
                                        return colorList[params.dataIndex];
                                    },
                                },
                                barBorderRadius: [50, 50, 50, 50], // 统一设置四个角的圆角大小
                            },
                            //barGap:'-30%',/*多个并排柱子设置柱子之间的间距*/

                        }
                    ],
                    tooltip: {formatter: this.formateTooltip},
                };
                myChart.off('mouseover')
                //光标悬浮标签上，显示完整名称
                myChart.on('mouseover', (params)=> {
                    if (params.componentType === 'yAxis') {
                        this.yLabel = params.value
                        let offsetX = params.event.offsetX + 10
                        let offsetY = params.event.offsetY + 10
                        myChart.setOption({tooltip: {
                                formatter: this.formateTooltip,
                                alwaysShowContent: true
                            }})
                        myChart.dispatchAction({
                            type: 'showTip',
                            seriesIndex: 0,
                            dataIndex: 0,
                            position: [offsetX, offsetY]
                        })
                    }
                })
                myChart.off('mouseout')
                myChart.on('mouseout', (params)=> {
                    if (params.componentType === 'yAxis') {
                        this.yLabel = ''
                        myChart.setOption({tooltip: {
                                formatter: this.formateTooltip,
                                alwaysShowContent: false
                            }})
                    }
                })
                myChart.off('click')//防止点击事件多次调用
                let this_ = this;
                myChart.on('click', function (params) {
                    if(params.data != null && params.data.length > 1) {
                        for (let index = 0; index < this_.clusterList.length; index++) {
                            for (let key in this_.clusterList[index]) {
                                let clusterItem = this_.clusterList[index][key];
                                for (let j = 0; j < clusterItem.length; j++) {
                                    for (let keyItem in clusterItem[j]) {
                                        if (clusterItem[j][keyItem] > 0) {
                                            if (params.data[1] === keyItem) {
                                                this_.clusterSearch(key, params.data[1]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                myChart.setOption(option);
                let autoHeight = (source.length - 1) * 50 + 50;
                myChart.getDom().style.height = autoHeight + "px";
                myChart.getDom().childNodes[0].style.height = autoHeight + "px";
                if(myChart.getDom().childNodes[0].childNodes[0] != undefined){
                    myChart.getDom().childNodes[0].childNodes[0].setAttribute("height",autoHeight);
                    myChart.getDom().childNodes[0].childNodes[0].style.height = autoHeight + "px";
                    myChart.resize();
                }
            },
            collapseHandleChange(val){
                for(let i = 0; i < this.clusterList.length; i++){
                    for(let key in this.clusterList[i]){
                        if(val == key){
                            setTimeout(()=>{
                                this.initCharts(Number(i))
                            },100)
                        }
                    }
                }
            },
            formateTooltip (params) {
                if(this.yLabel){
                    return `<div class="toolDiv">${this.yLabel}</div>`
                }
                return this.yLabel;
            },
            clusterSearch(key,value){
                for(let i = 0; i < this.clusterParamList.length; i++) {
                    for (let keyName in this.clusterParamList[i]) {
                        if(keyName == key && this.clusterParamList[i][keyName] == value){
                            return;
                        }
                    }
                }
                let json = {};
                json[key] = value;
                this.clusterParamList.push(json)
                let str = "@"+key+":"+"\""+value+"\"";
                this.paramStr = this.paramStr+str;
                this.refresh();
                this.clusterParamShow = true;
            },
            removeClusterSearch(key,value){
                this.clusterParamList = this.clusterParamList.filter((json)=>
                    json[key] != value
                )
                this.paramStr = "";
                if(this.clusterParamList.length == 0){
                    this.refresh();
                    this.clusterParamShow = false;
                }else{
                    for(let i = 0; i < this.clusterParamList.length; i++){
                        for(let key in this.clusterParamList[i]){
                            let str = "@"+key+":"+"\""+this.clusterParamList[i][key]+"\"";
                            this.paramStr = this.paramStr+str;
                            this.refresh();
                            this.clusterParamShow = true;
                        }
                    }
                }
            },
            //日期格式化
            dateShortFormat(data) {
                if (data === undefined || data === null) {
                    return "";
                }
                return this.$moment(data).format("MM月DD日 HH:mm");
            },
            //日期格式化
            dateFormat(data) {
                if (data === undefined || data === null) {
                    return "";
                }
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm");
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
            //检索
            search(){
                if(!this.searchTxt || !this.searchTxt.trim()) {
                    this.$message.warning("请输入搜索关键词")
                    return
                }
                let pattern = new RegExp("[/:*?'\"<>|\\\\]");
                if(pattern.test(this.searchTxt.trim())){
                    this.$message.warning("不能包含特殊字符：\\/:*?'\"<>|")
                    return
                }
                this.searchKey = this.searchTxt.trim()
                this.refresh()
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
            //画面刷新
            refresh(){
                this.searchTable.body = []
                this.searchTable.curPage = 0
                this.$refs.infinite.stateChanger.reset()
            },
            nextPage($state){
                this.searchTable.curPage += 1
                this.getData($state)
            },
            renameFolder(para) {
                let target;
                this.searchTable.body.forEach(c =>{
                    if (c.id === para.id && c.kind === para.kind){
                        target = c
                    }
                })
                if(target){
                    target.name = para.name
                }
            },
            deleteFolder(para) {
                this.refresh()
            },
            handleClickFile(item) {
                if (item.docType === 20) {
                    this.handleCollapse(item);
                } else {
                    this.$refs.preview.handlePreview(item);
                }
            },
            stopProp(e) {
                e.stopPropagation();
            },
            handleCollapse(item) {
                if (item.docType !== 20) {
                    return;
                }
                if (this.activeCollapse.includes(item.key)) {
                    this.activeCollapse = this.activeCollapse.filter(i => i !== item.key);
                } else {
                    this.activeCollapse.push(item.key);
                }
            },
            handleDownloadResource(item) {
                // this.handleDownloadSubmit(0, item);
                this.$confirm('开始下载吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.$axios({
                        url: `user/file/${item.saveId}/download`,
                        method: 'get',
                        responseType: 'blob',
                    }).then((res) => {
                        if (!res) {
                            this.$message({
                                message: '下载失败',
                                type: 'error'
                            });
                            return
                        }
                        const content = res;
                        const blob = new Blob([content]);
                        const fileName = `${item.name}.${item.suffix}`;
                        if ('download' in document.createElement('a')) {
                            const elink = document.createElement('a');
                            elink.download = fileName;
                            elink.style.display = 'none';
                            elink.href = URL.createObjectURL(blob);
                            document.body.appendChild(elink);
                            elink.click();
                            URL.revokeObjectURL(elink.href);
                            document.body.removeChild(elink);
                        } else {
                            console.log(blob);
                            navigator.msSaveBlob(blob, fileName);
                        }
                    });
                })
            },
            handlePreviewAppendFile(item, nextId) {
                let itemNext = {
                    ...item,
                    nextId,
                };
                this.$refs.preview.handlePreview(itemNext);
            },
            handleOpenReviewDB(item) {
                this.$refs['previewDatabase'].dbId = item.dbId;
                this.$refs['previewDatabase'].visible = true;
            },
        }
    }
</script>

<style lang="scss">
    .custom-search-table-cell {
        padding: 20px;
        display: inline-block;
        border-bottom: 1px solid #dcdfe6;
        width: 95%;
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
            color: #949494;
        }
    }
    .doc-item-file-container {
        .el-collapse {
            border-top: none;
            border-bottom: none;
            .el-collapse-item__header {
                border-bottom: none;
                font-size: inherit;
                height: inherit;
                line-height: inherit;
                & > div {
                    width: 100%;
                }
            }
            .el-collapse-item__wrap {
                border-bottom: none;
            }
            .el-collapse-item__content {
                padding-bottom: 0;
                padding-top: 20px;
            }
            .el-collapse-item__arrow {
                display: none;
            }
        }
        .doc-item-file-title {
            display: flex;
            align-items: center;
            justify-content: space-between;
            .doc-item-file-icon-list {
                padding-right: 25px;
            }
        }
        .doc-item-content-expand {
            font-size: 0;
            .el-form-item {
                display: flex;
                margin-right: 0;
                margin-bottom: 0;
                label {
                    width: 90px;
                    color: #99a9bf;
                    font-size: 12px;
                }
                .el-form-item__content {
                    font-size: 12px;
                    word-break: break-all;
                    max-width: 80%;
                }
            }
        }

    }
    .collapse-item{
            .el-collapse-item__header {
                font-size: 14px;
                font-weight: 600;
                height: inherit;
                line-height: inherit;
                background-color:rgb(242, 246, 249);
                border:none;
                margin-bottom: 14px;
            }
            .el-collapse-item__wrap {
                border-bottom: none;
                background-color:rgb(242, 246, 249);
            }
            .el-collapse-item__content {
                padding-bottom: 0;
            }

    }
    .resource-file-list {
        display: flex;
        align-items: center;
        margin-top: 10px;
    }
    .cluster-item{
        margin-left: 10px;
        padding-bottom: 6px;
        padding-right: 6px;
    }
    .cluster-tag{
        background-color: rgb(223, 235, 243);
        color: rgb(121, 148, 168);
        border-radius: 5px;
        padding-left: 6px;
        padding-right: 6px;
    }
    .cluster_param{
        border:1px solid gray;
        width:100%;
        border-radius: 6px;
        margin-bottom: 6px;
    }
    .cluster_param_item{
        padding:0px 12px 0px 12px;
        width:90%;
        height:40px;
    }
    .chartsBox{
        position: relative;
        bottom: 50px;
    }
    .charts {
        width: 100%;
        height: 9.2rem;
    }
    //echart tooltip自动换行
    .toolDiv{
        white-space:normal; // 不设的话会继承父级的 white-space:no-wrap;wrap;就没法换行了
    }

</style>

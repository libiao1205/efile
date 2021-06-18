<template>
    <el-container>
        <el-header height="79" class="pd0" >
<!--            <div class="app-title fxl bb1 pd10">-->
<!--                <div class="fxr">-->
<!--                    <el-button-group>-->
<!--                        <el-button size="mini" icon="el-icon-arrow-left" @click="preFolderAction"></el-button>-->
<!--                        <el-button size="mini" @click="nextFolderAction"><i class="el-icon-arrow-right el-icon&#45;&#45;right" ></i></el-button>-->
<!--                    </el-button-group>-->
<!--                    <el-breadcrumb separator-class="el-icon-arrow-right" class="pl10" :data="folderHeader">-->
<!--                        <el-breadcrumb-item-->
<!--                            v-for='(item) in folderHeader.item'-->
<!--                            :key='item.id'-->
<!--                            >-->
<!--                            <template v-if='item.name'>-->
<!--                                <a href="javascript:void(0);" style="cursor: pointer;" @click="breadcrumbClick(item)">-->
<!--                                    {{item.name}}-->
<!--                                </a>-->
<!--                            </template>-->
<!--                        </el-breadcrumb-item>-->
<!--                    </el-breadcrumb>-->
<!--                </div>-->
<!--                <div class="ib fxl searchBox fxc">-->
<!--                    <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "searchDoc" clearable></el-input>-->
<!--                    <div @click="searchDoc" slot="append" class="fx-t-b" style="height:34px;width: 60px;border: 1px solid #409EFF;background-color: #409EFF;border-bottom-right-radius:34px;border-top-right-radius:34px;">-->
<!--                        <i class="el-icon-search" style="font-size: 24px;color: white"></i>-->
<!--                    </div>-->
<!--                </div>-->
<!--            </div>-->
            <div class="fxb" style="padding:10px 0px 10px 0px;background-color: #FAFAFA;">
                <div v-if="this.selectFolder" class="fx-lr pd5">
                    <el-dropdown  class="plr10" v-if="this.selectFolder.permissionMap.new" @command="handleCommand">
                        <el-button type="primary" size="mini" @click="handleUploadResource('create')">
                            <svg style="width: 1.2rem; height: 1rem;" t="1615781333955" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="15073">
                                <path d="M826 548.9c-22 0-40 18-40 40v200H236v-202c0-22-18-40-40-40s-40 18-40 40v242c0 22 18 40 40 40h630c22 0 40-18 40-40v-240c0-22-18-40-40-40z" p-id="15074" fill="#ffffff"></path><path d="M273.1 489.6h128.5v156.3c0 38.4 31.2 69.6 69.6 69.6h80c38.4 0 69.6-31.2 69.6-69.6V489.6h128.1c22.7 0 41.1-18.4 41.1-41.1 0-10.5-3.9-20.1-10.4-27.3l0.3-0.3-239-239-0.1 0.1c-7.4-7.5-17.8-12.2-29.2-12.2-12.8 0-24.2 5.8-31.7 14.9L250.2 414.4c-10.9 7.4-18.1 19.9-18.1 34.1 0 22.7 18.3 41.1 41 41.1z" p-id="15075" fill="#ffffff"></path>
                            </svg>
                            文献上传
                        </el-button>
                        <el-dropdown-menu  v-if="getBrowser()!=='IE' && getBrowser()!=='Edge'" slot="dropdown">
                            <el-dropdown-item icon="el-icon-upload" command="open">文献上传</el-dropdown-item>
                            <el-dropdown-item icon="el-icon-upload" command="fileOpen">资源上传</el-dropdown-item>
                            <!-- <el-dropdown-item icon="el-icon-upload" command="folderOpen">上传文件夹</el-dropdown-item> -->
                        </el-dropdown-menu>
                    </el-dropdown>
                    <!-- <el-button type="primary" size="mini" icon="el-icon-upload" @click="handleUploadResource" plain>文献上传</el-button> -->
                    <div class="plr10 buttonBox">
                        <el-button v-if="this.selectFolder.permissionMap.new" type="primary" size="mini" @click="handleAddFolder" >
                            <svg style="width: 1.2rem; height: 1rem;" t="1615780650144" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1802" width="200" height="200">
                                <path d="M874.24 150.016C777.6 53.376 648.96 0.256 512 0.256s-265.6 53.12-362.24 149.76c-199.68 199.68-199.68 524.16 0 723.84 96.64 96.64 225.28 149.76 362.24 149.76s265.6-53.12 362.24-149.76c96.64-96.64 149.76-224.64 149.76-361.6s-53.12-265.6-149.76-362.24z m-72.32 652.16c-77.44 76.8-180.48 119.68-289.92 119.68-109.44 0-212.48-42.88-289.92-119.68-160-160-160-419.84 0-579.2 77.44-77.44 180.48-119.68 289.92-119.68 109.44 0 212.48 42.88 289.92 119.68 77.44 77.44 119.68 180.48 119.68 289.92 0 109.44-42.88 211.84-119.68 289.28z m-85.12-341.12h-153.6v-153.6c0-28.16-23.04-51.2-51.2-51.2s-51.2 23.04-51.2 51.2v153.6h-153.6c-28.16 0-51.2 23.04-51.2 51.2s23.04 51.2 51.2 51.2h153.6v153.6c0 28.16 23.04 51.2 51.2 51.2s51.2-23.04 51.2-51.2v-153.6h153.6c28.16 0 51.2-23.04 51.2-51.2s-23.04-51.2-51.2-51.2z" p-id="1803" fill="#29A5FA"></path>
                            </svg>
                            新建文件夹
                        </el-button>
                        <el-button v-if="this.selectFolder.permissionMap.link" type="primary" size="mini" @click="handleAddLink" style="width: 80px" >
                            <svg style="width: 1.2rem; height: 1rem;" t="1615783875077" class="icon" viewBox="0 0 1027 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="36773" width="200" height="200">
                                <path d="M734.72 964.48H106.24a103.04 103.04 0 0 1-103.04-103.04V225.28A103.04 103.04 0 0 1 106.24 121.6h409.6a51.84 51.84 0 0 1 0 103.68h-409.6v636.16h628.48V576a51.84 51.84 0 1 1 103.04 0v287.36a103.04 103.04 0 0 1-103.04 101.12zM847.36 450.56l179.84-195.84-92.16-94.08-87.68-89.6v379.52z" p-id="36774" fill="#29A5FA"></path><path d="M542.08 618.24H438.4a409.6 409.6 0 0 1 408.96-408.96v103.68a305.92 305.92 0 0 0-305.28 305.28z" p-id="36775" fill="#29A5FA"></path>
                            </svg>
                            分享
                        </el-button>
                        <el-button v-if="this.selectFolder.permissionMap.delete" type="primary" size="mini" @click="handleMultiDeleteSubmit" style="width: 80px">
                            <svg style="width: 1.2rem; height: 1rem;" t="1615781143926" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="12292">
                                <path d="M356.5 202l48.3-96.6c2.2-4.4 6.7-7.2 11.6-7.2h191.5c4.9 0 9.4 2.8 11.6 7.2l48.3 96.6h142.7c7.2 0 13 5.8 13 13v77.8c0 7.2-5.8 13-13 13H213.8c-7.2 0-13-5.8-13-13V215c0-7.2 5.8-13 13-13h142.7z m-90.8 155.6h492.9c7.2 0 13 5.8 13 13v505.8c0 28.7-23.2 51.9-51.9 51.9h-415c-28.7 0-51.9-23.2-51.9-51.9V370.6c-0.1-7.2 5.7-13 12.9-13z m142.6 181.6v207.5c0 14.3 11.6 25.9 25.9 25.9 14.3 0 25.9-11.6 25.9-25.9V539.2c0-14.3-11.6-25.9-25.9-25.9-14.2 0-25.9 11.6-25.9 25.9z m155.7 0v207.5c0 14.3 11.6 25.9 25.9 25.9 14.3 0 25.9-11.6 25.9-25.9V539.2c0-14.3-11.6-25.9-25.9-25.9-14.3 0-25.9 11.6-25.9 25.9z" p-id="12293" fill="#29A5FA"></path>
                            </svg>
                            删除
                        </el-button>
                    </div>
                    <!--<input ref="folder" id="open" type="file"  v-if="folderClear" style=display:none webkitdirectory @change.stop="onChangeStopProcess()" size="0"/>-->
                    <div v-if="folderHeader" class="fxr">
                        <span style="color:#DEBF8B;font-size: 12px;margin-right: 30px">上传限制：<b>{{folderHeader.size}}MB</b></span>
                        <span style="color:#A8B0B5;font-size: 12px">文件夹数：<b>{{folderHeader.selfFolderCount}}  </b> 文件数：<b>{{folderHeader.selfFileCount}}</b></span>
                    </div>
                </div>
                <div class="fxc pdr20">
                    <div class="fxl mr20">
<!--                                                <el-button size="mini" icon="el-icon-d-caret" type="primary" @click="$refs.sortFolder.handleSort(selectFolder)" plain> 文件夹排序</el-button>-->
                        <el-select v-model="rankField" @change="refresh" style="width: 120px;height: 30px" size="mini">
                            <el-option
                                v-for="(item, index) in rankList"
                                :key="index"
                                :label="item.name"
                                :value="item.value"
                            ></el-option>
                        </el-select>
                        <div style="border:1px solid #0099FF;padding: 2px;width:1.6rem;border-radius: 4px;margin-left: 6px" class="fx-lr-c finger" @click="rank()" >
                            <svg style="width:1.2rem;height:1.2rem" v-if="sort == 'ASC'" t="1617770647367" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4747" ><path d="M771.104 331.584v435.968a30.944 30.944 0 0 1-9.6 22.912c-6.304 6.08-14.944 9.536-24 9.536-18.624-0.32-33.568-14.784-33.568-32.448V331.584L569.632 458.88a33.792 33.792 0 0 1-23.84 9.664c-9.024 0-17.632-3.488-23.872-9.664a31.008 31.008 0 0 1-9.92-22.592c0-8.512 3.584-16.64 9.92-22.592L712 233.568c6.176-6.08 14.656-9.504 23.52-9.536 8.896-0.32 17.568 2.624 24.192 8.256l190.08 180.128A30.4 30.4 0 0 1 960 435.008a30.4 30.4 0 0 1-10.24 22.592 34.56 34.56 0 0 1-23.84 9.408 34.56 34.56 0 0 1-23.84-9.408L771.104 331.584zM88.864 736h398.24c13.76 0 24.896 14.176 24.896 31.68 0 17.504-11.136 31.68-24.896 31.68H88.896C75.136 799.36 64 785.184 64 767.68 64 750.176 75.136 736 88.896 736z m1.792-256h266.688c14.72 0 26.656 14.176 26.656 31.68 0 17.504-11.936 31.68-26.656 31.68H90.656C75.936 543.36 64 529.184 64 511.68 64 494.176 75.936 480 90.656 480z m396.48-256H88.864C75.136 224 64 238.176 64 255.68c0 17.504 11.136 31.68 24.896 31.68h398.208c13.76 0 24.896-14.176 24.896-31.68 0-17.504-11.136-31.68-24.896-31.68z" fill="#29A5FA" p-id="4748"></path></svg>
                            <svg style="width:1.2rem;height:1.2rem" v-if="sort == 'DESC'" t="1617770718141" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4940" ><path d="M843.84 690.368V256.32a31.488 31.488 0 0 0-9.152-22.848 32.192 32.192 0 0 0-22.912-9.472 32.512 32.512 0 0 0-32 32.32v434.048l-128.192-126.72a31.552 31.552 0 0 0-45.44 0 31.488 31.488 0 0 0 0 44.928l181.312 179.328a31.552 31.552 0 0 0 22.4 9.536 32.896 32.896 0 0 0 23.04-8.32l181.376-179.2a30.912 30.912 0 0 0 0-45.056 32.192 32.192 0 0 0-45.504 0l-124.928 125.44zM32 730.88h512.512c17.728 0 32.064 14.208 32.064 31.68a31.872 31.872 0 0 1-32 31.68H32a31.872 31.872 0 0 1-32-31.68c0-17.472 14.336-31.68 32-31.68z m0-253.44h320.32c17.728 0 32 14.208 32 31.68a31.872 31.872 0 0 1-32 31.68H32.064A31.872 31.872 0 0 1 0 509.12c0-17.472 14.336-31.68 32-31.68zM544.576 224H32.064c-17.728 0-32 14.208-32 31.68s14.272 31.68 32 31.68h512.512c17.728 0 32-14.208 32-31.68a31.872 31.872 0 0 0-32-31.68z" fill="#29A5FA" p-id="4941"></path></svg>
                        </div>
<!--                        <el-button type="primary" size="mini" @click="rank()">-->

<!--                        </el-button>-->
                    </div>
                    <div class="finger" @click="refresh">
                        <svg style="margin-right: 4px" t="1615544331757" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="11287" width="200" height="200">
                            <path d="M900.13268 352.212595c-17.746424-30.688359-56.998953-41.124182-87.436598-23.438133-30.621843 17.622603-41.119065 56.748239-23.43404 87.373152 27.935627 48.373384 42.685769 103.682835 42.685769 159.868247 0 176.434783-143.558565 319.990278-319.991302 319.990278-176.43069 0-319.985162-143.555495-319.985162-319.990278 0-176.43069 143.554472-319.988232 319.985162-319.988232 0.099262 0 0.198524 0.00307 0.297786 0.00307 0.046049 38.789987 0.079819 68.711879 0.079819 72.371273 0 30.746688-0.312113 55.621563 32.496566 55.621563 16.626912 0 31.561251-6.498085 42.312257-16.876602l0.24969 0.125868c0 0 167.056051-156.99374 174.992925-163.492849 4.005276-3.375934 5.689662-7.625784 5.563794-11.749766-0.064469-3.998113-1.745785-7.875475-4.749231-10.624114C757.072472 175.655013 585.889369 15.531959 585.889369 15.531959l-0.310066 0.25276c-10.685513-9.621259-25.124565-15.748903-41.125205-15.748903-32.809702 0-32.43312 24.875898-32.43312 55.622587 0 3.524316 0.03377 33.492257 0.079819 72.375367-0.049119 0-0.097215-0.001023-0.146335-0.001023-247.057202 0-447.980045 200.991406-447.980045 447.983115 0 246.993756 200.923867 447.984139 447.980045 447.984139 246.994779 0 447.988232-200.990382 447.988232-447.984139C959.943717 497.39517 939.25627 420.021905 900.13268 352.212595z" p-id="11288"></path>
                        </svg>
                        <span > 刷新</span>
                    </div>
                    <!--<el-checkbox v-model="isShowAll" @change="showAllChange">显示隐藏文件夹</el-checkbox>-->
                </div>
            </div>
        </el-header>

        <el-main class="sys-content pd0" id="main_table" style="border:none">
            <div class="custom-search-table-cell doc-item-file-container" v-for="(item, index) in folderTable.body" :key="index">
                <el-collapse ref="collapsible" v-model="activeCollapse">
                    <div v-if="item.kind === 'folder'">
                        <div v-if="item.permissionMap.visible">
                            <el-collapse-item :name="item.key">
                                <template slot="title">
                                    <div class="doc-item-file-title-container" @click="stopProp">
                                        <div class="doc-item-file-title">
                                            <div class="doc-item-file-title-main">
                                                <el-checkbox v-model="item.checked"></el-checkbox>
                                                <app-svg iconClass="wenjianjia3" style="font-size: 24px;margin-right: 4px"></app-svg>
                                                <a href="javascript:void(0)" @click="handleClickFolder(index, item)" style="margin-left:5px;text-decoration-line:none;"><b>{{item.name}}</b></a>
                                            </div>
                                            <div class="doc-item-file-icon-list">
                                                <el-tooltip class="item" effect="dark" content="编辑文件夹" placement="bottom">
                                                    <el-button
                                                        v-if="item.permissionMap.modify"
                                                        class="btnEdit2" type="text" size="mini"
                                                        icon="iconfont icon-edit-node"
                                                        @click="handleReName(index, item)"
                                                        circle
                                                    ></el-button>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="删除文件夹" placement="bottom">
                                                    <el-button
                                                        v-if="item.permissionMap.delete"
                                                        class="btnDel" type="text" size="mini"
                                                        icon="el-icon-close"
                                                        @click="handleDeleteSubmit(index, item)"
                                                        circle
                                                    ></el-button>
                                                </el-tooltip>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </el-collapse-item>
                        </div>
                        <div v-else>
                            <app-svg iconClass="wenjianjia3"></app-svg>
                            <span style="margin-left:5px;">{{item.name}}</span>
                        </div>
                        <div>
                            <p v-html='item.highLight'></p>
                        </div>
                    </div>
                    <div v-else>
                        <el-collapse-item :name="item.key">
                            <template slot="title">
                                <div class="doc-item-file-title-container" @click="stopProp">
                                    <div class="doc-item-file-title">
                                        <div class="doc-item-file-title-main">
                                            <template>
                                                <el-checkbox v-model="item.checked"></el-checkbox>
                                                <img v-if="item.docType === 20" style="width: 28px;height: auto" :src="viewImageUrl" alt=""/>
                                                <app-svg v-else :iconText="item.suffix" style="font-size: 20px"></app-svg>
                                            </template>
                                            <a href="javascript:void(0)"
                                                @click="handleClickFile(item)"
                                                style="margin-left:5px;text-decoration-line:none;font-size:14px;" >
                                                <b v-if="item.docType === 20">{{item.name}}</b>
                                                <b v-else>{{item.name}}.{{item.suffix}}</b>
                                            </a>
                                            <span style="padding-left: 20px; font-size: 12px;">
                                                <template v-if="item.hasDatabase">
                                                    <el-tooltip class="item" effect="dark" content="预览数据库" placement="bottom">
                                                        <el-button v-if="item.permissionMap.modify"
                                                                class="btnEdit1" type="text" size="mini" style="color: gray"
                                                                icon="iconfont icon-wenzhangpailie-"
                                                                @click="handleOpenReviewDB(item)"
                                                                circle></el-button>
                                                    </el-tooltip>
                                                </template>
                                            </span>
                                        </div>
                                        <div class="doc-item-file-icon-list" >
                                            <el-tooltip class="item" effect="dark" content="下载文件" placement="bottom" v-if="item.docType !== 20">
                                                <el-button v-if="item.permissionMap.download"
                                                    class="btnAdd" type="text" size="mini"
                                                    icon="iconfont icon-caozuo-shangchuan-download"
                                                    @click="handleDownloadSubmit(index, item)"
                                                    circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="编辑文件路径和名称" placement="bottom">
                                                <el-button v-if="item.permissionMap.modify"
                                                        class="btnEdit1" type="text" size="mini"
                                                        icon="iconfont icon-edit-file"
                                                        @click="handleReName(index, item)"
                                                        circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="文件更新" placement="bottom">
                                                <el-button v-if="item.permissionMap.update"
                                                        class="btnAdd" type="text" size="mini"
                                                        icon="iconfont icon-add-list"
                                                        @click="handleEditFile(index, item)"
                                                        circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="文件版本履历" placement="bottom">
                                                <el-button v-if="item.permissionMap.read"
                                                        class="btnEdit1" type="text" size="mini"
                                                        icon="iconfont icon-wenzhangpailie-"
                                                        @click="handleHistory(index, item)"
                                                        circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="文件删除" placement="bottom">
                                                <el-button v-if="item.permissionMap.delete"
                                                        class="btnDel" type="text" size="mini"
                                                        icon="el-icon-close"
                                                        @click="handleDeleteSubmit(index, item)"
                                                        circle></el-button>
                                            </el-tooltip>
                                        </div>
                                    </div>
                                    <div class="doc-item-file-content">
                                        <div style="padding: 5px">
                                            <p v-html='item.highLight'></p>
                                        </div>
                                        <div style="padding: 2px 0px 2px 40px">
                                            <p style="font-size: 12px;color: #969896">
                                                <span v-html="item.parentPathName" v-if="item.docType === 20" class="finger" @click="handleCollapse(item)"></span>
                                                <span v-html="item.parentPathName" v-if="item.docType === 10" @click="handleCollapse(item)"></span>
                                                <span style="padding-left: 30px; font-size: 12px;color: #969896">{{dateShortFormat(item.lastUpdateDate)}}</span>
                                                <span style="padding-left: 10px; font-size: 12px;color: #969896">{{item.size}}</span>
                                                <span style="padding-left: 10px; font-size: 12px;color: #969896">{{item.lastUpdateUserName}}</span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </template>
                            <div style="padding-left: 40px">
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
                                                class="btnAdd" type="text" size="mini" style="margin-left: 10px;margin-top: 8px"
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
                <span slot="no-more"></span>
            </infinite-loading>
            <!-- <el-table ref="table" v-loading="folderTable.loading" :data="folderTable.body" @selection-change="handleSelectionChange" highlight-current-row
                      :cell-style="{padding:'5px'}" class="custom-table-cell" border @header-dragend="colChange" @sort-change='sortChange'
                      :header-cell-style="{padding: '2px'}" :height="folderTable.height"
                      @row-click="rowClicked"
            >
                <el-table-column type="selection" width="50"></el-table-column>
                <el-table-column prop="name" label="文件名" :show-overflow-tooltip="true" sortable='custom'>
                    <template slot-scope="scope">
                        <div v-if="scope.row.kind === 'folder'">
                            <div v-if="scope.row.permissionMap.visible">
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <a href=" javascript:void(0) " @click="handleClickFolder(scope.$index, scope.row)"
                                   style="margin-left:10px;text-decoration-line:none;"><b>{{scope.row.name}}</b></a>
                            </div>
                            <div v-else>
                                <app-svg iconClass="wenjianjia3"></app-svg>
                                <span style="margin-left:10px;">{{scope.row.name}}</span>
                            </div>
                        </div>
                        <div v-else>
                            <div v-if="scope.row.permissionMap.read">
                                <app-svg :iconText="scope.row.suffix"></app-svg>
                                <a href=" javascript:void(0) " @click="handlePreview(scope.$index, scope.row)"
                                   style="margin-left:10px;text-decoration-line:none;" ><b>{{scope.row.name}}.{{scope.row.suffix}}</b></a>
                            </div>
                            <div v-else>
                                <app-svg :iconText="scope.row.suffix"></app-svg>
                                <span style="margin-left:10px;">{{scope.row.name}}.{{scope.row.suffix}}</span>
                            </div>
                        </div>
                    </template></el-table-column>
                <el-table-column label="版本说明" align="center" width="200px" :show-overflow-tooltip="true"><template slot-scope="scope">
                    <div v-if="scope.row.kind === 'folder'">
                    </div>
                    <div v-else>
                        <p>{{scope.row.comment}}</p>
                    </div>
                </template></el-table-column>
                <el-table-column prop="lastUpdateDate" label="最后修改时间" align="center" width="140px" sortable='custom'><template slot-scope="scope">
                    <div v-if="scope.row.kind === 'folder'">
                    </div>
                    <div v-else>
                        <el-tooltip class="item" effect="dark" placement="bottom">
                            <div slot="content">{{dateFormat(scope.row.lastUpdateDate)}}</div>
                            <p>{{dateShortFormat(scope.row.lastUpdateDate)}}</p>
                        </el-tooltip>
                    </div>
                </template></el-table-column>
                <el-table-column prop="size" label="文件大小" align="center" width="120px"><template slot-scope="scope">
                    <div v-if="scope.row.kind === 'folder'">
                    </div>
                    <div v-else>
                        <p>{{scope.row.size}}</p>
                    </div>
                </template></el-table-column>
                <el-table-column  prop="lastUpdateUserName" label="最后修改者" align="center" width="100px"  sortable='custom'>
                    <template slot-scope="scope">
                        <div v-if="scope.row.kind === 'folder'">
                        </div>
                        <div v-else>
                            <p>{{scope.row.lastUpdateUserName}}</p>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="210px">
                    <template slot-scope="scope">
                        <div v-if="scope.row.kind === 'folder'" style="float:right; margin-right: 25px;" >
                            <el-tooltip class="item" effect="dark" content="编辑文件夹" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.modify"
                                           class="btnEdit" type="text" size="mini"
                                           icon="iconfont icon-edit-node"
                                           @click="handleReName(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="删除文件夹" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.delete"
                                           class="btnDel" type="text" size="mini"
                                           icon="iconfont icon-trash"
                                           @click="handleDeleteSubmit(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                        </div>
                        <div v-else style="float:right; margin-right: 25px;">
                            <el-tooltip class="item" effect="dark" content="下载文件" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.download"
                                           class="btnAdd" type="text" size="mini"
                                           icon="iconfont icon-caozuo-shangchuan-download"
                                           @click="handleDownloadSubmit(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="编辑文件路径和名称" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.modify"
                                           class="btnEdit" type="text" size="mini"
                                           icon="iconfont icon-edit-file"
                                           @click="handleReName(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="文件更新" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.update"
                                           class="btnAdd" type="text" size="mini"
                                           icon="iconfont icon-add-list"
                                           @click="handleEditFile(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="文件版本履历" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.read"
                                           class="btnEdit" type="text" size="mini"
                                           icon="iconfont icon-wenzhangpailie-"
                                           @click="handleHistory(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="文件删除" placement="bottom">
                                <el-button v-if="scope.row.permissionMap.delete"
                                           class="btnDel" type="text" size="mini"
                                           icon="iconfont icon-trash"
                                           @click="handleDeleteSubmit(scope.$index, scope.row)"
                                           circle></el-button>
                            </el-tooltip>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="doc-table-expand">
                            <template v-for="(it, idx) in 10">
                                <el-form-item :key="idx" :label="'关键词'">
                                    <span>{{ props.row.name }}</span>
                                </el-form-item>
                            </template>
                        </el-form>
                    </template>
                </el-table-column>
                <infinite-loading ref="infinite" slot="append" @infinite="nextPage" force-use-infinite-wrapper=".el-table__body-wrapper">
                    <span slot="no-more"></span>
                </infinite-loading>
            </el-table> -->
        </el-main>
        <edit-folder ref="editFolder" :add-folder="addFolder"  :rename-folder="renameFolder" :delete-folder="deleteFolder" :delete-folders="deleteFolders"></edit-folder>
        <upload-file ref="uploadFile" :uploaded="refresh"></upload-file>
        <upload-folder ref="uploadFolder" :uploaded="refresh"></upload-folder>
        <upload-resource ref="uploadResource"></upload-resource>
        <link-file ref="linkFile" :linked="refresh"></link-file>
        <preview ref="preview"></preview>
        <preview-database ref="previewDatabase"></preview-database>
        <sort-folder ref="sortFolder"></sort-folder>
        <PasswordExceed ref="myPasswordExceed"></PasswordExceed>
        <Password ref="myPassword"></Password>
    </el-container>
</template>

<script>
    import {checkTime, formatDateTime} from '../../util/initData';
    import Utils from '../../util/utils';
    import PreviewDatabase from './PreviewDatabase';
    import UploadResource from './UploadResource';
    import editFolder from './EditFolder'
    import uploadFile from './UploadFile'
    import uploadFolder from "./UploadFolder";
    import linkFile from './LinkFile'
    import preview from './Preview'
    import sortFolder from './SortFolder'
    import PasswordExceed from './PasswordExceed'
    import {mapActions,mapState } from 'vuex'
    import Cookies from 'js-cookie'
    import Password from "../layout/Password";
    export default {
        components:{Password, editFolder, uploadFolder, uploadFile, linkFile, preview, sortFolder,PasswordExceed, UploadResource, PreviewDatabase},
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                folderClear: true,
                folderHeader:{
                    selfId:0,
                    selfFileCount:0,
                    selfFolderCount:0,
                    size:this.$maxFileSize,
                    item :[{
                        id : 0,
                        name : '',
                        data:null,
                    }],
                },
                folderTable:{
                    loading: false,
                    body: [],
                    height: 0,
                    pageSize: 100,
                    total: 0,
                    curPage: 0,
                    multipleSelection: [],
                    sort:{
                        prop:'',
                        order:'',
                    },
                },
                rankList:[{name:'创建时间',value:'createdate'},{name:'修改时间',value:'lastupdatedate'},{name:'作者姓名',value:'rankAuthor'},{name:'文献名称',value:'name'},{name:'文献来源',value:'rankSource'}],
                rankField:'createdate',
                sort:'DESC',
                searchTxt: '',
                isShowAll : false,
                isaa : false,
                activeCollapse: [],
            }
        },
        mounted () {
            let fruits = ["name", "lastUpdateDate","lastUpdateUserName"];
            this.tableColumnWidth()
            let applyTableColSort = JSON.parse(localStorage.getItem('applyTableColSort'))
            if (applyTableColSort){
                if (fruits.indexOf(applyTableColSort[0]) > -1){
                    this.folderTable.sort = {
                        prop : applyTableColSort[0],
                        order: applyTableColSort[1]
                    }
                }else{
                    localStorage.removeItem("applyTableColSort")
                }
            }
            if(Cookies.get('passwordExceed') === 'true'){
                this.$refs.myPasswordExceed.showPasswordExceedMsg(this)
                Cookies.set('passwordExceed',false)
            }
            Utils.$on('refreshHome', () => {
                this.refresh()
            });
            Utils.$on('search', (key) => {
                this.searchTxt = key;
                this.search();
            });
            Utils.$on('breadcrumbClick', (item) => {
                this.breadcrumbClick(item);
            });
        },
        computed: {
            selectFolder() {
                return this.$store.state.selectFolder
            }
        },
        watch:{
            selectFolder(){
                this.refresh()
            }
        },

        methods: {
            ...mapActions({
                preFolderAction: 'folderHistory/preFolder',
                nextFolderAction: 'folderHistory/nextFolder',
                addHis: 'folderHistory/addFolder',
                addHisById: 'folderHistory/addFolderById',
            }),
            //判断浏览器类型
            getBrowser(){
                var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
                var isOpera = userAgent.indexOf("Opera") > -1;
                if (isOpera) {
                    return "Opera"; //判断是否Opera浏览器
                }
                if (userAgent.indexOf("Firefox") > -1) {
                    return "FF"; //判断是否Firefox浏览器
                }
                if (userAgent.indexOf("Chrome") > -1){
                    return "Chrome"; //判断是否Chrome浏览器
                }
                if (userAgent.indexOf("Safari") > -1) {
                    return "Safari"; //判断是否Safari浏览器
                }
                if (userAgent.indexOf("Trident") > -1) {
                    return "Edge";//判断是否Edge浏览器
                }
                if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
                    return "IE"; //判断是否IE浏览器
                }
            },
            //表格高度计算
            tableHeight(){
                this.$nextTick(function () {
                    this.folderTable.height = window.innerHeight - this.$refs.table.$el.offsetTop - 60
                    let self = this;
                    window.onresize = function() {// 监听窗口大小变化
                        self.folderTable.height = window.innerHeight - self.$refs.table.$el.offsetTop - 60
                    }
                })
            },
            // 获取浏览器缓存的列宽
            tableColumnWidth () {
                this.$nextTick(function () {
                    let self = this
                    let tableWidth = localStorage.getItem('applyTableColWidths')
                    if (tableWidth && self.$refs.table != undefined) {
                        for (let i = 0; i < self.$refs.table.columns.length; i++) {
                            self.$refs.table.columns[i].width = JSON.parse(tableWidth)[i]
                        }
                    }
                })
            },
            /**
             * 拖动表头改变列宽
             */
            colChange (newWidth, oldWidth, column, event) {
                setTimeout(() => {
                    console.log(newWidth, oldWidth, column, event)
                    let applyTableColWidths = []
                    let applyTable = this.$refs.table
                    for (var i = 0; i < applyTable.columns.length; i++) {
                        applyTableColWidths.push(applyTable.columns[i].width)
                    }
                    console.log(applyTableColWidths)
                    localStorage.setItem('applyTableColWidths', JSON.stringify(applyTableColWidths))
                }, 100)
            },
            labelHead(h,{column,index}){
                let l = column.label.length
                let f = 16 //每个字大小，其实是每个字的比例值，大概会比字体大小差不多大一点，
                column.minWidth = f*l //字大小乘个数即长度 ,注意不要加px像素，这里minWidth只是一个比例值，不是真正的长度
                //然后将列标题放在一个div块中，注意块的宽度一定要100%，否则表格显示不完全
                return h('div',{class:'table-head',style:{width:'100%'}},[column.label])
            },
            sortChange(column, prop, order){
                console.log(column + '-' + column.prop + '-' + column.order)
                localStorage.removeItem("applyTableColSort")
                let applyTableColSort = []
                applyTableColSort.push(column.prop)
                applyTableColSort.push(column.order)
                localStorage.setItem('applyTableColSort', JSON.stringify(applyTableColSort))
                this.folderTable.sort = {
                    prop : column.prop,
                    order: column.order
                }

                this.refresh()

            },
            // 获取header数据
            getHeader($state){
                if (!this.selectFolder) return
                if (this.folderTable.curPage !== 1) return

                this.$axios({
                    url: `/user/folder/${this.selectFolder.id}`,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        let fullName = res.ctt.fullName.split('/');
                        let fullPath = res.ctt.fullPath.split('/');
                        let item = []
                        for (let i = 0;i<fullPath.length;i++){
                            let data = {
                                id :  parseInt(fullPath[i]),
                                name : fullName[i],
                            }
                            item.push(data);
                        }
                        this.folderHeader.selfId = res.ctt.id
                        this.folderHeader.selfFileCount = res.ctt.files.length
                        this.folderHeader.selfFolderCount = res.ctt.children.length
                        this.folderHeader.size = res.ctt.fileSize;
                        this.folderHeader.item = item
                    }else{
                        this.folderHeader.name.push('取得文件夹信息失败')
                    }
                    Utils.$emit('setFolderHeader',this.folderHeader);
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            }
            ,
            rank(){
                if(this.sort === "ASC"){
                    this.sort = "DESC";
                }else{
                    this.sort = "ASC"
                }
                this.refresh();
            },
            // 获取table数据
            getData($state) {
                if (!this.selectFolder) return

                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.1)'
                });

                this.$axios({
                    url: `/user/folder/${this.selectFolder.id}/docs/${this.folderTable.curPage}-${this.folderTable.pageSize}?column=${this.folderTable.sort.prop}&&order=${this.folderTable.sort.order}&rankField=${this.rankField}&sort=${this.sort}`,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        let permissionBody = [];
                        if (this.selectFolder.permissionMap && !this.selectFolder.permissionMap.visible) {
                            for( let i = 0; i < res.ctt.list.length; i++){
                                if (this.isShowAll === false && res.ctt.list[i].disabled === true ) continue
                                if (res.ctt.list[i].permissionMap.visible){
                                    permissionBody.push(res.ctt.list[i])
                                }
                            }
                        }else {
                            for( let i = 0; i < res.ctt.list.length; i++){
                                if (this.isShowAll === false && res.ctt.list[i].disabled === true ) continue
                                if (res.ctt.list[i].permissionMap.visible){
                                    permissionBody.push(res.ctt.list[i])
                                }
                            }
                        }
                        permissionBody.forEach(c => {
                            if (c.disabled === null) c.disabled = false
                        })
                        this.folderTable.body = this.folderTable.body.concat(permissionBody)
                        this.folderTable.body = this.folderTable.body.map((item) => {
                            let expandData = [];
                            let hasDatabase = false;
                            let appendFiles = [];
                            let dbId = null;
                            let dbName = '';
                            let checked = item.checked || false;
                            if (item.fileAccessoryDto) {
                                appendFiles = item.fileAccessoryDto.filter((i) => i.type !== 2);
                                // console.log(appendFiles);
                                let isDb = item.fileAccessoryDto.find((i) => i.type === 2);
                                if (isDb) {
                                    hasDatabase = true;
                                    dbId = isDb.saveId;
                                    dbName = isDb.name;
                                }
                            }
                            if (item.fileMetadataDto) {
                                expandData = item.fileMetadataDto.map((i) => {
                                    let content = i.value;
                                    if (i.dataType === 7 && i.value) {
                                        content = formatDateTime(i.value, '年月日');
                                        // let date = new Date(i.value);
                                        // content = `${date.getFullYear()}年${checkTime(date.getMonth() + 1)}月${checkTime(date.getDate())}日11`;
                                    }
                                    if (i.dataType === 6) {
                                        content = i.value === 'true' ? '是' : '否';
                                    }
                                    return {
                                        title: i.name,
                                        content,
                                    };
                                });
                            }
                            return {
                                ...item,
                                expandData,
                                hasDatabase,
                                dbId,
                                appendFiles,
                                checked,
                                dbName,
                            };
                        });
                        // console.log(this.folderTable.body);
                        this.folderTable.total = res.ctt.total
                        this.folderTable.loading = false
                        if (res.ctt.list.length === 0){
                            if($state) $state.complete()
                        }
                        else{
                            if($state) $state.loaded()
                        }

                        this.$nextTick(() => {
                            // this.tableHeight()
                        })
                        if (this.isaa === false) {
                            this.isaa = true;
                            // this.$nextTick(() => {
                            //     this.$refs.table.clearSort()
                            //     for (let i of this.$refs.table.columns) {
                            //         if (i.property === this.folderTable.sort.prop) {
                            //             i.order = this.folderTable.sort.order
                            //         } else {
                            //             i.order = ''
                            //         }
                            //     }
                            // })

                        }
                        loading.close();
                    }else{
                        this.$message.error(res.msg)
                        this.folderTable.loading = false
                        loading.close();
                        // this.refresh();
                    }
                }).catch(err => {
                    console.log(err)
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    loading.close();
                })
            },
            //画面刷新
            refresh(){
                this.folderTable.total = 0
                this.folderTable.body = []
                this.folderTable.curPage = 0
                this.$refs.infinite.stateChanger.reset()
            },
            //画面表格多选赋值操作
            handleSelectionChange(val) {
                this.folderTable.multipleSelection = val;
            },
            //日期格式化
            dateShortFormat(data) {
                if (!data) return ""
                return this.$moment(data).format("MM月DD日 HH:mm")
            },
            //日期格式化
            dateFormat(data) {
                if (!data) return ""
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm")
            },
            //点击文件夹
            handleClickFolder(index, row){
                this.$router.push('/home')
                this.addHis(row)
                this.$store.state.selectFolder = row
                this.$store.commit("setSelectFolder", row)
            },
            //新建文件夹界面
            handleAddFolder() {
                this.$refs.editFolder.handleAddFolder(this.selectFolder)
            },
            //点击上传菜单
            handleCommand(command){
                switch (command) {
                    case 'fileOpen':
                        this.handleUploadFile()
                        break
                    case 'folderOpen':
                        this.handleUploadFolder()
                        break
                    case 'open':
                        this.handleUploadResource('create')
                }
            },

            //显示重命名界面
            handleReName(index, row) {
                //this.$refs.editFolder.handleReName(row)
                this.$refs.editFolder.handleEditFolder(this.selectFolder,row)
            },
            //删除
            handleDeleteSubmit: function (index, row) {
                this.$refs.editFolder.handleDeleteSubmit(row)
            },
            addFolder(res) {
                this.refresh()
                this.$emit('lay_addFolder', res)
            },
            renameFolder(para) {
                let target;
                this.folderTable.body.forEach(c =>{
                    if (c.id === para.id && c.kind === para.kind){
                        target = c
                    }
                })
                if(target){
                    target.name = para.name
                    this.refresh()
                }
                if (para.kind === 'folder')
                    this.$emit('lay_putFolder', para)
            },
            deleteFolder(para) {
                this.refresh()
                if (para.kind === 'folder')
                    this.$emit('lay_delFolder', para)
            },
            deleteFolders(para) {
                this.refresh()
                for( let i = 0; i < para.length; i++) {
                    if (para[i].kind === 'folder')
                        this.$emit('lay_delFolder', para[i])
                }
            },
            //文件夹上传画面
            handleUploadFolder: function () {
                this.$refs.uploadFolder.handleUploadFolder(this.selectFolder,this.folderHeader.size)
            },
            //文件上传画面
            handleUploadFile: function () {
                this.$refs.uploadFile.handleUploadFile(this.selectFolder,this.folderTable.body,this.folderHeader.size)
            },
            //文件更新画面
            handleEditFile: function (index, row) {
                if (row.docType === 20) {
                    this.$refs['uploadResource'].types = 'edit';
                    this.$refs['uploadResource'].editData = row;
                    this.$refs['uploadResource'].folderId = this.selectFolder.id;
                    this.$refs['uploadResource'].visible = true;
                } else {
                    this.$refs.uploadFile.handleEditFile(row, this.selectFolder);
                }
            },
            //下载
            handleDownloadSubmit: function (index, row) {
                this.$refs.uploadFile.handleDownloadSubmit(row)
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
            //文件外链界面
            handleAddLink: function () {
                const selectedItems = this.folderTable.body.filter((i) => i.checked);
                this.$refs.linkFile.handleAddLink(selectedItems);
                // this.$refs.linkFile.handleAddLink(this.folderTable.multipleSelection)
            },
            //文件删除界面
            handleMultiDeleteSubmit: function () {
                const selectedItems = this.folderTable.body.filter((i) => i.checked);
                // this.$refs.editFolder.handleMultiDeleteSubmit(this.folderTable.multipleSelection)
                this.$refs.editFolder.handleMultiDeleteSubmit(selectedItems);
            },
            //查看文件履历
            handleHistory:function(index, row){
                this.$refs.preview.handleHistory(row)
            },
            //预览界面
            handlePreview:function(index, row) {
                if (row.kind === 'file') {
                    return;
                }
                this.$refs.preview.handlePreview(row)
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
                this.$router.push({path :'/search',query:{searchKey: this.searchTxt}})
            },
            nextPage($state){

                this.folderTable.curPage += 1
                this.getData($state)
                this.getHeader($state)
            },
            showAllChange(){
                this.refresh()
            },
            breadcrumbClick(data){
                if (this.selectFolder.id === data.id) return;
                this.addHisById(data.id);
            },
            updatePassword(){
                this.$refs.myPassword.handlePassword();
            },
            rowClicked(row) {
                // console.log(row);
                if (row.kind === 'folder') {
                    return;
                }
                this.$refs.table.toggleRowExpansion(row);
            },
            handleClickFile(item) {
                if (item.docType === 20) {
                    this.handleCollapse(item);
                } else {
                    this.$refs.preview.handlePreview(item);
                }
            },
            handlePreviewAppendFile(item, nextId) {
                let itemNext = {
                    ...item,
                    nextId,
                };
                this.$refs.preview.handlePreview(itemNext);
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
            handleUploadResource(type) {
                // console.log(this.selectFolder);
                this.$refs['uploadResource'].visible = true;
                this.$refs['uploadResource'].types = type;
                this.$refs['uploadResource'].folderId = this.selectFolder.id;
                this.$refs['uploadResource'].upload.size = this.folderHeader.size;
            },
            handleOpenReviewDB(item) {
                this.$refs['previewDatabase'].dbId = item.dbId;
                this.$refs['previewDatabase'].visible = true;
            },
        }
    }
</script>

<style lang="scss">
    .el-table th{
        font-size:14px!important;
    }
    /*.el-table--enable-row-transition .el-table__body td{*/
    /*border-right:0px;*/
    /*border-color: white;*/
    /*}*/
    .el-table--border th, .el-table--border td{
        border-right:1px solid white;
    }
    .btn-custom-cancel{
        float: right;
        margin-left: 10px;
    }
    .doc-table-expand {
        font-size: 0;
    }
    .doc-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .doc-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
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
        .btnEdit1{
            color: #e6a23c;
        }
        .btnEdit2{
            color: #75A1E1;
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
                cursor: default;
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

            .doc-item-file-title-main {
                display: flex;
                align-items: center;
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
    .resource-file-list {
        display: flex;
        align-items: center;
        margin-top: 10px;
    }
    .searchBox{
        width: 100%;
    }
    .searchBox .input-with-select{
        width: 100%;
        height: 36px;
    }
    .searchBox .el-input__inner{
        background-color: #EFF0F1;
        color: black;
        border-bottom-right-radius:0;
        border-top-right-radius:0;
        border-bottom-left-radius:34px;
        border-top-left-radius:34px;
        height: 36px;
        width: 100%;
    }
    .buttonBox{
        margin-right: 30px;
    }
    .buttonBox .el-button{
        background-color: white;
        color: black;
        font-weight: 600;
        border:1px solid #0099FF;
    }
    .buttonBox .el-button:hover{
        background-color: white;
        color:#409EFF;
    }

</style>


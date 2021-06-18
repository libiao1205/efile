<template>
<div>
    <div style="display: none;">
        <div>
            <img id="image" :src="imagePreview.src" alt="Picture">
        </div>
    </div>
    <!--文件履历界面-->
    <el-dialog :title="formHistory.title" :visible.sync="formHistory.visible" :close-on-click-modal="true" width="800px">
        <el-form :model="formHistory.data" label-width="200px" ref="formHistoryData">
            <P style=" line-height: 50px; color:#000000; font-size:16px">{{formHistory.data.name}}</P>
        </el-form>
        <el-table
            class="custom-table-cell"
            :data="formHistory.data.list"
            style="width: 100%">
            <el-table-column prop="seq" label="版本" width="60">
            </el-table-column>
            <el-table-column prop="username" label="最后更新者" width="120">
            </el-table-column>
            <el-table-column :formatter="historyDateFormat" prop="updateDate" label="最后更新日期" width="180"  align="center">
            </el-table-column>
            <el-table-column prop="size" label="文件大小" width="100" align="center">
            </el-table-column>
            <el-table-column prop="comment" label="版本描述" align="center">
                <template slot-scope="scope">
                    <el-tooltip class="item" effect="dark" placement="bottom">
                        <div slot="content">{{scope.row.comment}}</div>
                        <div>{{historyCommentFormat(scope.row)}}
                        </div>
                    </el-tooltip>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center" >
                <template slot-scope="scope">
                    <el-tooltip class="item" effect="dark" content="预览" placement="bottom" v-if="scope.row.suffix">
                        <el-button class="btnAdd" type="text" size="mini" icon="el-icon-view" @click="handleHistoryPreview(scope.row)" circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="下载" placement="bottom" v-if="scope.row.suffix">
                        <el-button class="btnAdd" type="text" size="mini" icon="el-icon-download" @click="handleDownloadSubmit(scope.row)" circle></el-button>
                    </el-tooltip>
                    <el-tooltip class="item" effect="dark" content="预览" placement="bottom" v-if="!scope.row.suffix">
                        <el-button class="btnAdd" type="text" size="mini" icon="el-icon-view" @click="handleLiteraturePreview(scope.row)" circle></el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="formActionHistory.title" :visible.sync="formActionHistory.visible" append-to-body width="500px">
            <el-table
                class="custom-table-cell"
                :data="formActionHistory.data.list"
                style="width: 100%" height="400px">
                <el-table-column prop="seq" label="序号" width="50" align="center">
                </el-table-column>
                <el-table-column prop="userName" label="操作者" width="120" align="center">
                </el-table-column>
                <el-table-column :formatter="historyDateFormat" prop="updateDate" label="操作日期" width="180"  align="center">
                </el-table-column>
                <el-table-column prop="actionName" label="操作行为" width="110" align="center">
                </el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button @click = "formActionHistory.visible = false">关闭</el-button>
            </div>
        </el-dialog>

        <div slot="footer" class="dialog-footer">
            <el-button @click = "handleActionHistory">查看和下载履历</el-button>
            <el-button @click = "formHistory.visible = false">关闭</el-button>
        </div>
    </el-dialog>
    <!--文件履历界面-->

    <!--文献元数据履历界面-->
    <el-dialog title="文献数据"
               :fullscreen="isFullScreen"
               v-dialogDrag
               :visible.sync="formLiteratureHistory.visible"
               :show-close="false"
               :close-on-click-modal="false"
               width="1200px"
               top="10px">
        <div slot="title" class="custom-title-container">
            <span></span>
            <span class="custom-title">文献数据</span>
            <span class="custom-icons">
                    <i v-if="isFullScreen" @click="handleChangeMaxMinDialog">
                        <svg t="1614587768535" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5438" width="200" height="200">
                            <path d="M449.92 543.36C449.92 520.96 431.36 512 410.24 512L97.92 512c-17.92 0-32 14.08-32 32 0 17.92 14.08 32 32 32l242.56 0-267.52 267.52c-12.16 12.16-12.16 31.36 0 43.52 12.16 12.16 31.36 12.16 43.52 0l269.44-269.44 0 246.4c0 17.92 14.08 32 32 32 17.92 0 32-14.08 32-32l0-320M865.92 384 622.72 384l267.52-267.52c12.16-12.16 12.16-31.36 0-43.52-12.16-12.16-31.36-12.16-43.52 0L577.92 342.4 577.92 96C577.92 78.08 563.2 64 545.92 64c-17.92 0-32 14.08-32 32l0 320c0 0.64 0.64 1.28 0.64 1.92 0 8.32 2.56 16.64 8.96 22.4C531.2 448.64 542.72 451.2 552.96 448l312.32 0c17.92 0 32-14.08 32-32C897.92 398.08 883.2 384 865.92 384z" p-id="5439" fill="#ffffff"></path>
                        </svg>
                    </i>
                    <i v-else @click="handleChangeMaxMinDialog">
                        <svg t="1614587805849" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5979" width="200" height="200">
                            <path d="M444.3 539.9L202 782.2 199.8 563c0-16.5-13.5-30-30-30s-30 13.5-30 30l2.2 285.1c0 8.8 3.8 16.7 9.8 22.2 5.5 6 13.4 9.8 22.2 9.8h295.6c16.5 0 30-13.5 30-30s-13.5-30-30-30H248.9l237.8-237.8c11.7-11.7 11.7-30.8 0-42.4-11.6-11.6-30.7-11.6-42.4 0zM578.1 488l242.3-242.3 2.2 219.2c0 16.5 13.5 30 30 30s30-13.5 30-30l-2.2-285.1c0-8.8-3.8-16.7-9.8-22.2-5.5-6-13.4-9.8-22.2-9.8H552.8c-16.5 0-30 13.5-30 30s13.5 30 30 30h220.7L535.7 445.6c-11.7 11.7-11.7 30.8 0 42.4 11.7 11.7 30.8 11.7 42.4 0z" p-id="5980" fill="#ffffff"></path>
                        </svg>
                    </i>
                <!-- <i class="el-icon-close" v-if="isFullScreen" @click="handleChangeMaxMinDialog"></i>
                <i class="el-icon-close" v-else @click="handleChangeMaxMinDialog"></i> -->
                    <i class="el-icon-close" @click="handleCloseDialog"></i>
                </span>
        </div>
        <div class="fxl" style="width: 100%;">
            <div class="fxl" style="width: 50%">
                <el-form inline class="preview-item-content-expand" style="width: 60%">
                    <template v-for="(it, idx) in this.formLiteratureHistory.data.fileMetadataHistoryList">
                        <el-form-item :key="idx" :label="it.name">
                            <span>{{ it.valueData }}</span>
                        </el-form-item>
                    </template>
                </el-form>
                <div style="position: relative;bottom: 14px;width: 38%">
                    <div v-for="(it, idx) in this.formLiteratureHistory.data.fileAccessoryHistoryList" :key="idx" class="resource-file-list">
                        <div @click="handleLiteratureAccessory(it)" class="word-hidden finger" style="color: #409EFF;font-size: 14px;">
                            <app-svg :iconText="it.suffix"></app-svg>
                            <el-tooltip effect="dark" :content="it.name+'.'+it.suffix" placement="bottom">
                                <span>{{it.name}}.{{it.suffix}}</span>
                            </el-tooltip>
                        </div>
                        <el-tooltip class="item" effect="dark" content="下载文件" placement="bottom">
                            <el-button class="btnAdd" type="text" size="medium" style="font-size: 20px"
                                       icon="iconfont icon-caozuo-shangchuan-download"
                                       @click="handleDownloadResource(it)"
                                       circle></el-button>
                        </el-tooltip>
                    </div>
                    <div v-if="this.formLiteratureHistory.data.dbName" class="resource-file-list">
                        <div @click="selectReviewDB" class="word-hidden finger" style="color: #409EFF;font-size: 14px;" >
                            <svg t="1614846486792" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1852" width="1.2rem" height="1.2rem">
                                <path d="M858.2 766.3V616.7c-22.1 19.3-47 35.2-73.7 47.2-86.5 38-180 57.2-274.5 56.3-122 7.1-242.8-27-343.1-96.7v150.7C173.7 840 318.8 912 512.3 912c193.5 0 337.5-72.6 345.9-138.9v-6.8z" p-id="1853" fill="#0072D9"></path><path d="M858.2 507v-2.8 6.8c-8.4 66.4-152.4 138.9-345.9 138.9-193.5 0-338.6-72-345.4-137.8V361.3C267.2 431.1 388 465.1 510 458c94.5 0.9 188-18.2 274.5-56.3 26.7-12.1 51.6-28 73.7-47.2V507z" p-id="1854" fill="#0072D9"></path><path d="M762.6 160.5c57.4 25.3 91.7 61.3 91.7 93.4 0 31.5-35.4 66.4-91.7 93.4-80 34-166.2 50.7-253.1 48.9-196.9 0-343.7-74.8-343.7-141.7s147.4-142.3 344.8-142.3c86.5-1.9 172.3 14.6 252 48.3z" p-id="1855" fill="#0072D9"></path>
                            </svg>
                            <el-tooltip effect="dark" :content="this.formLiteratureHistory.data.dbName" placement="bottom">
                                <span>{{this.formLiteratureHistory.data.dbName}}</span>
                            </el-tooltip>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 50%">
                <app-frame :height="'calc('+appFrameHeight+' - 60px)'" ref="appFrameLiterature" :suffix="formPreview.data.suffix" :file-url="formPreview.data.url"></app-frame>
            </div>
        </div>
    </el-dialog>
    <!--文献元数据履历界面-->

    <!--预览界面-->
    <el-dialog
        :fullscreen="isFullScreen"
        :show-close="false"
        v-dialogDrag
        :title="formPreview.title + ':' + formPreview.data.name"
        :visible.sync="formPreview.visible"
        @opened="formPreviewOpened"
        :close-on-click-modal="false"
        top="10px"
        custom-class="custom-dialog"
    >
        <div slot="title" class="custom-title-container">
            <span></span>
            <span class="custom-title word-hidden">
                <el-tooltip :content="formPreview.data.name" placement="bottom">
                    <span>{{formPreview.title + ':' + formPreview.data.name}}</span>
                </el-tooltip>
            </span>
            <span class="custom-icons">
                <i v-if="isFullScreen" @click="handleChangeMaxMinDialog">
                    <svg t="1614587768535" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5438" width="200" height="200">
                        <path d="M449.92 543.36C449.92 520.96 431.36 512 410.24 512L97.92 512c-17.92 0-32 14.08-32 32 0 17.92 14.08 32 32 32l242.56 0-267.52 267.52c-12.16 12.16-12.16 31.36 0 43.52 12.16 12.16 31.36 12.16 43.52 0l269.44-269.44 0 246.4c0 17.92 14.08 32 32 32 17.92 0 32-14.08 32-32l0-320M865.92 384 622.72 384l267.52-267.52c12.16-12.16 12.16-31.36 0-43.52-12.16-12.16-31.36-12.16-43.52 0L577.92 342.4 577.92 96C577.92 78.08 563.2 64 545.92 64c-17.92 0-32 14.08-32 32l0 320c0 0.64 0.64 1.28 0.64 1.92 0 8.32 2.56 16.64 8.96 22.4C531.2 448.64 542.72 451.2 552.96 448l312.32 0c17.92 0 32-14.08 32-32C897.92 398.08 883.2 384 865.92 384z" p-id="5439" fill="#ffffff"></path>
                    </svg>
                </i>
                <i v-else @click="handleChangeMaxMinDialog">
                    <svg t="1614587805849" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5979" width="200" height="200">
                        <path d="M444.3 539.9L202 782.2 199.8 563c0-16.5-13.5-30-30-30s-30 13.5-30 30l2.2 285.1c0 8.8 3.8 16.7 9.8 22.2 5.5 6 13.4 9.8 22.2 9.8h295.6c16.5 0 30-13.5 30-30s-13.5-30-30-30H248.9l237.8-237.8c11.7-11.7 11.7-30.8 0-42.4-11.6-11.6-30.7-11.6-42.4 0zM578.1 488l242.3-242.3 2.2 219.2c0 16.5 13.5 30 30 30s30-13.5 30-30l-2.2-285.1c0-8.8-3.8-16.7-9.8-22.2-5.5-6-13.4-9.8-22.2-9.8H552.8c-16.5 0-30 13.5-30 30s13.5 30 30 30h220.7L535.7 445.6c-11.7 11.7-11.7 30.8 0 42.4 11.7 11.7 30.8 11.7 42.4 0z" p-id="5980" fill="#ffffff"></path>
                    </svg>
                </i>
                <!-- <i class="el-icon-close" v-if="isFullScreen" @click="handleChangeMaxMinDialog"></i>
                <i class="el-icon-close" v-else @click="handleChangeMaxMinDialog"></i> -->
                <i class="el-icon-close" @click="handleCloseDialog"></i>
            </span>
        </div>
        <el-form :model="formPreview.data" ref="formPreviewData" style="height: 100%;">
            <app-frame :height="appFrameHeight+''" ref="appFrame" :suffix="formPreview.data.suffix" :file-url="formPreview.data.url"></app-frame>
        </el-form>
        <!--<div slot="footer" class="dialog-footer">-->
        <!--<el-button @click.native="formPreview.visible = false">关闭</el-button>-->
        <!--</div>-->
    </el-dialog>
    <!--预览界面-->

    <preview-database ref="previewDatabase"></preview-database>
</div>
</template>

<script>
    import AppFrame from "../../components/AppFrame/index";
    import Viewer from 'viewerjs'
    import 'viewerjs/dist/viewer.css'
    import {formatDateTime} from "../../util/initData";
    import PreviewDatabase from '../doc/PreviewDatabase';
    export default {
        name: "Preview",
        components: {AppFrame,PreviewDatabase},
        data(){
            return{
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                appFrameHeight: '85vh',
                /*pdf路径*/
                pdf:{
                    url:this.$pdf,
                },
                /*pdf路径*/

                /*预览数据处理*/

                /*预览数据处理*/
                formPreview: {
                    title:'预览',
                    data:{
                        id: 0,
                        name: '',
                        suffix: '',
                        seq: '',
                        size: '',
                        comment: '',
                        url: '',
                        pdfDoc: null,
                        canvasData: [],
                    },
                    visible:false,
                },
                /*预览数据处理*/
                /*文件履历 Start*/
                formHistory:{
                    title:'文件版本履历',
                    data:{
                        id:0,
                        name:'',
                        list:[]
                    },
                    visible:false,
                },
                /*文件履历 End*/
                /*文献元数据履历 Start*/
                formLiteratureHistory:{
                    title:'文件版本履历',
                    data:{
                        id:0,
                        name:'',
                        fileMetadataHistoryList:[],
                        fileAccessoryHistoryList:[],
                        dbId: 0,
                        dbName:'',
                    },
                    visible:false,
                },
                /*文献元数据履历 End*/
                formActionHistory:{
                    title:'文件查看和下载履历',
                    data:{
                        id:0,
                        name:'',
                        list:[]
                    },
                    visible:false,
                },
                imagePreview: {
                    src:'',
                    viewer:''
                },
                isFullScreen: false,
                dialogHeight:0
            }
        },
        mounted() {
            this.imagePreview.viewer = new Viewer(document.getElementById('image'), {
                toolbar: {
                    zoomIn: 4,
                    zoomOut: 4,
                    oneToOne: 4,
                    reset: 4,
                    rotateLeft: 4,
                    rotateRight: 4,
                    flipHorizontal: 4,
                    flipVertical: 4,
                },
            });
        },
        methods:{
            //预览界面
            handlePreview:function(row) {
                let id = 0;
                if(row.resourceId > 0){
                    id = row.resourceId;
                }else{
                    id = row.id
                }
                let url = `/user/file/${id}/seq/0/preview`;
                if (row.nextId) {
                    url = `/user/file/${id}/accessory/${row.nextId}/seq/0/preview`;
                }
                this.$axios({
                    url,
                    method: 'get',
                }).then((res) => {
                    if(res.c === 0){
                        if(res.ctt.permissionMap.visible === false || res.ctt.permissionMap.read === false){
                            this.$message.error(`您没有查看文件的权限！请联系文件夹管理员！`)
                            return
                        }
                        this.formPreview.data = {
                            id: res.ctt.id,
                            name:res.ctt.name + '.' + res.ctt.suffix,
                            suffix: res.ctt.suffix,
                            seq:0,
                            comment: res.ctt.comment,
                            size: res.ctt.size,
                            url: res.ctt.url.url,
                        }
                        // let url = `http://192.168.1.7:8080${res.ctt.url.url.split('http://localhost:8080')[1]}`;
                        // this.formPreview.data.url = url;
                        this.showPreview();
                    }else if(res.c === 6001){
                        this.$message.warning('文件已被删除，不能查看！')
                    }
                })
            },
            //预览文献附件
            handleLiteratureAccessory(row){
                this.$axios({
                    url: `/user/file/${row.id}/seq/${row.id}/Literature/preview`,
                    method: 'get',
                }).then((res) => {
                    this.formPreview.data = {
                        suffix: res.ctt.suffix,
                        url: res.ctt.url.url,
                        name:res.ctt.name + '.' + res.ctt.suffix,
                    }
                    if(this.$refs.appFrameLiterature){
                        this.$refs.appFrameLiterature.loadFile(this.formPreview.data.suffix, this.formPreview.data.url)
                    }
                    }
                )
            },
            //预览元数据界面
            handleLiteraturePreview:function(row) {
                this.$axios({
                    url: `/user/file/${row.fileId}/seq/${row.seq}`,
                    method: 'get',
                }).then((res) => {
                    if(res.ctt.fileMetadataHistoryDto === null ){
                        this.$message.warning('该数据为旧版系统数据，不能查看！')
                    }
                    this.formLiteratureHistory.data.fileAccessoryHistoryList = res.ctt.fileAccessoryHistoryDto.filter((i) => i.type !== 2);
                    this.formLiteratureHistory.data.fileMetadataHistoryList = res.ctt.fileMetadataHistoryDto;

                    let isDb = res.ctt.fileAccessoryHistoryDto.filter((i) => i.type === 2);
                    if (isDb) {
                        this.formLiteratureHistory.data.dbId = isDb[0].saveId;
                        this.formLiteratureHistory.data.dbName = isDb[0].name;
                    }

                    this.formLiteratureHistory.data.fileMetadataHistoryList.forEach(dto=>{
                        if(dto.dataType === 7 && dto.value != ''){
                            dto.valueData = formatDateTime(dto.value, '年月日');
                        }else{
                            dto.valueData = dto.value;
                        }
                    })
                    this.formLiteratureHistory.visible = true;
                 if(res.c === 6001){
                    this.$message.warning('文件已被删除，不能查看！')
                 }
                })
            },
            //查看数据库
            selectReviewDB() {
                if(this.$refs.appFrame) this.$refs.appFrame.isShow = false;
                this.$refs['previewDatabase'].dbId = this.formLiteratureHistory.data.dbId;
                this.$refs['previewDatabase'].dbName = this.formLiteratureHistory.data.dbName;
                this.$refs['previewDatabase'].visible = true;
            },
            showPreview(){
                // console.log(this.formPreview.data.url);
                switch (this.formPreview.data.suffix.toLowerCase()) {
                    case 'jpg':
                    case 'jpeg':
                    case 'png':
                    case 'bmp':
                    case 'gif':
                      this.imagePreview.src = this.formPreview.data.url
                      // this.imagePreview.viewer.update()
                      this.imagePreview.viewer.show()
                        break;
                    default:
                        this.formPreview.visible = true
                        if(this.$refs.appFrame) this.$refs.appFrame.loadFile(this.formPreview.data.suffix, this.formPreview.data.url)
                }
            },
            //历史版本预览界面
            handleHistoryPreview:function(row) {
                this.$axios({
                    url: `/user/file/${row.fileId}/seq/${row.seq}/preview`,
                    method: 'get',
                }).then((res) => {
                    this.formPreview.data = {
                        id: res.ctt.id,
                        name:res.ctt.name + '.' + res.ctt.suffix,
                        suffix: res.ctt.suffix,
                        seq:0,
                        comment: res.ctt.comment,
                        size: res.ctt.size,
                        url: res.ctt.url.url,
                    }
                    this.formPreview.visible = true
                    if(this.$refs.appFrame) this.$refs.appFrame.loadFile(this.formPreview.data.suffix, this.formPreview.data.url)
                    }
                )
            },
            //下载
            handleDownloadSubmit(row){
                this.$confirm('开始下载吗？', '提示', {
                    cancelButtonClass: 'btn-custom-cancel',
                    type: 'success'
                }).then(() => {
                    this.$axios({
                        url: `user/file/${row.fileId}/seq/${row.seq}/download`,
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
                        if (res.status === 9){
                            this.$message({
                                message: '文件已经删除，不能下载',
                                type: 'error'
                            });
                            return
                        }
                        window.location.href = `${this.$upload}/user/file/download/${row.fileId}/${res.guid}`;

                        // const content = res
                        // const blob = new Blob([content])
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
            //下载文献中的附件
            handleDownloadResource(item) {
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
            //查看文件履历
            handleHistory:function(row){
                this.$axios({
                    url: `/user/file/${row.id}`,
                    method: 'get',
                }).then((res) => {
                    this.formHistory.title = '文件版本履历'
                    this.formHistory.data = {
                        id: res.ctt.id,
                        name : res.ctt.suffix  ? (res.ctt.name + '.' + res.ctt.suffix) : res.ctt.name,
                        list:res.ctt.fileHistoryDto,
                    }
                    console.log( this.formHistory.data)
                    this.formHistory.visible = true
                })

            },
            handleActionHistory:function(){
                this.$axios({
                    url: `/user/file/${this.formHistory.data.id}/actionhistory`,
                    method: 'get',
                }).then((res) => {
                    this.formActionHistory.visible = true
                    this.formActionHistory.data = {
                        id: this.formHistory.data.id,
                        name:this.formHistory.data.name,
                        list:res.ctt,
                        }
                    }
                )
            },
            //履历日期格式化
            historyDateFormat(row){
                let data = row.updateDate;
                return this.dateFormat(data);
            },
            //日期格式化
            dateFormat(data) {
                if (!data) return ""
                return this.$moment(data).format("YYYY年MM月DD日 HH:mm")
            },
            //履历描述格式化
            historyCommentFormat(row){
                let data = row.comment == null ? '' : row.comment;
                if (data.length > 5){
                    return data.substr(0,5) + '...';
                }
                return data;
            },
            formPreviewOpened(){
                if(!this.$refs.appFrame.loaded) this.$refs.appFrame.loadFile(this.formPreview.data.suffix, this.formPreview.data.url)
            },
            handleCloseDialog() {
                this.formPreview.visible = false;
                this.formLiteratureHistory.visible = false;
                this.isFullScreen = false;
            },
            handleChangeMaxMinDialog() {
                this.isFullScreen = !this.isFullScreen;
                if (this.isFullScreen) {
                    this.appFrameHeight = 'calc(100vh - 60px)';
                } else {
                    this.appFrameHeight = '85vh';
                }
                // this.imagePreview.viewer.update();
            },
        }
    }
</script>

<style scoped>
    .custom-title-container {
        display: flex;
        flex-direction: row;
    }
    .custom-title {
        color: white;
        line-height: 24px;
        font-size: 18px;
        font-weight: bold;
    }
    .custom-icons {
        color: white;
        text-align: right;
    }
    .custom-icons i {
        cursor: pointer;
    }
</style>

<style lang="scss">
    .preview-item-content-expand {
        font-size: 0;
        .el-form-item {
            display: flex;
            margin-right: 0;
            margin-bottom: 0;
            label {
                width: 70px;
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
    .btnAdd{
        color: #67c23a;
    }
    .custom-dialog {
        .el-dialog__body {
            padding: 5px;
            // height: calc(100vh - 60px);
        }
    }
    .custom-title-container {
        span:first-child, span:last-child {
            width: 40px;
        }
        span:nth-child(2) {
            flex: 1;
        }
    }
</style>

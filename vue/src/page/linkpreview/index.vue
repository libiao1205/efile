<template>
    <el-container>
        <el-header class="apphead1 fxb bb1 pr0">
            <div>
                <h1 class="fb text1em"
                    style="color: white;font-weight: bold; font-style: italic;letter-spacing: 3px; padding-left: 20px;">
                    <svg class="icon" aria-hidden="true">
                        <use xlink:href="#icon-as-wendangku"></use>
                    </svg>
                    外链预览
                </h1>
            </div>
            <div class="top-menu-group fxc" style="background-color: #f8f8f8">
                <el-dropdown @command="userCommand" trigger="click" >
                      <span class="el-dropdown-link pd10">
                          <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-yonghu"></use>
                          </svg>
                          <span> {{username}}</span>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="logout">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-tuichu1"></use>
                            </svg>
                            <span>退出</span>
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>

        <split-pane split="vertical" :className="splitClass" :defaultPercent=20  ref="split" @mousedown.native="onMouseDown" @mouseup.native="onMouseUp">
            <template slot="paneL">
                <el-container>
                    <el-main class="pt10">
                        <el-tree ref="folderTree" :data="folderTree.body" highlight-current :props="folderTree.props"
                                 v-loading="folderTree.loading" :load="getLazyData" lazy node-key="id"
                                 :expand-on-click-node="false" @node-click="clickFolder">
                                <span class="custom-tree-node" slot-scope="{node, data}">
                                    <div v-if="data.kind === 'folder'">
                                        <svg class="icon" aria-hidden="true">
                                            <use xlink:href="#icon-wenjianjia3"></use>
                                        </svg>
                                        <span><b>{{node.label}}1</b></span>
                                    </div>
                                    <div v-else>
                                        <img v-if="!data.suffix" style="width: 20px;height: auto" :src="viewImageUrl" alt=""/>
                                        <app-svg v-if="data.suffix" :iconText="data.suffix"></app-svg>
                                        <span>{{node.label}}1</span>
                                    </div>
                                </span>
                        </el-tree>
                    </el-main>
                </el-container>
            </template>

            <template slot="paneR" >
                <div class="fxl" style="width: 100%;height: 100%">
                    <div style="float: left;width:50%;" class="fxl" v-if="LiteratureBoxShow">
                        <el-form inline class="link-item-content-expand" style="width: 60%">
                            <template v-for="(it, idx) in this.fileMetadataList">
                                <el-form-item :key="idx" :label="it.name">
                                    <span >{{ it.valueData }}</span>
                                </el-form-item>
                            </template>
                        </el-form>
                        <div style="width: 38%;">
                            <div v-for="(it, idx) in this.fileAccessoryList" :key="idx" @click="clickLiteratureFolder(it)" class="word-hidden finger" style="color: #409EFF;font-size: 14px;padding: 10px 0px 10px 0px">
                                <app-svg :iconText="it.suffix"></app-svg>
                                <el-tooltip effect="dark" :content="it.name+'.'+it.suffix" placement="bottom">
                                    <span >{{it.name}}.{{it.suffix}}</span>
                                </el-tooltip>
                            </div>
                            <div class="word-hidden finger" v-if="this.dbName" @click="selectReviewDB" style="color: #409EFF;font-size: 14px;padding: 10px 0px 10px 0px">
                                <svg t="1614846486792" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1852" width="1.2rem" height="1.2rem">
                                    <path d="M858.2 766.3V616.7c-22.1 19.3-47 35.2-73.7 47.2-86.5 38-180 57.2-274.5 56.3-122 7.1-242.8-27-343.1-96.7v150.7C173.7 840 318.8 912 512.3 912c193.5 0 337.5-72.6 345.9-138.9v-6.8z" p-id="1853" fill="#0072D9"></path><path d="M858.2 507v-2.8 6.8c-8.4 66.4-152.4 138.9-345.9 138.9-193.5 0-338.6-72-345.4-137.8V361.3C267.2 431.1 388 465.1 510 458c94.5 0.9 188-18.2 274.5-56.3 26.7-12.1 51.6-28 73.7-47.2V507z" p-id="1854" fill="#0072D9"></path><path d="M762.6 160.5c57.4 25.3 91.7 61.3 91.7 93.4 0 31.5-35.4 66.4-91.7 93.4-80 34-166.2 50.7-253.1 48.9-196.9 0-343.7-74.8-343.7-141.7s147.4-142.3 344.8-142.3c86.5-1.9 172.3 14.6 252 48.3z" p-id="1855" fill="#0072D9"></path>
                                </svg>
                                <el-tooltip effect="dark" :content="this.dbName" placement="bottom">
                                    <span>{{this.dbName}}</span>
                                </el-tooltip>
                            </div>
                        </div>
                    </div>
                    <app-frame ref="appFrame"></app-frame>
                </div>
                <preview-database ref="previewDatabase"></preview-database>
                <div style="display: none">
                    <img id="image" :src="imagePreview.src" alt="Picture">
                </div>
            </template>
        </split-pane>
    </el-container>
</template>

<script>
    import splitPane from 'vue-splitpane'
    import { mapState, mapActions } from 'vuex'
    import Cookie from 'js-cookie'
    import Viewer from 'viewerjs'
    import 'viewerjs/dist/viewer.css'
    import PreviewDatabase from '../doc/PreviewDatabase';
    import {formatDateTime} from "../../util/initData";
    export default {
        components: { splitPane, PreviewDatabase },
        data() {
            return {
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                folderTree:{
                    body: [],
                    props: {
                        children: 'children',
                        label: 'name'
                    },
                    loading: false,
                },
                splitClass:'',
                linkRandom: '',
                shareRandom:'',
                imagePreview: {
                    src:'',
                    viewer:''
                },
                fileAccessoryList:[],
                fileMetadataList:[],
                dbId: 0,
                dbName:'',
                //文献数据div是否显示
                LiteratureBoxShow: false,
            }
        },
        computed: {
            ...mapState({
                username: state => state.user.name,
                operation: state => state.user.operation
            })
        },
        mounted() {
            this.linkRandom = this.$route.query.link
            this.shareRandom = this.$route.query.shared
            this.getData();
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
        methods: {
            ...mapActions({
                sysLogout: 'auth/logout'
            }),
            getData(){
                this.folderTree.loading = true
                let url;
                if (this.shareRandom){
                    let password = Cookie.get(this.shareRandom)
                    url = `/app/link/${this.shareRandom}/?password=${password}`
                }
                else
                {
                    url = `/user/link/${this.linkRandom}`
                }
                this.$axios({
                    url: url,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        this.folderTree.body = res.ctt.resources
                    }else {
                        this.$message.error(res.msg)
                    }
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                this.folderTree.loading = false
            },
            getLazyData(node, resolve){
                if (node.level === 0) {
                    return
                }
                let id
                if (node.data.kind === 'folder'){
                    if (node.data.resourceId)
                        id = node.data.resourceId
                    else
                        id = node.data.id
                } else {
                    resolve([])
                    return
                }

                this.folderTree.loading = true
                this.$axios({
                    url: `/user/link/folder/${id}/docs`,
                    method: 'get'
                }).then(res => {
                    resolve(res.ctt)
                    this.folderTree.loading = false
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
                return
            },
            //点击文件
            clickFolder(data){
                if (data.kind === 'folder') return
                this.LiteratureBoxShow = false;
                this.$refs.appFrame.isShow = false;
                this.fileAccessoryList = [];
                this.fileMetadataList = [];
                this.dbName = '';
                let id
                if (data.resourceId)
                    id = data.resourceId
                else
                    id = data.id
                if(data.resource != null && data.resource.docType == 20 || data.docType == 20){
                    this.$axios({
                        url: `/user/file/${id}/Literature`,
                        method: 'get',
                    }).then((res) => {
                        if(res.c == 0){
                            this.fileAccessoryList = res.ctt.fileAccessoryDto.filter((i) => i.type !== 2);
                            let isDb = res.ctt.fileAccessoryDto.filter((i) => i.type == 2);
                            if (isDb && isDb.length > 0) {
                                this.dbId = isDb[0].saveId;
                                this.dbName = isDb[0].name;
                            }
                            this.fileMetadataList = res.ctt.fileMetadataDto;
                            this.fileMetadataList.forEach(dto=>{
                                if(dto.dataType === 7 && dto.value != ''){
                                    dto.valueData = formatDateTime(dto.value, '年月日');
                                } else if(dto.dataType === 6 && dto.value != ''){
                                    dto.valueData = dto.value ? '是' : '否';
                                }else{
                                    dto.valueData = dto.value;
                                }
                            })
                            this.LiteratureBoxShow = true;
                        }else{
                            this.$message.error(res.msg);
                        }
                    })
                }else{
                    this.selectFolder(`/user/file/${id}/seq/0/preview`);
                }


            },
            //点击文献中的文件
            clickLiteratureFolder(data){
                this.$refs.appFrame.isShow = false;
                this.selectFolder(`/user/file/${data.fileId}/accessory/${data.id}/seq/0/preview`);
            },
            //查看文件
            selectFolder(url){
                this.$axios({
                    url: url,
                    method: 'get',
                }).then((res) => {
                    if(res.c == 0){
                        if(this.$refs.appFrame) this.$refs.appFrame.loadFile(res.ctt.suffix, res.ctt.url.url)
                    }else{
                        this.$message.error(res.msg);
                    }
                })
            },
            //查看数据库
            selectReviewDB() {
                this.$refs.appFrame.isShow = false;
                this.$refs['previewDatabase'].dbId = this.dbId;
                this.$refs['previewDatabase'].dbName = this.dbName;
                this.$refs['previewDatabase'].visible = true;
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            message(){
                this.$router.push('/message')
            },
            userCommand(key){
                switch (key) {
                    case 'admin':
                        this.$router.push('/admin')
                        break
                    case 'logout':
                        this.sysLogout().then(() => {
                            this.$router.push('/login')
                        })
                        break
                }
            },
        }
    }
</script>
<style lang="scss">
    .link-item-content-expand {
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
    .resource-file-list {
        display: flex;
        align-items: center;
        margin-top: 10px;
    }

</style>

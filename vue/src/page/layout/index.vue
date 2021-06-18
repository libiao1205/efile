<template>
    <el-container>
        <el-header class="apphead fx-lr pr0" style="margin-bottom: 5px;box-shadow: 1px 2px 6px #cecece">
            <div style="width: calc(100% - 8rem);" class="fxl">
                <div style="margin-right: 4%;min-width: 310px">
                    <h1 class="fb text1em fxc"
                        style="color: black;font-weight: bold;letter-spacing: 3px; padding-left: 20px;font-size: 26px">
                        <!--                <svg class="icon" aria-hidden="true">-->
                        <!--                    <use xlink:href="#icon-as-wendangku"></use>-->
                        <!--                </svg>-->
                        <img style="width: 50px;height: auto;margin-right: 10px" :src="viewImageUrl" alt=""/>
                        四知★智库知识库
                    </h1>
                </div>
                <div class="fxr" v-if="folderHeader != null" style="margin-right: 6%">
                    <el-button-group>
                        <el-button size="mini" icon="el-icon-arrow-left" @click="preFolderAction"></el-button>
                        <el-button size="mini" icon="el-icon-arrow-right" @click="nextFolderAction"></el-button>
                    </el-button-group>
                    <el-breadcrumb separator-class="el-icon-arrow-right" class="pl10" :data="folderHeader">
                        <el-breadcrumb-item
                            v-for='(item) in folderHeader.item'
                            :key='item.id'
                        >
                            <template v-if='item.name'>
                                <a href="javascript:void(0);" style="cursor: pointer;" @click="breadcrumbClick(item)">
                                    {{item.name}}
                                </a>
                            </template>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div v-if="folderHeader != null" style="width: 40%;" class="fx-lr">
                    <div class="ib fxl searchBox fxc">
                        <el-input placeholder="请输入内容" class="input-with-select" v-model="searchTxt" @keyup.enter.native = "searchDoc" clearable></el-input>
                        <div @click="searchDoc" slot="append" class="fx-t-b" style="height:34px;width: 60px;border: 1px solid #409EFF;background-color: #409EFF;border-bottom-right-radius:34px;border-top-right-radius:34px;">
                            <i class="el-icon-search" style="font-size: 24px;color: white"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div class="top-menu-group fxc">
<!--                <a href=" javascript:void(0) " @click="message" class="top-menu-item fxc plr10" style="text-decoration-line:none;">-->
<!--                    <div v-if="this.messages.has">-->
<!--                        <el-badge :value="this.messages.count" :max="99" class="item">-->
<!--                            <svg class="icon" aria-hidden="true">-->
<!--                                <use xlink:href="#icon-xiaoxi1"></use>-->
<!--                            </svg>-->
<!--                        </el-badge>-->
<!--                    </div>-->
<!--                    <div v-else>-->
<!--                        <svg class="icon" aria-hidden="true">-->
<!--                            <use xlink:href="#icon-xiaoxi1"></use>-->
<!--                        </svg>-->
<!--                    </div>-->
<!--                    <span class="pl10 tnl">消息</span>-->
<!--                </a>-->
                <svg style="width: 2rem;height: 2rem" t="1615535068365" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5225">
                    <path d="M795.794286 938.203429l-3.876572 2.56a512.438857 512.438857 0 0 1-180.882285 73.654857l-9.069715 1.682285-5.412571 0.950858-2.413714 0.365714-6.729143 1.097143-7.68 1.024-2.194286 0.292571 1.755429-0.219428c-16.310857 2.194286-32.914286 3.510857-49.737143 4.096L512 1024c-26.404571 0-52.370286-1.974857-77.750857-5.851429l-4.388572-0.731428-2.925714-0.438857a509.878857 509.878857 0 0 1-11.702857-2.121143l-2.194286-0.438857-1.024-0.146286a510.098286 510.098286 0 0 1-3.218285-0.731429l-7.314286-1.536-5.193143-1.170285a504.100571 504.100571 0 0 1-26.331429-6.802286l-7.094857-2.121143a403.163429 403.163429 0 0 1-10.532571-3.291428l-6.802286-2.340572a488.813714 488.813714 0 0 1-25.892571-9.654857l-5.558857-2.267429a507.977143 507.977143 0 0 1-68.681143-35.108571l-4.681143-2.925714-8.630857-5.558857-3.876572-2.56-2.486857-1.609143a514.633143 514.633143 0 0 1-155.062857-164.864l-0.731429-1.316572a511.049143 511.049143 0 0 1-22.454857-42.788571l-0.512-1.170286a509.147429 509.147429 0 0 1-25.161143-66.267428l0.512 1.901714a512 512 0 1 1 979.382858 0l0.512-1.901714a508.050286 508.050286 0 0 1-25.161143 66.267428l-0.512 1.170286a510.025143 510.025143 0 0 1-22.454857 42.788571l-0.731429 1.316572a513.462857 513.462857 0 0 1-155.062857 164.864l-2.413714 1.609143zM648.045714 602.331429H375.954286a240.932571 240.932571 0 0 0-231.131429 172.763428 451.510857 451.510857 0 0 0 734.208 0A240.932571 240.932571 0 0 0 648.045714 602.331429zM512 180.736a180.662857 180.662857 0 1 0 0 361.325714 180.662857 180.662857 0 0 0 0-361.325714z" p-id="5226" fill="#80B6FF"></path>
                </svg>
                <el-dropdown @command="userCommand" trigger="click" >
                      <span class="el-dropdown-link pd10">

                          <span> {{username}}</span>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="changePassword">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-wode-mima"></use>
                            </svg>
                            <span>修改密码</span>
                        </el-dropdown-item>
                        <el-dropdown-item command="tagList">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-biaoqian1"></use>
                            </svg>
                            <span>我的标签</span>
                        </el-dropdown-item>
                        <!--<el-dropdown-item disabled>-->
                            <!--<svg class="icon" aria-hidden="true">-->
                                <!--<use xlink:href="#icon-MDpeise"></use>-->
                            <!--</svg>-->
                            <!--<span>自定义配色</span>-->
                        <!--</el-dropdown-item>-->
                        <!--<el-dropdown-item command="notice">-->
                            <!--<svg class="icon" aria-hidden="true">-->
                                <!--<use xlink:href="#icon-xiaoxi1"></use>-->
                            <!--</svg>-->
                            <!--<span>通知设定</span>-->
                        <!--</el-dropdown-item>-->
                        <el-dropdown-item v-if="operation.indexOf('ROLE_ADMIN') >= 0" command="admin">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-shezhi1"></use>
                            </svg>
                            <span>进入管理</span>
                        </el-dropdown-item>
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
            <template slot="paneL" >
                <el-container>
                    <el-main class="pt10" style="background-color:#D5EBF8;">
                        <el-tree ref="folderTree" :data="folderTree.body" highlight-current v-loading="folderTree.loading" :props="folderTree.props"
                                 :default-expand-all="false" :expand-on-click-node="false" node-key='id' :default-expanded-keys="[1]"
                                 lazy :load="printFolderTree" @node-click="onFolderSelect">
                                <span class="custom-tree-node"  slot-scope="{node, data}">
                                    <div v-if="data.parentId === 0" style="width: 84%;" class="fx-lr">
<!--                                        <app-svg iconClass="zhuye11"></app-svg>-->
                                        <span>{{node.label}}</span>
                                    </div>
                                    <div v-else>
                                        <svg style="margin-right: 2px" t="1615789118288" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="45446" width="200" height="200">
                                            <path d="M853.333333 938.666667H170.666667a128 128 0 0 1-128-128V213.333333a128 128 0 0 1 128-128h213.333333c12.16 0 24.234667 5.205333 32.682667 15.232L512 214.186667V213.333333h341.333333a128 128 0 0 1 128 128v469.333334a128 128 0 0 1-128 128zM170.666667 170.666667a42.538667 42.538667 0 0 0-42.24 36.864L128 213.333333v597.333334a42.538667 42.538667 0 0 0 36.864 42.24L170.666667 853.333333h682.666666a42.538667 42.538667 0 0 0 42.24-36.864L896 810.666667V341.333333a42.538667 42.538667 0 0 0-36.864-42.24L853.333333 298.666667h-361.941333a42.666667 42.666667 0 0 1-32.682667-15.232L364.074667 170.666667H170.666667z" fill="#666666" p-id="45447"></path>
                                        </svg>
                                        {{node.label}}
                                    </div>
                                </span>
                        </el-tree>
                    </el-main>
                    <el-footer class="bt1 overflow" height="" style="background-color:#D5EBF8;">
                        <div class="fx-lr-c">
                            <div class="ib pd10">
                                <span @click="link" class="finger" style="margin-right: 16px">
                                    <svg style="margin-right: 6px" t="1615781013852" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8689" width="200" height="200">
                                        <path d="M911.6 651.6l0 209.2c0 36-30.4 66.2-66.2 66.2l-699.6 1.2c-35.8 0-65-29.2-65-65.2l1.2-699.6c0-35.8 30.4-66.2 66.2-66.2l387 0L535.2 20.2 148.2 20.2c-81.8 0-143 82.8-143 156.8l0 686c0 77.6 63 140.8 140.8 140.8l686 0c82 0 156.6-68.2 156.6-143L988.6 651.6 911.6 651.6 911.6 651.6zM730.2 60.8l288.6 289.2L730.2 639l0-165.2c0 0-286.4-31.8-453.6 206.6 0 0 52.6-454.4 453.6-454.4L730.2 60.8 730.2 60.8z" p-id="8690" fill="#29A5FA"></path>
                                    </svg>
                                    <b>我的分享</b>
                                </span>
                                <span style="width: 97px"  class="finger" @click="trash">
                                    <svg style="width: 1.4rem;height: 1.4rem;position: relative;top:2px;margin-right: 2px" t="1615783190409" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="34593">
                                        <path d="M646.257778 98.986667c2.844444 0 5.688889 0.568889 7.964444 1.706666 29.013333 15.928889 45.511111 38.115556 42.666667 73.955556-1.706667 18.204444 7.964444 27.875556 27.306667 26.737778 12.515556-0.568889 25.6-0.568889 38.115555 0 35.84 1.706667 58.595556 25.031111 60.871111 60.871111 0.568889 9.671111-1.137778 19.342222 0.568889 29.013333 3.413333 26.168889-8.533333 32.995556-32.995555 32.995556-188.871111-0.568889-377.742222-0.568889-566.613334 0-24.462222 0-33.564444-6.826667-32.995555-32.995556 0.568889-75.093333 18.204444-95.004444 94.435555-93.866667 27.306667 0.568889 39.822222-7.964444 37.546667-36.408888-2.844444-30.151111 18.773333-46.08 41.528889-59.733334a13.084444 13.084444 0 0 1 7.964444-2.275555h273.635556zM506.311111 201.955556c37.546667 0 74.524444-0.568889 112.071111 0 20.48 0.568889 28.444444-7.964444 28.444445-28.444445s-10.808889-24.462222-28.444445-24.462222c-74.524444 0.568889-149.617778 0.568889-224.142222 0-17.635556 0-28.444444 3.982222-28.444444 24.462222s7.964444 29.013333 28.444444 28.444445c36.977778-0.568889 74.524444 0 112.071111 0zM778.808889 364.088889c-91.022222-0.568889-181.475556 0-272.497778 0H237.226667c-39.822222 0-44.373333 5.688889-40.391111 44.942222 1.706667 17.066667 3.982222 34.133333 5.688888 50.631111 13.653333 138.24 28.444444 275.911111 40.96 414.151111 2.844444 33.564444 15.928889 44.942222 50.062223 44.942223 141.084444-1.706667 282.168889-0.568889 422.684444-0.568889 44.373333 0 47.786667-2.275556 52.337778-44.942223 15.928889-155.875556 31.857778-311.751111 46.648889-468.195555 3.982222-37.546667 0.568889-40.391111-36.408889-40.96z m-461.368889 295.253333v-14.222222c8.533333-15.36 17.066667-30.72 25.6-46.648889-6.826667-5.688889-13.653333-11.377778-23.324444-19.911111H398.222222c2.844444 0 6.826667 3.413333 8.533334 6.257778 11.946667 21.048889 23.324444 42.666667 36.977777 67.697778-9.671111-4.551111-15.928889-7.395556-22.755555-10.24-8.533333 14.222222-16.497778 28.444444-26.168889 42.097777-3.413333 4.551111-10.24 8.533333-15.36 8.533334-27.306667 0.568889-50.062222-6.826667-62.008889-33.564445z m159.857778 135.964445c-27.875556-1.706667-55.182222-1.137778-81.351111-5.12-9.102222-1.137778-19.342222-10.808889-24.462223-19.342223-15.36-25.031111-28.444444-51.768889-41.528888-75.093333 18.204444 2.844444 35.84 7.395556 54.044444 7.964445 30.72 1.137778 62.008889 0.568889 93.297778 0.568888v91.022223z m23.324444-283.875556c-13.653333 25.031111-28.444444 49.493333-43.235555 76.231111-26.168889-14.791111-51.2-28.444444-78.506667-43.804444 13.653333-22.755556 26.737778-45.511111 40.96-67.128889 6.826667-10.808889 18.204444-15.928889 31.857778-15.36 25.031111 0.568889 49.493333 0.568889 74.524444 0.568889 4.551111 0 9.102222 1.137778 18.204445 1.706666-24.462222 10.808889-34.133333 29.013333-43.804445 47.786667z m60.871111-41.528889c12.515556-1.137778 22.186667 3.413333 29.013334 13.653334 7.964444 12.515556 16.497778 24.462222 25.031111 37.546666 7.964444-2.844444 15.928889-6.257778 27.306666-10.24-13.653333 25.031111-25.6 47.786667-38.115555 69.404445-1.137778 1.706667-4.551111 3.413333-6.826667 3.413333-23.893333 0-47.786667-0.568889-71.68-1.137778-1.137778 0-2.275556-1.137778-4.551111-2.275555 6.257778-4.551111 11.377778-7.964444 17.635556-12.515556-7.964444-14.222222-16.497778-28.444444-23.893334-43.235555-1.706667-3.413333-4.551111-6.826667-3.982222-10.24 2.844444-18.773333 30.72-42.666667 50.062222-44.373334z m66.56 307.2c-3.982222 6.826667-13.084444 11.946667-21.048889 14.222222-9.671111 2.844444-21.048889 3.413333-31.288888 2.844445-14.791111-1.137778-19.342222 5.688889-19.342223 19.342222 0 2.275556-0.568889 4.551111-0.568889 6.826667-1.137778 0.568889-2.275556 1.137778-2.844444 1.706666-13.653333-21.048889-26.737778-42.097778-39.822222-63.715555-1.706667-2.275556-1.137778-6.257778 0.568889-9.102222 11.946667-21.617778 24.462222-43.235556 36.977777-64.284445 1.137778 0.568889 2.275556 1.137778 2.844445 1.137778 2.844444 21.048889 2.844444 21.048889 25.031111 19.911111 3.982222 0 7.395556-0.568889 11.377778 0 11.946667 1.137778 25.031111-4.551111 32.995555 11.946667 9.102222 19.342222 17.635556 38.684444 5.12 59.164444z m62.008889-93.866666c-15.36 25.031111-29.582222 51.2-43.804444 76.231111 0-21.048889-8.533333-38.115556-19.342222-54.613334-16.497778-25.6-31.857778-51.768889-48.924445-79.075555 25.031111-15.928889 49.493333-31.288889 76.231111-47.786667 13.084444 22.186667 26.168889 43.804444 38.115556 65.422222 6.826667 13.084444 5.688889 26.737778-2.275556 39.822223z" p-id="34594" fill="#29A5FA"></path>
                                    </svg>
                                    <b>回收站</b>
                                </span>
                            </div>
                        </div>
                    </el-footer>
                </el-container>
            </template>
            <template slot="paneR">
                <!--<keep-alive>-->
                    <router-view @lay_addFolder="addFolder" @lay_delFolder="delFolder" @lay_putFolder="putFolder" ></router-view>
                <!--</keep-alive>-->
            </template>
        </split-pane>
        <tag ref="myTag"></tag>
        <password ref="myPassword"></password>
        <notice-message ref="notice"></notice-message>
    </el-container>
</template>

<script>
    import splitPane from 'vue-splitpane'
    import Tag from './Tag'
    import Password from './Password'
    import { mapState, mapActions } from 'vuex'
    import NoticeMessage from '../message/noticeMessage'
    import Utils from "../../util/utils";
    export default {
        components: { splitPane, Tag , Password, NoticeMessage},
        created(){
            this.$store.commit("setSelectFolder", null)
        },
        mounted() {
            if (this.$debug === false){
                this.getMessageCount()
                window.InitSetInterval = setInterval(() => {
                    setTimeout(this.getMessageCount(), 0)
                }, 30000)
            }
            Utils.$on('setFolderHeader', (folderHeader) => {
                this.folderHeader = folderHeader;
            });
        },
        destroyed() {
            clearInterval(window.InitSetInterval)
        },
        watch:{
            moveFolder(){
                if (this.moveFolder === null) return;
                let data;
                if (this.$refs.folderTree.getNode(this.moveFolder.id)) {
                    this.$refs.folderTree.setCurrentNode(this.$refs.folderTree.getNode(this.moveFolder.id).data)
                    data = this.$refs.folderTree.getNode(this.moveFolder.id).data;
                    this.setSelectFolder(data)
                } else {
                    this.$axios({
                        url: `/user/folder/${this.moveFolder.id}`,
                        method: 'get'
                    }).then(res => {
                        data = res.ctt;
                        this.setSelectFolder(data)
                    }).catch(err => {
                        this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                    })
                }
            },
            $route(to,from){
                this.folderHeader = null;
            }
        },
        computed: {
            ...mapState({
                username: state => state.user.name,
                operation: state => state.user.operation,
                moveFolder : state => state.folderHistory.moveFolder
            })
        },
        data() {
            return {
                messages:{
                  has:false,
                  count:0,
                },
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
                splitClass:'',
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
                searchTxt:'',
                folderHeader:null,
            }
        },
        methods: {
            ...mapActions({
                preFolderAction: 'folderHistory/preFolder',
                nextFolderAction: 'folderHistory/nextFolder',
                addHis: 'folderHistory/addFolder',
                addHisById: 'folderHistory/addFolderById',
                sysLogout: 'auth/logout',
                addHis: 'folderHistory/addFolder',
            }),
            // 获取message数据
            getMessageCount() {
                this.$axios({
                    url: `/user/messages/count`,
                    method: 'get'
                }).then(res => {
                    this.messages.has = res.ctt > 0
                    this.messages.count = res.ctt
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
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
                    url: `/user/folder/${id}/children`,
                    method: 'get'
                }).then(res => {
                    if(res.c === 0){
                        let permissionBody = [];
                        for( let i = 0; i < res.ctt.length; i++){
                            //if (this.isShowAll === false && res.ctt[i].disabled === true ) continue
                            if (res.ctt[i].permissionMap.visible){
                                permissionBody.push(res.ctt[i])
                            }
                        }

                        resolve(permissionBody)
                        this.folderTree.loading = false
                        this.$nextTick(() => {
                            this.$refs.folderTree.setCurrentKey(id)
                            this.folderTree.selected = this.$refs.folderTree.getCurrentNode()
                            this.$store.commit("setSelectFolder", this.folderTree.selected)
                            this.addHis(this.folderTree.selected)
                        });
                    }else{
                        resolve([])
                        this.$message.error(res.msg)
                        this.folderTree.loading = false
                        // this.refresh();
                    }

                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            setSelectFolder(data){
                this.$router.push('/home')
                this.folderTree.selected = data
                this.$store.commit("setSelectFolder", data)
            },
            onFolderSelect(data){
                if (this.folderTree.selected.id === data.id) {
                    return;
                }

                if (this.folderTree.selected.id !== data.id){
                    this.addHis(data)
                }
                this.setSelectFolder(data)
            },
            onMouseDown(){
                if (this.$refs.split.active) this.splitClass = 'active'
            },
            onMouseUp(){
                if (!this.$refs.split.active)  this.splitClass = ''
            },
            addFolder(res){
                this.$refs.folderTree.append(res.ctt, res.ctt.parentId)
            },
            delFolder(data){
                this.$refs.folderTree.remove(data)
            },
            // delFolders(data){
            //     this.$refs.folderTree.remove(data)
            // },
            putFolder(data){
                this.$refs.folderTree.remove(data)
                this.$refs.folderTree.append(data, data.parentId)
                let node = this.$refs.folderTree.getNode(data)
                node.data.name = data.name;
            },
            link(){
                this.$router.push('/link')
            },
            trash(){
                this.$router.push('/trash')
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
                    case 'tagList':
                        this.$refs.myTag.handleTag()
                        break
                    case 'notice':
                        this.$refs.notice.handleNotice()
                        break
                    case 'changePassword':
                        this.$refs.myPassword.handlePassword()
                        break
                }
            },
            getLogo(){
                this.$axios({
                    url: '/setting/getFolderRootLogo',
                    method: 'get'
                }).then(res => {
                    this.messages.has = res.ctt > 0
                    this.messages.count = res.ctt
                }).catch(err => {
                    this.$message.error(`获取数据失败，失败码：${err.response.status}`)
                })
            },
            searchDoc(){
                Utils.$emit('search',this.searchTxt);
            },
            breadcrumbClick(item){
                Utils.$emit('breadcrumbClick',item);
            }
        },

    }
</script>

<style scoped>
    .el-tree{
        display: inline-block!important;
        min-width: 200px;
        background-color:#D5EBF8;
    }
    .el-tree-node
    {
        display: inline-block!important;
        min-width: 200px;
    }
    .el-tree-node__content{
        display: inline-block!important;
        min-width: 200px;
    }
    .custom-tree-node{
        display: inline-block!important;
        min-width: 100%;
    }
    .el-dropdown-link {
        cursor: pointer;
        color: black;
        font-weight: 600;
    }

</style>

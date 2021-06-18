<template>
    <el-container>
        <el-header class="apphead1 fxb bb1 pr0">
            <h1 class="fb text1em" style="color: white;font-weight: bold; font-style: italic;letter-spacing: 3px; padding-left: 20px;">
<!--                <svg class="icon" aria-hidden="true">-->
<!--                    <use xlink:href="#icon-as-wendangku"></use>-->
<!--                </svg>-->
                <img style="width: 30px;height: auto;" :src="viewImageUrl" alt=""/>
                四知★智库知识库 - 后台管理
            </h1>
            <div class="top-menu-group fxc">
                <el-dropdown @command="userCommand" trigger="click"  class="bl1">
                      <span class="el-dropdown-link pd10" style="background-color: #f8f8f8">
                          <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-yonghu"></use>
                          </svg>
                          <span> {{username}}</span>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="main">
                            <svg class="icon" aria-hidden="true">
                                <use xlink:href="#icon-yunshujuku"></use>
                            </svg>
                            <span>归档系统</span>
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
        <el-container>
            <el-aside>
                <el-menu router :default-active="activeMenu" class="el-menu-vertical-demo">
                    <el-menu-item index="/admin">
                        <app-svg iconClass="wenjianjia"></app-svg>
                        <span slot="title">文件夹管理</span>
                    </el-menu-item>
                    <el-submenu index="1">
                        <template slot="title">
                            <app-svg iconClass="drxx74"></app-svg>
                            <span>权限管理</span>
                        </template>
                        <el-menu-item v-if="level == 1" index="/admin/roledept">
                            <app-svg iconClass="drxx95"></app-svg>
                            <span>组织架构权限设定</span>
                        </el-menu-item>
                        <el-menu-item index="/admin/rolefolder">
                            <app-svg iconClass="wenjianjia1"></app-svg>
                            <span>文件夹权限设定</span>
                        </el-menu-item>
                        <el-menu-item v-if="level == 1" index="/admin/admininfo">
                            <app-svg iconClass="guanlirenyuan"></app-svg>
                            <span>管理员设定</span>
                        </el-menu-item>
<!--                        <el-menu-item index="/admin/folderip">-->
<!--                            <app-svg iconClass="jueceliubaimingdan"></app-svg>-->
<!--                            <span>文件夹访问白名单</span>-->
<!--                        </el-menu-item>-->
                    </el-submenu>
                    <el-submenu index="2">
                        <template slot="title">
                            <app-svg iconClass="drxx10"></app-svg>
                            <span>虚拟团队</span>
                        </template>
                        <el-menu-item index="/admin/usergroup">
                            <app-svg iconClass="lyq-qunguanli"></app-svg>
                            <span>团队管理</span>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu v-if="level == 1" index="3">
                        <template slot="title">
                            <app-svg iconClass="database"></app-svg>
                            <span>基础数据</span>
                        </template>
                        <el-menu-item index="/admin/dept">
                            <app-svg iconClass="drxx95"></app-svg>
                            <span>组织架构</span>
                        </el-menu-item>
                        <el-menu-item index="/admin/user">
                            <app-svg iconClass="drxx10"></app-svg>
                            <span>用户管理</span>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu v-if="level == 1" index="4">
                        <template slot="title">
                            <app-svg iconClass="icon-test"></app-svg>
                            <span>系统</span>
                        </template>
                        <el-menu-item index="/admin/sys">
                            <app-svg iconClass="shezhi2"></app-svg>
                            <span>系统设置</span>
                        </el-menu-item>
                        <el-menu-item index="/admin/log">
                            <app-svg iconClass="zhinengrizhiguanlipingtai"></app-svg>
                            <span>系统日志</span>
                        </el-menu-item>
                        <el-menu-item index="/admin/backup">
                            <app-svg iconClass="yunshujuku"></app-svg>
                            <span>系统备份</span>
                        </el-menu-item>
                    </el-submenu>
                    <el-submenu index="5">
                        <template slot="title">
                            <app-svg iconClass="OA"></app-svg>
                            <span>数据管理</span>
                        </template>
                        <el-menu-item index="/admin/database">
                            <app-svg iconClass="shezhi1"></app-svg>
                            <span>数据源管理</span>
                        </el-menu-item>
                        <el-menu-item v-if="level == 1" index="/admin/metadata">
                            <app-svg iconClass="web-xietongwendang"></app-svg>
                            <span>元数据设置</span>
                        </el-menu-item>
                        <el-menu-item v-if="level == 1" index="/admin/solr">
                            <app-svg iconClass="shezhi1"></app-svg>
                            <span>solr管理</span>
                        </el-menu-item>
                    </el-submenu>
                    <!--<el-submenu v-if="level == 1" index="5">-->
                        <!--<template slot="title">-->
                            <!--<app-svg iconClass="OA"></app-svg>-->
                            <!--<span>OA设定</span>-->
                        <!--</template>-->
                        <!--<el-menu-item index="/admin/oafolder">-->
                            <!--<app-svg iconClass="web-xietongwendang"></app-svg>-->
                            <!--<span>附件目录设定</span>-->
                        <!--</el-menu-item>-->
                        <!--<el-menu-item index="/admin/oaapi">-->
                            <!--<app-svg iconClass="shezhi1"></app-svg>-->
                            <!--<span>批处理设定</span>-->
                        <!--</el-menu-item>-->
                    <!--</el-submenu>-->
                    <el-menu-item v-if="level == 1" index="/admin/link">
                        <app-svg iconClass="gongxiangwenjianjia"></app-svg>
                        <span slot="title">外链</span>
                    </el-menu-item>
                    <el-menu-item v-if="level == 1" index="/admin/trash">
                        <app-svg iconClass="weibiaoti-"></app-svg>
                        <span slot="title">回收站</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main class="pd0">
                <!--<keep-alive>-->
                    <router-view></router-view>
                <!--</keep-alive>-->
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import { mapState, mapActions } from 'vuex'
    import Utils from '../../../util/utils'
    export default {
        mounted() {
            let _this = this;
            Utils.$on('refreshImg',function () {
                _this.viewImageUrl = _this.viewImageUrl+new Date().getTime();
            })
        },
        computed: {
            ...mapState({
                username: state => state.user.name,
                level: state => state.user.level
            })
        },
        data() {
            return {
                activeMenu: this.$route.path,
                viewImageUrl: `${this.$upload}/setting/getLogoImg?seed=`,
            }
        },
        methods: {
            ...mapActions({
                sysLogout: 'auth/logout'
            }),
            userCommand(key){
                switch (key) {
                    case 'main':
                        this.$router.push('/home')
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

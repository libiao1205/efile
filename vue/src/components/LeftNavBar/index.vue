<template>
    <div class="side-nav left">
        <div class="side-inner">
            <div class="user-info">
                <i class="el-icon-star-on"></i>
                <div clss="user-name">
                    欢迎您，{{username}}
                </div>
            </div>
            <div class="operator">
                <el-row>
                    <el-button size="small" @click="logout" icon="el-icon-back" plain>退出</el-button>
                    <slot name="btn"></slot>
                </el-row>
            </div>
            <slot name="menu"></slot>
        </div>
    </div>
</template>

<script>
    import { mapState, mapActions } from 'vuex'
    export default {
        name: "LeftNavBar",
        computed: {
            ...mapState({
                username: state => state.user.name
            })
        },
        methods: {
            ...mapActions({
                sysLogout: 'auth/logout'
            }),
            logout() {
                this.sysLogout().then(() => {
                    this.$router.push('/login')
                })
            }
        }
    }
</script>

<style lang="scss">
    @import 'sysStatic/css/theme/default.scss';
    .side-nav {
        text-align:center;
        div{
            color:$themeColor;
        }
        .side-inner {
            margin: 10px;
            width:90%;
            height:95%;
            border:1px solid #b9b9b9;
            border-radius:5px;
            background:white;
        }
        .user-info{
            padding:20px;
            line-height: 30px;
            .el-icon-star-on{
                font-size:30px;
                color:$themeColor;
                float:left;
            }
        }
        .el-tree{
            margin-top: 20px;
        }
    }
</style>

import Cookies from 'js-cookie'
import axios from '@/util/ajax'
import Auth from '@/util/auth'
import store from "../../index";

const state = {
    token: '',
    passwordExceed:null,
    navList: []
}

const mutations = {
    setNavList: (state, data) => {
        state.navList = data
    },

    setToken: (state, data) => {
        if(data){
            Auth.setToken(data)
            Auth.setLoginStatus()
        } else {
            Auth.removeToken()
            Auth.removeLoginStatus()
        }
        state.token = data
    },

    setPasswordExceed: (state, data) => {
        if(data){
            Auth.setPasswordExceed(data)
        } else {
            Auth.removePasswordExceed()
        }
        state.passwordExceed = data
    }
}

const actions = {
    // 邮箱登录
    loginByEmail({commit}, userInfo) {
        return new Promise((resolve) => {
            axios({
                url: '/auth/login',
                method: 'post',
                params: {
                    username: userInfo.name,
                    password: userInfo.password
                }
            }).then(res => {
                if (res.ctt && res.ctt.token) {
                    res.login = true
                    commit('setToken', res.ctt.token)
                    commit('setPasswordExceed', res.ctt.exceed)
                    commit('user/setName', res.ctt.name, {root: true})
                    commit('user/setOperation', res.ctt.operation, {root: true})
                    commit('user/setLevel', res.ctt.level, {root: true})
                } else {
                    res.login = false
                    res.message = res.ctt.mistakeMsg
                }
                resolve(res)
            })
        });
    },

    // 登出
    logout({commit}) {
        return new Promise((resolve) => {
            commit('setToken', '')
            commit('user/setName', '', { root: true })
            commit('user/setOperation', '', { root: true })
            commit('user/setLevel', '', { root: true })
            commit('tagNav/removeTagNav', '', {root: true})
            resolve()
        })
    },

    // 重新获取用户信息及Token
    // TODO: 这里不需要提供用户名和密码，实际中请根据接口自行修改
    relogin({dispatch, commit, state}){
        return new Promise((resolve) => {
            // 根据Token进行重新登录
            let token = Cookies.get('token'),
                userName = Cookies.get('userName'),
                operation = Cookies.get('operation'),
                level = Cookies.get('level')

            // 重新登录时校验Token是否存在，若不存在则获取
            if(!token){
                dispatch("getNewToken").then(() => {
                    commit('setToken', state.token)
                })
            } else {
                commit('setToken', token)
            }
            // 刷新/关闭浏览器再进入时获取用户名
            commit('user/setName', decodeURIComponent(userName), { root: true })
            commit('user/setOperation', operation, { root: true })
            commit('user/setLevel', level, { root: true })
            resolve()
        })
    },

    // 获取新Token
    getNewToken({commit, state}){
        return new Promise((resolve) => {
            // axios({
            //     url: '/getToken',
            //     method: 'get',
            //     param: {
            //         token: state.token
            //     }
            // }).then((res) =>{
            //     commit("setToken", `${store.state.user.token}`)
                resolve()
            // })
        })
    },

    // 获取该用户的菜单列表
    getNavList({commit}){
        return new Promise((resolve) =>{
            let res = [
                // {name: '集团人员管理',child: [
                //         {   name: '部门管理',
                //             path: '/example/table',
                //             permission: ['edit']},
                //         {   name: '人员管理',
                //             path: '/components/pageToolbar'}]}

                {path: '/admin/doc', name: '基础文件夹管理'},
                {path: '/admin/user', name: '集团人员管理'},
                {path: '/admin/dept', name: '组织架构管理'},
                {path: '/admin/usergroup', name: '用户组管理'},
                {path: '/admin/roledept', name: '部门权限管理'},
                {path: '/admin/rolefolder', name: '文件夹权限管理'},
                {path: '/admin/trash', name: '回收站'},
                {path: '/admin/link', name: '外链管理'},
                {path: '/admin/metadata', name: '元数据设置'},
                {path: '/admin/solr', name: 'solr管理'},
                {path: '/admin/database', name: '数据库管理'},
                {path: '/admin/sys', name: '系统设置'},
                {path: '/admin/log', name: '系统日志'},
                {path: '/admin/backup', name: '系统备份'},
                {path: '/admin/oafolder', name: '附件目录设定'},
                {path: '/admin/oaapi', name: '批处理设定'},
                {path: '/admin/admininfo', name: '管理员设定'},
                {path: '/admin/folderip', name: '白名单设置'},
                {path: '/share', name: '共享'},
                {path: '/preview', name: '预览'},
                {path: '/linkpreview', name: '分享预览'},
                {path: '/message', name: '通知'},
                {path: '/search', name: '检索'},
                {path: '/link', name: '分享'},
                {path: '/trash', name: '回收站'},
                {path: '/setting', name: '设置'},
                {path: '/setting/message', name: '通知设定'},
                {path: '/setting/theme', name: '皮肤'},
                {path: '/setting/password', name: '修改密码'},
                {path: '/setting/tag', name: '自定义标签'},
            ]
            commit("setNavList", res)
            resolve(res)
        })
    },

    // 将菜单列表扁平化形成权限列表
    getPermissionList({state}){
        return new Promise((resolve) =>{
            let permissionList = []
            // 将菜单数据扁平化为一级
            function flatNavList(arr){
                for(let v of arr){
                    if(v.child && v.child.length){
                        flatNavList(v.child)
                    } else{
                        permissionList.push(v)
                    }
                }
            }
            flatNavList(state.navList)
            resolve(permissionList)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

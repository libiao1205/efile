import Cookies from 'js-cookie'

const state = {
    // 用户名
    name: ''
}

const mutations = {
    setName: (state, data) => {
        if(data){
            Cookies.set('userName', encodeURIComponent(data), {
                expires: 365
            })
        } else {
            Cookies.remove('userName')
        }
        state.name = data
    },
    setOperation: (state, data) => {
        if(data){
            Cookies.set('operation', data, {
                expires: 365
            })
        } else {
            Cookies.remove('operation')
        }
        state.operation = data
    },
    setLevel: (state, data) => {
        if(data){
            Cookies.set('level', data, {
                expires: 365
            })
        } else {
            Cookies.remove('level')
        }
        state.level = data
    }
}

export default {
    namespaced: true,
    state,
    mutations
}

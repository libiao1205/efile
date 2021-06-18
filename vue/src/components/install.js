// 组件全局注册
import Vue from 'vue'

import AppPanel from './AppPanel'
import AppNotes from './AppNotes'
import AppTitle from './AppTitle'
import AppToolbar from './AppToolbar'
import AppSection from './AppSection'
import AppSearch from './AppSearch'
import TableMixin from './TableMixin'
import HeaderBar from './HeaderBar'
import LeftNavBar from './LeftNavBar'
import AppSvg from './AppSvg'
import AppFrame from './AppFrame'



// 组件库
const Components = [
    AppPanel,
    AppNotes,
    AppTitle,
    AppToolbar,
    AppSection,
    AppSearch,
    TableMixin,
    HeaderBar,
    LeftNavBar,
    AppSvg,
    AppFrame
]

// 注册全局组件
Components.map((com) =>{
    Vue.component(com.name, com)
})

export default Vue

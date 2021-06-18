const Layout = () => import(/* webpackChunkName: 'index' */ '../page/layout')
const AdminLayout = () => import(/* webpackChunkName: 'index' */ '../page/admin/layout')

const staticRoute = [

    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/error',
        component: () => import(/* webpackChunkName: 'error' */ '../page/error'),
        children: [
            {
                path: '401',
                component: () => import(/* webpackChunkName: 'error' */ '../page/error/401')
            },
            {
                path: '403',
                component: () => import(/* webpackChunkName: 'error' */ '../page/error/403')
            },
            {
                path: '404',
                component: () => import(/* webpackChunkName: 'error' */ '../page/error/404')
            },
            {
                path: '500',
                component: () => import(/* webpackChunkName: 'error' */ '../page/error/500')
            }
        ]
    },
    {
        path: '/login',
        component: () => import(/* webpackChunkName: 'login' */ '../page/login')
    },
    {
        path: '/home',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/doc'),
            },
        ]
    },
    {
        path: '/search',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/search'),
            },
        ]
    },
    {
        path: '/link',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/link'),
            },
        ]
    },
    {
        path: '/trash',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/trash'),
            },
        ]
    },
    {
        path: '/message',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/message'),
            },
        ]
    },
    {
        path: '/linkpreview',
        component: () => import(/* webpackChunkName: 'home' */ '../page/linkpreview')
    },
    {
        path: '/share',
        component: () => import(/* webpackChunkName: 'home' */ '../page/link/LinkPassword')
    },
    {
        path: '/admin',
        component: AdminLayout,
        children: [
            {
                path: 'sys',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/sys'),
            },
            {
                path: 'user',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/user'),
            },
            {
                path: 'dept',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/dept'),
            },
            {
                path: 'usergroup',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/usergroup'),
            },
            {
                path: 'trash',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/trash'),
            },
            {
                path: 'link',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/link'),
            },
            {
                path: 'log',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/log'),
            },
            {
                path: 'backup',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/backup'),
            },
            {
                path: '',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/doc'),
            },
            {
                path: 'roledept',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/roledept'),
            },
            {
                path: 'rolefolder',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/rolefolder'),
            },
            {
                path: 'metadata',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/metadata'),
            },
            {
                path: 'solr',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/solr'),
            },
            {
                path: 'database',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/database'),
            },
            {
                path: 'oafolder',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/oafolder'),
            },
            {
                path: 'oaapi',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/oaapi'),
            },
            {
                path: 'admininfo',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/admininfo'),
            },
            {
                path: 'folderip',
                component: () => import(/* webpackChunkName: 'home' */ '../page/admin/folderip'),
            }
        ]
    },
    {
        path: '*',
        redirect: '/error/404'
    }
]

export default staticRoute

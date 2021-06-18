var data = {
    'login': '@boolean',
    'captcha': "@image('100x40', '#FFFFFF', '@word')",
    'message': '这里是登录提交后错误提示信息@increment',
    'name': '@cname',
    'token': '@guid',
    'operation': 'admin' //admin为管理员菜单
}

export default [{
    path: '/login',
    data: data
}]

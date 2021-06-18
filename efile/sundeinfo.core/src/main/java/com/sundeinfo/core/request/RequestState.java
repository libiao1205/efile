package com.sundeinfo.core.request;

import org.springframework.http.HttpStatus;

public enum RequestState {

    SUCCESS(0, "执行成功"),

    ERROR(1,"服务器发生未知异常"),

    IP_ERROR(10,"该IP无访问权限"),

    ERROR400(400,"错误的请求"),
    ERROR401(401,"未经授权的访问"),
    ERROR403(403,"禁止访问"),
    ERROR404(404,"没有找到资源"),
    ERROR405(405,"方法不允许"),
    ERROR406(406,"不可接受"),
    ERROR407(407,"需要代理身份验证"),
    ERROR408(408,"请求超时"),
    ERROR409(409,"指令冲突"),
    ERROR500(500,"内部服务器错误"),
    ERROR501(501,"未实现"),
    ERROR503(503,"服务不可用"),

    PARAM_CHECK_CONFIG_ERROR(1000, "参数校验配置异常"),
    PARAM_USELESS(1001, "参数无效"),
    PARAM_NULL(1002, "必须参数为空"),
    PARAM_USELESS_USERNAME_PASSWORD(1010, "用户名或者密码不正确"),
    PARAM_USELESS_USERNAME(1011, "用户名已经存在"),
    PARAM_USELESS_RANDOM(1012, "有相同的外链RANDOM"),
    PARAM_USELESS_ADMIN(1013, "已经是系统管理员用户"),
    PARAM_USELESS_ADMIN_FOLDER(1014, "对当前目录已有管理权限"),
    PSD_FORMAT_MISTAKE(10015,"密码格式不正确"),
    ACCOUNT_LOCK(10016,"账号已被锁定，请联系管理员重置密码"),
    PARAM_SCHEMA_ERROR(1101,"数据表检查异常"),

    PARAM_USELESS_PARENT_DEPT(1201, "不能把自己或者自己的子部门设为上级部门"),

    PARAM_PERMISSION_NULL(1301, "权限列表为空"),
    PARAM_PERMISSION_NULL_MEMBER(1302, "权限没有指定用户"),
    PARAM_PERMISSION_ERROR_TYPE(1303, "权限类型设定错误"),
    PARAM_PERMISSION_MULTIPLE(1304, "指定资源已有特定权限设定，不能新增"),
    PARAM_PERMISSION_NULL_ROLE(1305, "没有找到相关角色"),

    PARAM_LINK_NULL(1401,"外链错误或者外链密码错误"),
    PARAM_LINK_EXPIRE(1402,"外链已经失效"),
    PARAM_LINK_DELETE(1403,"外链已经被删除"),

    REQUEST_USELESS(2001, "请求无效"),
    REQUEST_ILLEGAL(2002, "请求非法"),
    REQUEST_PASSWORD_NULL(2003, "需要访问密码"),
    REQUEST_PASSWORD_USELESS(2004, "无效的访问密码"),
    REQUEST_DATABASE_NOT_CONNECT(2005, "该数据库无法连接"),

    TOKEN_ERROR(3001,"令牌检查异常"),
    TOKEN_IMPL_ERROR(3002,"令牌校验方式异常"),

    DB_INSERT_ERROR(4001,"数据新建失败"),
    DB_UPDATE_ERROR(4002,"数据更新失败"),
    DB_DELETE_ERROR(4003,"数据删除失败"),
    DB_DELETE_ERROR_OWN(4004,"不能删除本账号的管理员权限"),
    DB_BACKUP_ERROR(4005,"数据备份失败"),
    DB_BACKUP_SUCCESS(4006,"数据备份成功"),
    FILE_INSERT_ERROR(4101,"文件保存失败"),

    RELAY_SETTING_REPEAT(5001,"导入配置设定重复"),
    RELAY_USER_NULL(5002,"没有找到OA系统对应的用户"),
    RELAY_DEPT_NULL(5003,"没有找到OA系统对应的部门"),
    RELAY_KIND_NULL(5004,"没有在m_config中找到OA系统对应的workflow的文档类型配置"),
    RELAY_SETTING_ERROR(5005,"FolderSetting的配置异常，没有符合条件的配置或者找到多个配置"),
    RELAY_SETTING_FOLDER_ERROR(5006,"文件夹配置异常，已配置的文件夹没有找到"),
    RELAY_SETTING_CREATE_FOLDER_ERROR(5007,"文件夹新建失败"),

    RELAY_SETTING_USER_OFF(5101,"服务器已经关闭了用户同步服务"),
    RELAY_SETTING_DEPARTMENT_OFF(5102,"服务器已经关闭了部门同步服务"),
    RELAY_SETTING_DOCUMENT_OFF(5103,"服务器已经关闭了部门同步服务"),

    FILE_STATUS_DELETE(6001,"文件已被删除"),

    PLACE_OTHER(8001,"登录端末不同"),

    NO_IMPLEMENTED(9999,"未实装方法");

    private final int id;

    private final String value;

    RequestState(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int id() {
        return this.id;
    }

    public String value() {
        return this.value;
    }


    public static HttpStatus valueOfHttpStatus(RequestState state){
        switch (state){
            case ERROR:
            case ERROR500:
            case TOKEN_IMPL_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            case ERROR400:
                return HttpStatus.BAD_REQUEST;
            case ERROR401:
            case TOKEN_ERROR:
                return HttpStatus.UNAUTHORIZED;
            case ERROR403:
            case REQUEST_ILLEGAL:
                return HttpStatus.FORBIDDEN;
            case ERROR404:
                return HttpStatus.NOT_FOUND;
            case ERROR405:
                return HttpStatus.METHOD_NOT_ALLOWED;
            case ERROR406:
                return HttpStatus.NOT_ACCEPTABLE;
            case ERROR407:
                return HttpStatus.PROXY_AUTHENTICATION_REQUIRED;
            case ERROR408:
                return HttpStatus.REQUEST_TIMEOUT;
            case ERROR409:
                return HttpStatus.CONFLICT;
            case ERROR501:
            case NO_IMPLEMENTED:
                return HttpStatus.NOT_IMPLEMENTED;
            case ERROR503:
                return HttpStatus.SERVICE_UNAVAILABLE;
            default:
                return HttpStatus.OK;
        }
    }
}

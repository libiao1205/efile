package com.sundeinfo.core.request;


public interface PostCheckResultCallback {

    /** 获取校验信息 */
    String getCheckMessage(RequestState state);

    /** 获取校验信息 */
    String getInvokeMessage(RequestState state);

    /** 参数检查 */
    RequestState doCheck() throws Exception;

    /** 执行Post请求 */
    RequestState invoke() throws Exception;
}

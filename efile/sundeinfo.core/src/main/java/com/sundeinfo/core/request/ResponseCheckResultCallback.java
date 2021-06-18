package com.sundeinfo.core.request;

public interface ResponseCheckResultCallback<T> extends PostCallback{

    /** 获取校验信息 */
    String getCheckMessage(RequestState state);

    /** 获取校验信息 */
    String getInvokeMessage(RequestState state);

    /** 获取Response对象 */
    T response() throws Exception;
}

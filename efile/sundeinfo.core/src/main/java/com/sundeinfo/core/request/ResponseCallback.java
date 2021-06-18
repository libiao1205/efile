package com.sundeinfo.core.request;

public interface ResponseCallback<T> extends PostCallback{
    /** 获取Response对象 */
    T response() throws Exception;
}

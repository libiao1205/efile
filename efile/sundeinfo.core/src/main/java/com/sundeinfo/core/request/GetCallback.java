package com.sundeinfo.core.request;


public interface GetCallback<T> {
    /** 参数检查 */
    RequestState doCheck() throws IllegalAccessException, InstantiationException, NoSuchFieldException, Exception;

    /** 获取Get对象 */
    T invoke() throws Exception;
}

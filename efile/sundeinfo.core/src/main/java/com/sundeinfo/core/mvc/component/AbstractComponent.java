package com.sundeinfo.core.mvc.component;

//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractComponent<T> implements Component<T> {

    //log处理
    protected Logger logger;
    private Class<T> clazz;


    @SuppressWarnings("unchecked")
    protected AbstractComponent() {
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        logger = LoggerFactory.getLogger(clazz);
    }
}

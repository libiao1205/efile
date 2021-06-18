package com.sundeinfo.core.log;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ActionLog {
    //Log处理类
    Class execClass() default LogInfo.class;
}

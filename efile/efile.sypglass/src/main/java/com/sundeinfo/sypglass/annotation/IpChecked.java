package com.sundeinfo.sypglass.annotation;

import com.sundeinfo.sypglass.define.ResourceType;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface IpChecked {
    ResourceType value() default ResourceType.UNKNOWN;
}


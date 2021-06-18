package com.sundeinfo.core.permission;

import com.sundeinfo.core.permission.FunctionType;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Permissionable {

    String dataKey() default "id";

    FunctionType function() default FunctionType.NONE;

}

package com.sundeinfo.sypglass.annotation;

import com.sundeinfo.sypglass.define.MessageType;
import com.sundeinfo.sypglass.define.SypGlass;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface SendMessage {
    int idSeq() default 0;

    String kind() default SypGlass.RESOURCE_TYPE_FILE;

    MessageType type() default MessageType.UNKNOWN;
}

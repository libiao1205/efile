package com.sundeinfo.core.token;

import com.sundeinfo.core.token.TokenType;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Token {

    TokenType[] types() default {TokenType.ALL};

}

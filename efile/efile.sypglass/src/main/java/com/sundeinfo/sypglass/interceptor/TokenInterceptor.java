package com.sundeinfo.sypglass.interceptor;


import com.sundeinfo.core.token.Token;
import com.sundeinfo.security.token.AbstractTokenInterceptor;
import com.sundeinfo.security.utility.TokenResource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenInterceptor extends AbstractTokenInterceptor<TokenInterceptor> {

    @Autowired
    private TokenResource tokenResource;

    @Override
    protected String requestHeader() {
        return tokenResource.getRequestheader();
    }

    @Override
    protected String tokenHeader() {
        return tokenResource.getTokenheader();
    }

    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.core.token.Token)")
    public void tokenCheckPointcut(){}

    @Around(value = "tokenCheckPointcut() && @annotation(token)")
    public Object interceptor(ProceedingJoinPoint joinPoint, Token token) throws Throwable{
        return super.invoke(joinPoint,token);
    }


}

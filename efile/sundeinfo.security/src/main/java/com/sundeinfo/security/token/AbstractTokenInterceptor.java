package com.sundeinfo.security.token;


import com.sundeinfo.core.token.Token;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.ResultFactory;
import com.sundeinfo.security.strategy.GeneralMetadataSourceService;
import com.sundeinfo.security.strategy.GeneralAccessDecisionManager;
import com.sundeinfo.security.strategy.token.TokenUtil;
import com.sundeinfo.security.utility.AuthenticationGetter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public abstract class AbstractTokenInterceptor<T> extends AbstractComponent<T> {

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private GeneralMetadataSourceService generalMetadataSourceService;

    @Autowired
    private GeneralAccessDecisionManager generalAccessDecisionManager;

    @Autowired
    private TokenUtil tokenUtil;

    protected abstract String requestHeader();

    protected abstract String tokenHeader();


    public Object invoke(ProceedingJoinPoint joinPoint, Token token) throws Throwable {
        if (token == null || token.types() == null || token.types().length == 0) {
            String log = "tokenCheck参数异常: Check参数没有配置";
            if (logger.isDebugEnabled()){logger.debug(log);}
            return ResultFactory.getDataMessage(RequestState.TOKEN_IMPL_ERROR);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader(requestHeader());

        if (authHeader == null || !authHeader.startsWith(tokenHeader())) {
            return ResultFactory.getDataMessage(RequestState.TOKEN_ERROR);
        }
//        final String authToken = authHeader.substring(tokenHeader().length());
//        String username = tokenUtil.getUsernameFromToken(authToken);
//
//        Authentication authentication = authenticationGetter.getAuthentication(username);
//        Collection<ConfigAttribute> configAttributes = generalMetadataSourceService.getAttributes(request);
//
//        try{
//            generalAccessDecisionManager.decide(authentication,request,configAttributes);
//        }catch (Exception e){
//            return ResultFactory.getDataMessage(RequestState.REQUEST_ILLEGAL);
//        }
        return joinPoint.proceed();
    }
}

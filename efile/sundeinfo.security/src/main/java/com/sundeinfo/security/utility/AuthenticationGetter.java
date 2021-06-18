package com.sundeinfo.security.utility;


import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.strategy.AnyUserService;
import com.sundeinfo.security.strategy.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationGetter extends AbstractComponent<AuthenticationGetter> {

    @Autowired
    private TokenResource tokenResource;


    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private AnyUserService anyUserService;


    public AnyUser getAnyUser(){
        String token = getToken();
        if (StringUtils.isEmpty(token))return null;
        String username = tokenUtil.getUsernameFromToken(token);
        return (AnyUser)getAuthentication(username).getPrincipal();
    }

    private String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authHeader = request.getHeader(tokenResource.getRequestheader());
        if (authHeader != null && authHeader.startsWith(tokenResource.getTokenheader())) {
            return authHeader.substring(tokenResource.getTokenheader().length());
        }
        return "";
    }


    public Authentication getAuthentication(String username){
        Assert.hasText(username,"用户名为空");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null){
            AnyUser anyUser = anyUserService.getAnyUser(username);
            setAuthentication(anyUser);
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Authentication getAuthentication(String authToken,String username){
        Assert.hasText(authToken,"令牌为空");
        Assert.hasText(username,"用户名为空");

        if (SecurityContextHolder.getContext().getAuthentication() == null){
            AnyUser anyUser = anyUserService.getAnyUser(username);
            if (tokenUtil.validateToken(authToken, anyUser)) {
                setAuthentication(anyUser);
            }
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private void setAuthentication(AnyUser anyUser){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(anyUser, null, anyUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        logger.info("authenticated user " + anyUser.getUsername() + ", setting security context");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

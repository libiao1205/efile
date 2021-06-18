package com.sundeinfo.security.strategy.token;

import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.security.utility.TokenResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenResource tokenResource;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(tokenResource.getRequestheader());
        if (authHeader != null && authHeader.startsWith(tokenResource.getTokenheader())) {
            final String authToken = authHeader.substring(tokenResource.getTokenheader().length());
            String username = tokenUtil.getUsernameFromToken(authToken);
            if (StringUtils.isNotBlank(username)){
                logger.info("-----------check authentication----------- username: " + username);
                Authentication authentication = authenticationGetter.getAuthentication(authToken,username);
            }
        }
        chain.doFilter(request, response);
    }
}


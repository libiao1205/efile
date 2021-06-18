package com.sundeinfo.security.configuration;

import com.sundeinfo.security.strategy.GeneralAccessDecisionManager;
import com.sundeinfo.security.strategy.session.SessionSecurityExpressionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityBeanRegistry {

//    @Autowired
//    private FilterInvocationSecurityMetadataSource securityMetadataSource;
//
//    @Bean
//    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
//        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
//        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
//        filterSecurityInterceptor.afterPropertiesSet();
//        return filterSecurityInterceptor;
//    }
//
//    @Bean
//    public AccessDecisionManager accessDecisionManager(){
//        //return new JwtAccessDecisionManager();
//        return new GeneralAccessDecisionManager();
//    }
//
//    @Bean
//    public AffirmativeBased affirmativeBased(){
//        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
//        WebExpressionVoter expressionVoter = webExpressionVoter();
//        decisionVoters.add(expressionVoter);
//        return new AffirmativeBased(decisionVoters);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean("webExpressionVoter")
    public WebExpressionVoter webExpressionVoter(){
        WebExpressionVoter voter = new WebExpressionVoter();
        voter.setExpressionHandler(securityExpressionHandler());
        return voter;
    }

    @Bean("securityExpressionHandler")
    public SessionSecurityExpressionHandler securityExpressionHandler(){
        return new SessionSecurityExpressionHandler();
    }

}
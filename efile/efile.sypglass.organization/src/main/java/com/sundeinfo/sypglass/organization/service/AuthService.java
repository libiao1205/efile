package com.sundeinfo.sypglass.organization.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.domain.UserDomain;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.model.GeneralUserAuthority;
import com.sundeinfo.security.strategy.token.TokenUtil;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.security.utility.AuthorityCreater;
import com.sundeinfo.security.utility.TokenResource;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("AuthService")
public class AuthService extends AbstractService<AuthService,AnyUser> {

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenResource tokenResource;

    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AnyUser anyUser = (AnyUser)authenticationGetter.getAuthentication(username).getPrincipal();
        final String token = tokenUtil.generateToken(anyUser);
        return token;
    }

    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenResource.getTokenheader().length());
        String username = tokenUtil.getUsernameFromToken(token);
        AnyUser anyUser = (AnyUser)authenticationGetter.getAuthentication(username).getPrincipal();
        if (tokenUtil.canTokenBeRefreshed(token, anyUser.getUser().getLastPasswordResetDate())){
            return tokenUtil.refreshToken(token);
        }
        return null;
    }

    public UserDto register(UserDto userToAdd) throws IllegalAccessException, InstantiationException {
        final String username = userToAdd.getUsername();
        if(userService.findByUsername(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userService.save(userToAdd,userToAdd.getDepartments());
        return userToAdd;
    }
}
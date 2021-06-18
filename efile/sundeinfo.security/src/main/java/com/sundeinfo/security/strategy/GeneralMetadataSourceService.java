package com.sundeinfo.security.strategy;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.model.GeneralConfigAttribute;
import com.sundeinfo.security.domain.PermissionDomain;
import com.sundeinfo.security.service.*;
import com.sundeinfo.security.utility.RequestResourceMatcher;
import com.sundeinfo.security.utility.AuthorityCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class GeneralMetadataSourceService extends AbstractService<GeneralMetadataSourceService,ConfigAttribute> implements FilterInvocationSecurityMetadataSource {

    private static final String MATCH_ALL = "**";

    private PermissionDomainService permissionService;

    private HashMap<String, Collection<ConfigAttribute>> map =null;

    public void setServices(PermissionDomainService permissionService){
        this.permissionService = permissionService;
    }

    private void prepareResourceDefine(){
        if(map ==null) {
            try {
                loadResourceDefine();
            } catch (IllegalAccessException | InstantiationException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadResourceDefine() throws IllegalAccessException, InstantiationException,NoSuchFieldException {
        map = new HashMap<>();
        List<PermissionDomain> permissions = permissionService.findAll();
        for(PermissionDomain permission : permissions) {
            setConfigAttributeToMap(new GeneralConfigAttribute(AuthorityCreater.getActionAuthority(permission),permission));
        }
    }

    private void setConfigAttributeToMap(GeneralConfigAttribute generalConfigAttribute){
        if (map.containsKey(generalConfigAttribute.getPermission().getCode())){
            map.get(generalConfigAttribute.getPermission().getCode()).add(generalConfigAttribute);
        }else {
            Collection<ConfigAttribute> configAttributeCollection = new ArrayList<>();
            configAttributeCollection.add(generalConfigAttribute);
            map.put(generalConfigAttribute.getPermission().getCode(), configAttributeCollection);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)  throws IllegalArgumentException  {
        prepareResourceDefine();
        HttpServletRequest request;
        RequestResourceMatcher matcher;

        if (object instanceof FilterInvocation){
            request = ((FilterInvocation) object).getHttpRequest();
        }else if(object instanceof HttpServletRequest){
            request = (HttpServletRequest)object;
        }else{
            logger.error("----------------传入的参数不是Request----------------");
            throw new IllegalArgumentException();
        }

        for (Collection<ConfigAttribute> attributes : map.values()) {
            for(ConfigAttribute attribute : attributes){
                GeneralConfigAttribute generalConfigAttribute = (GeneralConfigAttribute)attribute;
                matcher = new RequestResourceMatcher(String.format("%s:%s/%s",
                        generalConfigAttribute.getPermission().getKind(),
                        generalConfigAttribute.getPermission().getAction(),
                        MATCH_ALL));
                if (matcher.matches(request)) {
                    return attributes;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

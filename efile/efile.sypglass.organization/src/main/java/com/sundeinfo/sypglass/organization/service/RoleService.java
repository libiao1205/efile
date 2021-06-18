package com.sundeinfo.sypglass.organization.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.service.RoleDomainService;
import com.sundeinfo.sypglass.organization.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("RoleService")
public class RoleService extends AbstractService<RoleService,RoleDto> implements RoleDomainService<RoleService,RoleDto> {

    @Override
    public List<RoleDto> findCompleteRolesByUserId(long id){
        return new ArrayList<>();
    }
}
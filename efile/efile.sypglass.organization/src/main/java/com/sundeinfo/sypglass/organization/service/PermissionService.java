package com.sundeinfo.sypglass.organization.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.service.PermissionDomainService;
import com.sundeinfo.sypglass.organization.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("PermissionService")
public class PermissionService extends AbstractService<PermissionService,PermissionDto> implements PermissionDomainService<PermissionService,PermissionDto> {
    @Override
    public List<PermissionDto> findAll() {
        return new ArrayList<>();
    }
}

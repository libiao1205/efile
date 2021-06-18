package com.sundeinfo.sypglass.organization.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.security.service.AdminDomainService;
import com.sundeinfo.sypglass.organization.dto.AdminDto;
import org.springframework.stereotype.Service;

@Service("AdminService")
public class AdminService extends AbstractService<AdminService,AdminDto> implements AdminDomainService<AdminService,AdminDto> {

    public boolean isSysAdmin(long id){
        return false;
    }

    @Override
    public AdminDto findByUser(long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return null;
    }
}

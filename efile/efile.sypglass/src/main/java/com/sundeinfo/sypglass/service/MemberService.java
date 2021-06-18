package com.sundeinfo.sypglass.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.mapper.OrganizationMapper;
import com.sundeinfo.sypglass.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrganizationService")
public class MemberService extends AbstractService<MemberService,Member> {

    @Autowired
    private OrganizationMapper organizationMapper;

    public List<Member> getMemberByDeptId(long id){
        return organizationMapper.selectMemberByParentid(id);
    }

}
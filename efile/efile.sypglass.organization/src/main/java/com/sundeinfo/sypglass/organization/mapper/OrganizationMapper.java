package com.sundeinfo.sypglass.organization.mapper;

import com.sundeinfo.sypglass.organization.model.Department;
import com.sundeinfo.sypglass.organization.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrganizationMapper {

    /*取得部门下属部门和员工列表*/
    List<Member> selectMemberByParentid(@Param("id") long id);

    /*向下取得部门列表*/
    List<Department> selectDeptAndChildrenById(@Param("id") long id);

    /*向上取得部门列表*/
    List<Department> selectDeptAndParentsById(@Param("id") long id);
}

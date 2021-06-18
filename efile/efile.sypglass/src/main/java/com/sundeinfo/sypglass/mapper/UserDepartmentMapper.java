package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.UserDepartment;
import com.sundeinfo.sypglass.model.UserDepartmentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDepartmentMapper {
    long countByExample(UserDepartmentExample example);

    int deleteByExample(UserDepartmentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserDepartment record);

    int insertSelective(UserDepartment record);

    List<UserDepartment> selectByExample(UserDepartmentExample example);

    UserDepartment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserDepartment record, @Param("example") UserDepartmentExample example);

    int updateByExample(@Param("record") UserDepartment record, @Param("example") UserDepartmentExample example);

    int updateByPrimaryKeySelective(UserDepartment record);

    int updateByPrimaryKey(UserDepartment record);
}
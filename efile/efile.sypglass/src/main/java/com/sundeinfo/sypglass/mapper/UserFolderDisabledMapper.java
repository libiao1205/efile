package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.UserFolderDisabled;
import com.sundeinfo.sypglass.model.UserFolderDisabledExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserFolderDisabledMapper {
    long countByExample(UserFolderDisabledExample example);

    int deleteByExample(UserFolderDisabledExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFolderDisabled record);

    int insertSelective(UserFolderDisabled record);

    List<UserFolderDisabled> selectByExample(UserFolderDisabledExample example);

    UserFolderDisabled selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFolderDisabled record, @Param("example") UserFolderDisabledExample example);

    int updateByExample(@Param("record") UserFolderDisabled record, @Param("example") UserFolderDisabledExample example);

    int updateByPrimaryKeySelective(UserFolderDisabled record);

    int updateByPrimaryKey(UserFolderDisabled record);
}
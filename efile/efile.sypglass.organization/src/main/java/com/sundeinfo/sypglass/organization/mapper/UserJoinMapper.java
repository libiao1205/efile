package com.sundeinfo.sypglass.organization.mapper;

import com.sundeinfo.sypglass.organization.model.UserJoin;
import com.sundeinfo.sypglass.organization.model.UserJoinExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserJoinMapper {
    long countByExample(UserJoinExample example);

    int deleteByExample(UserJoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserJoin record);

    int insertSelective(UserJoin record);

    List<UserJoin> selectByExample(UserJoinExample example);

    UserJoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserJoin record, @Param("example") UserJoinExample example);

    int updateByExample(@Param("record") UserJoin record, @Param("example") UserJoinExample example);

    int updateByPrimaryKeySelective(UserJoin record);

    int updateByPrimaryKey(UserJoin record);
}
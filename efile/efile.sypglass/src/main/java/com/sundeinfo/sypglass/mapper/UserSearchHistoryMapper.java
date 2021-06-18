package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.UserSearchHistory;
import com.sundeinfo.sypglass.model.UserSearchHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserSearchHistoryMapper {
    long countByExample(UserSearchHistoryExample example);

    int deleteByExample(UserSearchHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserSearchHistory record);

    int insertSelective(UserSearchHistory record);

    List<UserSearchHistory> selectByExample(UserSearchHistoryExample example);

    UserSearchHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserSearchHistory record, @Param("example") UserSearchHistoryExample example);

    int updateByExample(@Param("record") UserSearchHistory record, @Param("example") UserSearchHistoryExample example);

    int updateByPrimaryKeySelective(UserSearchHistory record);

    int updateByPrimaryKey(UserSearchHistory record);
}
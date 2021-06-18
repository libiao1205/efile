package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.UserFolderSort;
import com.sundeinfo.sypglass.model.UserFolderSortExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserFolderSortMapper {
    long countByExample(UserFolderSortExample example);

    int deleteByExample(UserFolderSortExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFolderSort record);

    int insertSelective(UserFolderSort record);

    List<UserFolderSort> selectByExample(UserFolderSortExample example);

    UserFolderSort selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFolderSort record, @Param("example") UserFolderSortExample example);

    int updateByExample(@Param("record") UserFolderSort record, @Param("example") UserFolderSortExample example);

    int updateByPrimaryKeySelective(UserFolderSort record);

    int updateByPrimaryKey(UserFolderSort record);
}
package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FolderSetting;
import com.sundeinfo.sypglass.model.FolderSettingExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FolderSettingMapper {
    long countByExample(FolderSettingExample example);

    int deleteByExample(FolderSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FolderSetting record);

    int insertSelective(FolderSetting record);

    List<FolderSetting> selectByExample(FolderSettingExample example);

    FolderSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FolderSetting record, @Param("example") FolderSettingExample example);

    int updateByExample(@Param("record") FolderSetting record, @Param("example") FolderSettingExample example);

    int updateByPrimaryKeySelective(FolderSetting record);

    int updateByPrimaryKey(FolderSetting record);
}
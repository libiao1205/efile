package com.sundeinfo.sypglass.solr.mapper;

import com.sundeinfo.sypglass.solr.model.QuartzSetting;
import com.sundeinfo.sypglass.solr.model.QuartzSettingExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuartzSettingMapper {
    long countByExample(QuartzSettingExample example);

    int deleteByExample(QuartzSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QuartzSetting record);

    int insertSelective(QuartzSetting record);

    List<QuartzSetting> selectByExample(QuartzSettingExample example);

    QuartzSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QuartzSetting record, @Param("example") QuartzSettingExample example);

    int updateByExample(@Param("record") QuartzSetting record, @Param("example") QuartzSettingExample example);

    int updateByPrimaryKeySelective(QuartzSetting record);

    int updateByPrimaryKey(QuartzSetting record);
}
package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.ProcessSetting;
import com.sundeinfo.sypglass.relay.model.relay.ProcessSettingExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProcessSettingMapper {
    long countByExample(ProcessSettingExample example);

    int deleteByExample(ProcessSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProcessSetting record);

    int insertSelective(ProcessSetting record);

    List<ProcessSetting> selectByExample(ProcessSettingExample example);

    ProcessSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProcessSetting record, @Param("example") ProcessSettingExample example);

    int updateByExample(@Param("record") ProcessSetting record, @Param("example") ProcessSettingExample example);

    int updateByPrimaryKeySelective(ProcessSetting record);

    int updateByPrimaryKey(ProcessSetting record);
}
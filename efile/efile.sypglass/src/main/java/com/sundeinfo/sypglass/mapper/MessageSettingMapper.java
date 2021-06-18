package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.MessageSetting;
import com.sundeinfo.sypglass.model.MessageSettingExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageSettingMapper {
    long countByExample(MessageSettingExample example);

    int deleteByExample(MessageSettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageSetting record);

    int insertSelective(MessageSetting record);

    List<MessageSetting> selectByExample(MessageSettingExample example);

    MessageSetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageSetting record, @Param("example") MessageSettingExample example);

    int updateByExample(@Param("record") MessageSetting record, @Param("example") MessageSettingExample example);

    int updateByPrimaryKeySelective(MessageSetting record);

    int updateByPrimaryKey(MessageSetting record);
}
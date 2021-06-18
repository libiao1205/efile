package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.UserSettingDetail;
import com.sundeinfo.sypglass.model.UserSettingDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserSettingDetailMapper {
    long countByExample(UserSettingDetailExample example);

    int deleteByExample(UserSettingDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserSettingDetail record);

    int insertSelective(UserSettingDetail record);

    List<UserSettingDetail> selectByExample(UserSettingDetailExample example);

    UserSettingDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserSettingDetail record, @Param("example") UserSettingDetailExample example);

    int updateByExample(@Param("record") UserSettingDetail record, @Param("example") UserSettingDetailExample example);

    int updateByPrimaryKeySelective(UserSettingDetail record);

    int updateByPrimaryKey(UserSettingDetail record);
}
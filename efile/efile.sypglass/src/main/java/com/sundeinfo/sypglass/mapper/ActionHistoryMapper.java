package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.ActionHistory;
import com.sundeinfo.sypglass.model.ActionHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ActionHistoryMapper {
    long countByExample(ActionHistoryExample example);

    int deleteByExample(ActionHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ActionHistory record);

    int insertSelective(ActionHistory record);

    List<ActionHistory> selectByExample(ActionHistoryExample example);

    ActionHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ActionHistory record, @Param("example") ActionHistoryExample example);

    int updateByExample(@Param("record") ActionHistory record, @Param("example") ActionHistoryExample example);

    int updateByPrimaryKeySelective(ActionHistory record);

    int updateByPrimaryKey(ActionHistory record);
}
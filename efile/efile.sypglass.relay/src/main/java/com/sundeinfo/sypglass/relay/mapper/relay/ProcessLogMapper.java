package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.ProcessLog;
import com.sundeinfo.sypglass.relay.model.relay.ProcessLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProcessLogMapper {
    long countByExample(ProcessLogExample example);

    int deleteByExample(ProcessLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProcessLog record);

    int insertSelective(ProcessLog record);

    List<ProcessLog> selectByExample(ProcessLogExample example);

    ProcessLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProcessLog record, @Param("example") ProcessLogExample example);

    int updateByExample(@Param("record") ProcessLog record, @Param("example") ProcessLogExample example);

    int updateByPrimaryKeySelective(ProcessLog record);

    int updateByPrimaryKey(ProcessLog record);
}
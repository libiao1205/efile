package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.ProcessStatus;
import com.sundeinfo.sypglass.relay.model.relay.ProcessStatusExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProcessStatusMapper {
    long countByExample(ProcessStatusExample example);

    int deleteByExample(ProcessStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProcessStatus record);

    int insertSelective(ProcessStatus record);

    List<ProcessStatus> selectByExample(ProcessStatusExample example);

    ProcessStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProcessStatus record, @Param("example") ProcessStatusExample example);

    int updateByExample(@Param("record") ProcessStatus record, @Param("example") ProcessStatusExample example);

    int updateByPrimaryKeySelective(ProcessStatus record);

    int updateByPrimaryKey(ProcessStatus record);
}
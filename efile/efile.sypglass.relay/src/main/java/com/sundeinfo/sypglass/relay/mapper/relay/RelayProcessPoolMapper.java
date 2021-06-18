package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.RelayProcessPool;
import com.sundeinfo.sypglass.relay.model.relay.RelayProcessPoolExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RelayProcessPoolMapper {
    long countByExample(RelayProcessPoolExample example);

    int deleteByExample(RelayProcessPoolExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RelayProcessPool record);

    int insertSelective(RelayProcessPool record);

    List<RelayProcessPool> selectByExample(RelayProcessPoolExample example);

    RelayProcessPool selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RelayProcessPool record, @Param("example") RelayProcessPoolExample example);

    int updateByExample(@Param("record") RelayProcessPool record, @Param("example") RelayProcessPoolExample example);

    int updateByPrimaryKeySelective(RelayProcessPool record);

    int updateByPrimaryKey(RelayProcessPool record);
}
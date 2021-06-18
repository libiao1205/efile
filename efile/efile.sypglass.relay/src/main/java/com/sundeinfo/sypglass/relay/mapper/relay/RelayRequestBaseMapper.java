package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.RelayRequestBase;
import com.sundeinfo.sypglass.relay.model.relay.RelayRequestBaseExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RelayRequestBaseMapper {
    long countByExample(RelayRequestBaseExample example);

    int deleteByExample(RelayRequestBaseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RelayRequestBase record);

    int insertSelective(RelayRequestBase record);

    List<RelayRequestBase> selectByExample(RelayRequestBaseExample example);

    RelayRequestBase selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RelayRequestBase record, @Param("example") RelayRequestBaseExample example);

    int updateByExample(@Param("record") RelayRequestBase record, @Param("example") RelayRequestBaseExample example);

    int updateByPrimaryKeySelective(RelayRequestBase record);

    int updateByPrimaryKey(RelayRequestBase record);
}
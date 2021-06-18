package com.sundeinfo.sypglass.relay.mapper.relay;

import com.sundeinfo.sypglass.relay.model.relay.OASchema;
import com.sundeinfo.sypglass.relay.model.relay.OASchemaExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OASchemaMapper {
    long countByExample(OASchemaExample example);

    int deleteByExample(OASchemaExample example);

    int insert(OASchema record);

    int insertSelective(OASchema record);

    List<OASchema> selectByExample(OASchemaExample example);

    int updateByExampleSelective(@Param("record") OASchema record, @Param("example") OASchemaExample example);

    int updateByExample(@Param("record") OASchema record, @Param("example") OASchemaExample example);
}
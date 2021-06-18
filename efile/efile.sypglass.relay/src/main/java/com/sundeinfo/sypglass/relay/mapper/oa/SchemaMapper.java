package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.relay.OASchema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SchemaMapper {

    List<OASchema> selectAll();

    List<OASchema> selectByTableName(@Param("name") String name);

}
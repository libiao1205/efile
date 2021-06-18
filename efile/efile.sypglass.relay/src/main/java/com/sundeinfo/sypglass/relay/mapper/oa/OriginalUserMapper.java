package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.oa.HrmResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OriginalUserMapper {

    List<HrmResource> selectAllHrmResource();

    List<HrmResource> selectAllHrmResourceByLastModDate(@Param("lastmoddate") String lastmoddate);

}
package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.oa.FormTableMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FormTableMain145Mapper {

    FormTableMain selectById(@Param("id") int id);

    List<FormTableMain> selectByRequestId(@Param("requestid") int requestid);

}
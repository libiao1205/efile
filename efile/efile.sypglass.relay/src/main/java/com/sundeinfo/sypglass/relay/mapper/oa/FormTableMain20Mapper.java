package com.sundeinfo.sypglass.relay.mapper.oa;

import java.util.List;

import com.sundeinfo.sypglass.relay.model.oa.FormTableMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FormTableMain20Mapper {

    FormTableMain selectById(@Param("id") int id);

    List<FormTableMain> selectByRequestId(@Param("requestid") int requestid);

}
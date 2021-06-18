package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.oa.DocImageFile;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DocImageFileMapper {

    List<DocImageFile> selectDocImageFileByDocId(@Param("docid") int docid);

}
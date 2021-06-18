package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.Metadata;
import com.sundeinfo.sypglass.model.MetadataExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MetadataMapper {
    long countByExample(MetadataExample example);

    int deleteByExample(MetadataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Metadata record);

    int insertSelective(Metadata record);

    List<Metadata> selectByExample(MetadataExample example);

    Metadata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Metadata record, @Param("example") MetadataExample example);

    int updateByExample(@Param("record") Metadata record, @Param("example") MetadataExample example);

    int updateByPrimaryKeySelective(Metadata record);

    int updateByPrimaryKey(Metadata record);
}
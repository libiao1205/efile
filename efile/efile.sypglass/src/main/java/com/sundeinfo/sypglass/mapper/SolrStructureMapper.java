package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.SolrStructure;
import com.sundeinfo.sypglass.model.SolrStructureExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SolrStructureMapper {
    long countByExample(SolrStructureExample example);

    int deleteByExample(SolrStructureExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SolrStructure record);

    int insertSelective(SolrStructure record);

    List<SolrStructure> selectByExample(SolrStructureExample example);

    SolrStructure selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SolrStructure record, @Param("example") SolrStructureExample example);

    int updateByExample(@Param("record") SolrStructure record, @Param("example") SolrStructureExample example);

    int updateByPrimaryKeySelective(SolrStructure record);

    int updateByPrimaryKey(SolrStructure record);
}
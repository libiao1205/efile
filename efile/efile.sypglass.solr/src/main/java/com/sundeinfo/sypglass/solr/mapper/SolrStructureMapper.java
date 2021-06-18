package com.sundeinfo.sypglass.solr.mapper;

import com.sundeinfo.sypglass.solr.model.SolrStructure;
import com.sundeinfo.sypglass.solr.model.SolrStructureExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
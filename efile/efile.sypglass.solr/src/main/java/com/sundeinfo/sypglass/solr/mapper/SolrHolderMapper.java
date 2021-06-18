package com.sundeinfo.sypglass.solr.mapper;

import com.sundeinfo.sypglass.solr.model.SolrHolder;
import com.sundeinfo.sypglass.solr.model.SolrHolderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SolrHolderMapper {
    long countByExample(SolrHolderExample example);

    int deleteByExample(SolrHolderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SolrHolder record);

    int insertSelective(SolrHolder record);

    List<SolrHolder> selectByExample(SolrHolderExample example);

    SolrHolder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SolrHolder record, @Param("example") SolrHolderExample example);

    int updateByExample(@Param("record") SolrHolder record, @Param("example") SolrHolderExample example);

    int updateByPrimaryKeySelective(SolrHolder record);

    int updateByPrimaryKey(SolrHolder record);
}
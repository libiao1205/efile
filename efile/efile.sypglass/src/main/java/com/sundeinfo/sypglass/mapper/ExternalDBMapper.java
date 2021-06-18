package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.ExternalDB;
import com.sundeinfo.sypglass.model.ExternalDBExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExternalDBMapper {
    long countByExample(ExternalDBExample example);

    int deleteByExample(ExternalDBExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExternalDB record);

    int insertSelective(ExternalDB record);

    List<ExternalDB> selectByExample(ExternalDBExample example);

    ExternalDB selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExternalDB record, @Param("example") ExternalDBExample example);

    int updateByExample(@Param("record") ExternalDB record, @Param("example") ExternalDBExample example);

    int updateByPrimaryKeySelective(ExternalDB record);

    int updateByPrimaryKey(ExternalDB record);
}
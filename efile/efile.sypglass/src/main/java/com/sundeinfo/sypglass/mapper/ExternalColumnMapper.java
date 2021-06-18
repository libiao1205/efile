package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.ExternalColumn;
import com.sundeinfo.sypglass.model.ExternalColumnExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExternalColumnMapper {
    long countByExample(ExternalColumnExample example);

    int deleteByExample(ExternalColumnExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExternalColumn record);

    int insertSelective(ExternalColumn record);

    List<ExternalColumn> selectByExample(ExternalColumnExample example);

    ExternalColumn selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExternalColumn record, @Param("example") ExternalColumnExample example);

    int updateByExample(@Param("record") ExternalColumn record, @Param("example") ExternalColumnExample example);

    int updateByPrimaryKeySelective(ExternalColumn record);

    int updateByPrimaryKey(ExternalColumn record);
}
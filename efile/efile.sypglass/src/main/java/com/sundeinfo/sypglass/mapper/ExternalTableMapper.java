package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.ExternalTable;
import com.sundeinfo.sypglass.model.ExternalTableExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ExternalTableMapper {
    long countByExample(ExternalTableExample example);

    int deleteByExample(ExternalTableExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExternalTable record);

    int insertSelective(ExternalTable record);

    List<ExternalTable> selectByExample(ExternalTableExample example);

    ExternalTable selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExternalTable record, @Param("example") ExternalTableExample example);

    int updateByExample(@Param("record") ExternalTable record, @Param("example") ExternalTableExample example);

    int updateByPrimaryKeySelective(ExternalTable record);

    int updateByPrimaryKey(ExternalTable record);
}
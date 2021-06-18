package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.DeptJoin;
import com.sundeinfo.sypglass.model.DeptJoinExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeptJoinMapper {
    long countByExample(DeptJoinExample example);

    int deleteByExample(DeptJoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeptJoin record);

    int insertSelective(DeptJoin record);

    List<DeptJoin> selectByExample(DeptJoinExample example);

    DeptJoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeptJoin record, @Param("example") DeptJoinExample example);

    int updateByExample(@Param("record") DeptJoin record, @Param("example") DeptJoinExample example);

    int updateByPrimaryKeySelective(DeptJoin record);

    int updateByPrimaryKey(DeptJoin record);
}
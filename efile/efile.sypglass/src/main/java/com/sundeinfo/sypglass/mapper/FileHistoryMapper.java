package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FileHistory;
import com.sundeinfo.sypglass.model.FileHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileHistoryMapper {
    long countByExample(FileHistoryExample example);

    int deleteByExample(FileHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileHistory record);

    int insertSelective(FileHistory record);

    List<FileHistory> selectByExample(FileHistoryExample example);

    FileHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileHistory record, @Param("example") FileHistoryExample example);

    int updateByExample(@Param("record") FileHistory record, @Param("example") FileHistoryExample example);

    int updateByPrimaryKeySelective(FileHistory record);

    int updateByPrimaryKey(FileHistory record);
}
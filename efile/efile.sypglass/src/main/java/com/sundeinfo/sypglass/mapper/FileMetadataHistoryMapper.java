package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FileMetadataHistory;
import com.sundeinfo.sypglass.model.FileMetadataHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMetadataHistoryMapper {
    long countByExample(FileMetadataHistoryExample example);

    int deleteByExample(FileMetadataHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileMetadataHistory record);

    int insertSelective(FileMetadataHistory record);

    List<FileMetadataHistory> selectByExample(FileMetadataHistoryExample example);

    FileMetadataHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileMetadataHistory record, @Param("example") FileMetadataHistoryExample example);

    int updateByExample(@Param("record") FileMetadataHistory record, @Param("example") FileMetadataHistoryExample example);

    int updateByPrimaryKeySelective(FileMetadataHistory record);

    int updateByPrimaryKey(FileMetadataHistory record);
}
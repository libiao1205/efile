package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FileMetadata;
import com.sundeinfo.sypglass.model.FileMetadataExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMetadataMapper {
    long countByExample(FileMetadataExample example);

    int deleteByExample(FileMetadataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileMetadata record);

    int insertSelective(FileMetadata record);

    List<FileMetadata> selectByExample(FileMetadataExample example);

    FileMetadata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileMetadata record, @Param("example") FileMetadataExample example);

    int updateByExample(@Param("record") FileMetadata record, @Param("example") FileMetadataExample example);

    int updateByPrimaryKeySelective(FileMetadata record);

    int updateByPrimaryKey(FileMetadata record);
}
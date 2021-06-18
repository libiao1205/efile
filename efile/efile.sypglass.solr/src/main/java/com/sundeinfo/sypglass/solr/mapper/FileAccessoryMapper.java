package com.sundeinfo.sypglass.solr.mapper;

import com.sundeinfo.sypglass.solr.model.FileAccessory;
import com.sundeinfo.sypglass.solr.model.FileAccessoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileAccessoryMapper {
    long countByExample(FileAccessoryExample example);

    int deleteByExample(FileAccessoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileAccessory record);

    int insertSelective(FileAccessory record);

    List<FileAccessory> selectByExample(FileAccessoryExample example);

    FileAccessory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileAccessory record, @Param("example") FileAccessoryExample example);

    int updateByExample(@Param("record") FileAccessory record, @Param("example") FileAccessoryExample example);

    int updateByPrimaryKeySelective(FileAccessory record);

    int updateByPrimaryKey(FileAccessory record);
}
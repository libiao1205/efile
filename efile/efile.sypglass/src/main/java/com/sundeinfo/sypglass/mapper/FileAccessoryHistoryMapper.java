package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FileAccessoryHistory;
import com.sundeinfo.sypglass.model.FileAccessoryHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileAccessoryHistoryMapper {
    long countByExample(FileAccessoryHistoryExample example);

    int deleteByExample(FileAccessoryHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FileAccessoryHistory record);

    int insertSelective(FileAccessoryHistory record);

    List<FileAccessoryHistory> selectByExample(FileAccessoryHistoryExample example);

    FileAccessoryHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FileAccessoryHistory record, @Param("example") FileAccessoryHistoryExample example);

    int updateByExample(@Param("record") FileAccessoryHistory record, @Param("example") FileAccessoryHistoryExample example);

    int updateByPrimaryKeySelective(FileAccessoryHistory record);

    int updateByPrimaryKey(FileAccessoryHistory record);
}
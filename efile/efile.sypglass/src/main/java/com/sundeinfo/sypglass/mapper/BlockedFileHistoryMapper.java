package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.BlockedFileHistory;
import com.sundeinfo.sypglass.model.BlockedFileHistoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlockedFileHistoryMapper {
    long countByExample(BlockedFileHistoryExample example);

    int deleteByExample(BlockedFileHistoryExample example);

    int deleteByPrimaryKey(Long sequence);

    int insert(BlockedFileHistory record);

    int insertSelective(BlockedFileHistory record);

    List<BlockedFileHistory> selectByExample(BlockedFileHistoryExample example);

    BlockedFileHistory selectByPrimaryKey(Long sequence);

    int updateByExampleSelective(@Param("record") BlockedFileHistory record, @Param("example") BlockedFileHistoryExample example);

    int updateByExample(@Param("record") BlockedFileHistory record, @Param("example") BlockedFileHistoryExample example);

    int updateByPrimaryKeySelective(BlockedFileHistory record);

    int updateByPrimaryKey(BlockedFileHistory record);
}
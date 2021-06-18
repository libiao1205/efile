package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.BlockedFile;
import com.sundeinfo.sypglass.model.BlockedFileExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlockedFileMapper {
    long countByExample(BlockedFileExample example);

    int deleteByExample(BlockedFileExample example);

    int deleteByPrimaryKey(Long sequence);

    int insert(BlockedFile record);

    int insertSelective(BlockedFile record);

    List<BlockedFile> selectByExample(BlockedFileExample example);

    BlockedFile selectByPrimaryKey(Long sequence);

    int updateByExampleSelective(@Param("record") BlockedFile record, @Param("example") BlockedFileExample example);

    int updateByExample(@Param("record") BlockedFile record, @Param("example") BlockedFileExample example);

    int updateByPrimaryKeySelective(BlockedFile record);

    int updateByPrimaryKey(BlockedFile record);
}
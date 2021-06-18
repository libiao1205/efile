package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.BlockedFolder;
import com.sundeinfo.sypglass.model.BlockedFolderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlockedFolderMapper {
    long countByExample(BlockedFolderExample example);

    int deleteByExample(BlockedFolderExample example);

    int deleteByPrimaryKey(Long sequence);

    int insert(BlockedFolder record);

    int insertSelective(BlockedFolder record);

    List<BlockedFolder> selectByExample(BlockedFolderExample example);

    BlockedFolder selectByPrimaryKey(Long sequence);

    int updateByExampleSelective(@Param("record") BlockedFolder record, @Param("example") BlockedFolderExample example);

    int updateByExample(@Param("record") BlockedFolder record, @Param("example") BlockedFolderExample example);

    int updateByPrimaryKeySelective(BlockedFolder record);

    int updateByPrimaryKey(BlockedFolder record);
}
package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.BlockedFolderIp;
import com.sundeinfo.sypglass.model.BlockedFolderIpExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlockedFolderIpMapper {
    long countByExample(BlockedFolderIpExample example);

    int deleteByExample(BlockedFolderIpExample example);

    int deleteByPrimaryKey(Long sequence);

    int insert(BlockedFolderIp record);

    int insertSelective(BlockedFolderIp record);

    List<BlockedFolderIp> selectByExample(BlockedFolderIpExample example);

    BlockedFolderIp selectByPrimaryKey(Long sequence);

    int updateByExampleSelective(@Param("record") BlockedFolderIp record, @Param("example") BlockedFolderIpExample example);

    int updateByExample(@Param("record") BlockedFolderIp record, @Param("example") BlockedFolderIpExample example);

    int updateByPrimaryKeySelective(BlockedFolderIp record);

    int updateByPrimaryKey(BlockedFolderIp record);
}
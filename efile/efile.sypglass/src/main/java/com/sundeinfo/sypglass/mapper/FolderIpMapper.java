package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.FolderIp;
import com.sundeinfo.sypglass.model.FolderIpExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FolderIpMapper {
    long countByExample(FolderIpExample example);

    int deleteByExample(FolderIpExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FolderIp record);

    int insertSelective(FolderIp record);

    List<FolderIp> selectByExample(FolderIpExample example);

    FolderIp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FolderIp record, @Param("example") FolderIpExample example);

    int updateByExample(@Param("record") FolderIp record, @Param("example") FolderIpExample example);

    int updateByPrimaryKeySelective(FolderIp record);

    int updateByPrimaryKey(FolderIp record);
}
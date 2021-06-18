package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.Backup;
import com.sundeinfo.sypglass.model.BackupExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BackupMapper {
    long countByExample(BackupExample example);

    int deleteByExample(BackupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Backup record);

    int insertSelective(Backup record);

    List<Backup> selectByExample(BackupExample example);

    Backup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Backup record, @Param("example") BackupExample example);

    int updateByExample(@Param("record") Backup record, @Param("example") BackupExample example);

    int updateByPrimaryKeySelective(Backup record);

    int updateByPrimaryKey(Backup record);
}
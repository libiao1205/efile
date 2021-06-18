package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.AdminFolder;
import com.sundeinfo.sypglass.model.AdminFolderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminFolderMapper {
    long countByExample(AdminFolderExample example);

    int deleteByExample(AdminFolderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdminFolder record);

    int insertSelective(AdminFolder record);

    List<AdminFolder> selectByExample(AdminFolderExample example);

    AdminFolder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdminFolder record, @Param("example") AdminFolderExample example);

    int updateByExample(@Param("record") AdminFolder record, @Param("example") AdminFolderExample example);

    int updateByPrimaryKeySelective(AdminFolder record);

    int updateByPrimaryKey(AdminFolder record);
}
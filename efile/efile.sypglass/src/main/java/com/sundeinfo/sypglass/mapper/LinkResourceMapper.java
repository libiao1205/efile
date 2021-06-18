package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.LinkResource;
import com.sundeinfo.sypglass.model.LinkResourceExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LinkResourceMapper {
    long countByExample(LinkResourceExample example);

    int deleteByExample(LinkResourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LinkResource record);

    int insertSelective(LinkResource record);

    List<LinkResource> selectByExample(LinkResourceExample example);

    LinkResource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LinkResource record, @Param("example") LinkResourceExample example);

    int updateByExample(@Param("record") LinkResource record, @Param("example") LinkResourceExample example);

    int updateByPrimaryKeySelective(LinkResource record);

    int updateByPrimaryKey(LinkResource record);
}
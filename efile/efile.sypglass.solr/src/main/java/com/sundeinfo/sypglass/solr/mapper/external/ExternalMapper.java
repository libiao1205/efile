package com.sundeinfo.sypglass.solr.mapper.external;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ExternalMapper {

    List<Map<String, Object>> selectSql(@Param("tableName") String tableName, @Param("fields") String fields, @Param("whereCondition") String whereCondition, @Param("orderCondition") String orderCondition, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    List<Map<String, Object>> selectSqlServer(@Param("tableName") String tableName, @Param("fields") String fields, @Param("whereCondition") String whereCondition, @Param("orderCondition") String orderCondition, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    Integer selectSqlCount(@Param("tableName") String tableName, @Param("whereCondition") String whereCondition);

    Integer insertSql(@Param("tableName") String tableName, @Param("result") String result);

    Integer updateSql(@Param("tableName") String tableName, @Param("result") String result, @Param("whereCondition") String whereCondition);

    Integer deleteSql(@Param("tableName") String tableName, @Param("whereCondition") String whereCondition);
}

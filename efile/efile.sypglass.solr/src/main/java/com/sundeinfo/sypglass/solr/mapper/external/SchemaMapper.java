package com.sundeinfo.sypglass.solr.mapper.external;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SchemaMapper {

    //测试mySql数据库连接
    @Select("select now() as Systemtime;")
    List<Map<String, Object>> selectTimeMySql();

    //测试sqlServer数据库连接
    @Select("select GETDATE() as Systemtime;")
    List<Map<String, Object>> selectTimeSqlServer();

    //获取数据库所有表的信息
    List<Map<String, Object>> selectTable(@Param("dbName") String dbName);

    //获取表的所有字段信息
    List<Map<String, Object>> selectColumn(@Param("dbName") String dbName,@Param("tableName") String tableName);

    //获取数据库所有表的信息
    List<Map<String, Object>> selectTableSqlServer(@Param("dbName") String dbName);

    //获取表的所有字段信息
    List<Map<String, Object>> selectColumnSqlServer(@Param("dbName") String dbName,@Param("tableName") String tableName);
}

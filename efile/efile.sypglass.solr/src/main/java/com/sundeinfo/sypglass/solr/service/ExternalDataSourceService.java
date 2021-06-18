package com.sundeinfo.sypglass.solr.service;

import com.alibaba.fastjson.JSON;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.solr.component.ExternalDataSourceAdapter;
import com.sundeinfo.sypglass.solr.mapper.external.ExternalMapper;
import com.sundeinfo.sypglass.solr.mapper.external.SchemaMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ExternalDataSourceService")
public class ExternalDataSourceService extends AbstractService<ExternalDataSourceService,Map<String, Object>> {

    @Value("${external.mapper.path}")
    private String mapperPath;

    @Value("${external.mapper.package}")
    private String mapperPackage;

    public String getDBTime(String ip,String prot,String username,String password,String driverName,String dbName) throws Exception {
        logger.info("********************************测试外部数据库连接是否正常*************************************");
        ExternalDataSourceAdapter adapter = new ExternalDataSourceAdapter.Builder().setMapperPath(mapperPath).setMapperPackage(mapperPackage).setUrl(this.joinDBUrl(ip,prot,dbName,driverName)).setUsername(username).setPassword(password).setDriver(driverName).build();
        SchemaMapper schemaMapper = adapter.getMapper(SchemaMapper.class);
        String res = "";
        List<Map<String, Object>> list;
        if("com.mysql.jdbc.Driver".equals(driverName)){
            list = schemaMapper.selectTimeMySql();
        }else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverName)){
            list = schemaMapper.selectTimeSqlServer();
        }else{
            list = new ArrayList<>();
        }
        if(list.size() > 0){
            res = list.get(0).get("Systemtime").toString();
        }
        return res;
    }

    public List<Map<String, Object>> getDBSchema(String ip,String prot,String username,String password,String driverName,String dbName) throws Exception {
        logger.info("********************************导入外部数据库表结构*************************************");
        ExternalDataSourceAdapter adapter = new ExternalDataSourceAdapter.Builder().setMapperPath(mapperPath).setMapperPackage(mapperPackage).setUrl(this.joinDBUrl(ip,prot,dbName,driverName)).setUsername(username).setPassword(password).setDriver(driverName).build();
        SchemaMapper schemaMapper = adapter.getMapper(SchemaMapper.class);
        if("com.mysql.jdbc.Driver".equals(driverName)){
            List<Map<String, Object>> tables = schemaMapper.selectTable(dbName);
            for(int i = 0; i < tables.size(); i++){
                Map<String,Object> table = tables.get(i);
                String tableName = table.get("tableName").toString();
                List<Map<String, Object>> columns = schemaMapper.selectColumn(dbName,tableName);
                System.out.println(columns);
                table.put("columns",columns);
            }
            return tables;
        }else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverName)){
            List<Map<String, Object>> tables = schemaMapper.selectTableSqlServer(dbName);
            for(int i = 0; i < tables.size(); i++){
                Map<String,Object> table = tables.get(i);
                String tableName = table.get("tableName").toString();
                List<Map<String, Object>> columns = schemaMapper.selectColumnSqlServer(dbName,tableName);
                table.put("columns",columns);
            }
            return tables;
        }
        return null;
    }

    public String getData(String ip,String prot,String username,String password,String driverName,String dbName,String tableName,Integer pageNum,Integer pageSize,String orderCondition)throws Exception {
        logger.info("********************************查询外部数据库表内容*************************************");
        ExternalDataSourceAdapter adapter = new ExternalDataSourceAdapter.Builder().setMapperPath(mapperPath).setMapperPackage(mapperPackage).setUrl(this.joinDBUrl(ip,prot,dbName,driverName)).setUsername(username).setPassword(password).setDriver(driverName).build();
        ExternalMapper externalMapper = adapter.getMapper(ExternalMapper.class);
        int count = externalMapper.selectSqlCount(tableName,"");
        Map<String, Object> map = new HashMap();
        List<Map<String, Object>> rows = null;
        if("com.mysql.jdbc.Driver".equals(driverName)){
            rows = externalMapper.selectSql(tableName, "*", "", "",(pageNum-1) * pageSize,pageSize);
        }else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverName)){
            rows = externalMapper.selectSqlServer(tableName, "*", "", orderCondition,pageNum * pageSize,pageSize);
        }
        map.put("rows",rows);
        map.put("count",count);
        String str = JSON.toJSONString(map); //此行转换
        return str;
    }
    private String joinDBUrl(String ip,String prot,String dbName,String driverName){
        if("com.mysql.jdbc.Driver".equals(driverName)){
            return "jdbc:mysql://" + ip + ":" + prot + "/" + dbName + "?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        }else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverName)){
            return "jdbc:sqlserver://" + ip + ":" + prot + ";database=" + dbName;
        }
        return "";
    }
}

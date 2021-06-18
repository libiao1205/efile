package com.sundeinfo.sypglass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.dto.ExternalColumnDto;
import com.sundeinfo.sypglass.dto.ExternalDBDto;
import com.sundeinfo.sypglass.dto.ExternalTableDto;
import com.sundeinfo.sypglass.mapper.ExternalColumnMapper;
import com.sundeinfo.sypglass.mapper.ExternalDBMapper;
import com.sundeinfo.sypglass.mapper.ExternalTableMapper;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("ExternalDataSourceService")
public class ExternalDataSourceService extends AbstractService<ExternalDataSourceService,ExternalTable> {

    @Autowired
    private ExternalDBMapper externalDBMapper;

    @Autowired
    private ExternalTableMapper externalTableMapper;

    @Autowired
    private ExternalColumnMapper externalColumnMapper;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private HttpService httpService;

    @Value("${externalDatabase.testConnect.path}")
    private String externalDatabase_testConnect_url;

    @Value("${externalDatabase.dataInput.path}")
    private String externalDatabase_dataInput_url;

    @Value("${externalDatabase.solrHolder.path}")
    private String externalDatabase_solrHolder_url;


    /**
     * 查询所有的数据库连接
     * @return
     */
    public List<ExternalDBDto> findDBList(String name){
        ExternalDBExample example = new ExternalDBExample();
        ExternalDBExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria.andDbnameLike("%" + name + "%");
        }
        criteria.andStatusEqualTo(1);
        //不是root登录只能查看自己创建的数据源
        if(this.getUserId() != 1){
            criteria.andSyncuserEqualTo(this.getUserId());
        }
        ExternalDBExample.Criteria criteria2 = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria2.andDbnameLike("%" + name + "%");
        }
        criteria2.andStatusEqualTo(9);
        example.or(criteria2);
        List<ExternalDB> externalDBList = externalDBMapper.selectByExample(example);
        List<ExternalDBDto> externalDBDtoList = new ArrayList<>();
        externalDBList.forEach(record ->{
            externalDBDtoList.add(this.dbRecordToDto(record));
        });
        return externalDBDtoList;
    }

    /**
     * 指定id查询数据库连接
     * @param id
     * @return
     */
    public ExternalDBDto findDBById(Long id){
        ExternalDBExample example = new ExternalDBExample();
        ExternalDBExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andStatusNotEqualTo(0);
        List<ExternalDB> externalDBs = externalDBMapper.selectByExample(example);
        ExternalDBDto dto;
        if(externalDBs.size() > 0){
            dto = this.dbRecordToDto(externalDBs.get(0));
        } else {
            dto = new ExternalDBDto();
        }
        return dto;
    }

    /**
     * 指定数据库id查询数据库表列表
     * @param id
     * @return
     */
    public List<ExternalTableDto> findTableListByDBId(Long id){
        ExternalTableExample example = new ExternalTableExample();
        ExternalTableExample.Criteria criteria = example.createCriteria();
        criteria.andDbidEqualTo(id);
        List<ExternalTable> externalTableList = externalTableMapper.selectByExample(example);
        List<ExternalTableDto> externalTableDtoList = new ArrayList<>();
        externalTableList.forEach(record ->{
            externalTableDtoList.add(this.tableRecordToDto(record));
        });
        return externalTableDtoList;
    }

    /**
     * 指定数据表id查询数据表字段列表
     * @param id
     * @return
     */
    public List<ExternalColumnDto> findTableColumnsByTableId(Long id) throws InstantiationException, IllegalAccessException {
        ExternalColumnExample example = new ExternalColumnExample();
        ExternalColumnExample.Criteria criteria = example.createCriteria();
        criteria.andTableidEqualTo(id);
        List<ExternalColumn> externalColumnList = externalColumnMapper.selectByExample(example);
        return ConvertUtils.convertList(externalColumnList,ExternalColumnDto.class);
    }

    /*
    * 根据表名称和dbId查询表字段
    * */
    public List<ExternalColumnDto> findTableColumnsByTableNameDBId(Long dbId,String name) throws InstantiationException, IllegalAccessException {
        ExternalColumnExample example = new ExternalColumnExample();
        ExternalColumnExample.Criteria criteria = example.createCriteria();
        criteria.andDbidEqualTo(dbId);
        criteria.andTablenameEqualTo(name);
        List<ExternalColumn> externalColumnList = externalColumnMapper.selectByExample(example);
        return ConvertUtils.convertList(externalColumnList,ExternalColumnDto.class);
    }

    /**
     * 指定表name查询表内容
     * @param name
     * @return
     */
    public String findTableContent(Long dbId,String name,Integer pageNum,Integer pageSize)throws Exception{
        ExternalDBDto db = this.findDBById(dbId);
        List<ExternalColumnDto> externalColumnDtos = this.findTableColumnsByTableNameDBId(dbId,name);
        String orderCondition = "";
        for(int i = 0; i < externalColumnDtos.size(); i++){
            ExternalColumnDto externalColumnDto = externalColumnDtos.get(i);
            if(externalColumnDto.getColumnname().equalsIgnoreCase("id")){
                orderCondition = "id";
            }
        }
        if("".equals(orderCondition) && externalColumnDtos.size() > 0){
            orderCondition = externalColumnDtos.get(0).getColumnname();
        }
        String dbUrl = this.getDBUrl(externalDatabase_solrHolder_url,db);
        dbUrl = dbUrl + "&tableName="+URLEncoder.encode(name,"utf-8")+"&pageNum="+pageNum+"&pageSize="+pageSize+"&orderCondition="+orderCondition;
        return httpService.doGet(dbUrl);
    }

    public static void main(String[] args) {
        String url = "http://192.168.1.7:8080?145";
        System.out.println(url.substring(url.indexOf("?")));
    }

    /**
     * 测试连接
     * @return
     */
    public String connect(ExternalDBDto db) throws Exception{
        String dbUrl = this.getDBUrl(externalDatabase_testConnect_url,db);
        logger.info("dbUrl"+dbUrl);
        String resultJson = httpService.doGet(dbUrl);
        if(StringUtils.isNotBlank(resultJson)){
            return "success";
        }
        return "failed";
    }

    /**
     * 导入外部数据源结构
     * @param id
     * @return
     */
    public String inputSchema(Long id) throws Exception{
        ExternalDBDto db = this.findDBById(id);
        String dbUrl = this.getDBUrl(externalDatabase_dataInput_url,db);
        Date currDate = new Date();
        String resultJson = httpService.doGet(dbUrl);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode tables = mapper.readTree(resultJson);
        if(tables.get("ctt").size() > 0){
            this.removeData(id);
            tables.get("ctt").forEach(table ->{
                ExternalTable record = new ExternalTable();
                record.setDbid(id);
                record.setDbname(table.get("dbName") == null ? "" : table.get("dbName").toString().replace("\"",""));
                record.setTablename(table.get("tableName") == null ? "" : table.get("tableName").toString().replace("\"",""));
                record.setCreatetime(table.get("createTime") == null ? "" : table.get("createTime").toString());
                record.setDatacount(Long.valueOf(table.get("tableRows") == null ? "0" : table.get("tableRows").toString()));
                record.setComment(table.get("tableComment") == null ? "" : table.get("tableComment").toString().replace("\"",""));
                record.setSynctime(currDate);
                record.setSyncuser(this.getUserId());
                int flag = externalTableMapper.insert(record);
                System.out.println("---------------------->添加表"+flag);
                JsonNode columns = table.get("columns");
                for(int i = 0; i < columns.size(); i++){
                    JsonNode column = columns.get(i);
                    ExternalColumn recordColumn = new ExternalColumn();
                    recordColumn.setDbid(id);
                    recordColumn.setDbname(column.get("dbName") == null ? "" : column.get("dbName").toString().replace("\"",""));
                    recordColumn.setTableid(record.getId());
                    recordColumn.setTablename(column.get("tableName") == null ? "" : column.get("tableName").toString().replace("\"",""));
                    recordColumn.setColumnname(column.get("columnName") == null ? "" : column.get("columnName").toString().replace("\"","") );
                    recordColumn.setDatatype(column.get("dataType") == null ? "" : column.get("dataType").toString().replace("\"",""));
                    recordColumn.setDatatypevalue(column.get("columnType") == null ? "" : column.get("columnType").toString().replace("\"",""));
                    recordColumn.setComment(column.get("columnComment") == null ? "" : column.get("columnComment").toString().replace("\"",""));
                    recordColumn.setSeq(i+1);
                    recordColumn.setSynctime(currDate);
                    recordColumn.setSyncuser(this.getUserId());
                    externalColumnMapper.insert(recordColumn);
                }
            });
            db.setSyncTime(currDate);
            this.updataSyncTime(db);
            return "success";
        }
        return "failed";
    }

    public int save(ExternalDBDto db){
        ExternalDB record = this.dbDtoToRecord(db);
        return externalDBMapper.insert(record);
    }

    public int update(ExternalDBDto db){
        ExternalDB record = this.dbDtoToRecord(db);
        ExternalDBExample example = new ExternalDBExample();
        ExternalDBExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(db.getId());
        int i = externalDBMapper.updateByExampleSelective(record,example);
        this.removeData(db.getId());
        return i;
    }
    private int updataSyncTime(ExternalDBDto db){
        ExternalDB record = this.dbDtoToRecord(db);
        ExternalDBExample example = new ExternalDBExample();
        ExternalDBExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(db.getId());
        int i = externalDBMapper.updateByExampleSelective(record,example);
        return i;
    }
    public int delete(ExternalDBDto db){
        db.setStatus(0);
        return this.update(db);
    }

    private String getDBUrl(String apiPath,ExternalDBDto db){
        return apiPath + "?ip=" + db.getIp() + "&prot=" + db.getPort() + "&username="
                + db.getUsername() + "&password=" + db.getPassword() + "&drivername="+db.getDriverName() + "&dbname=" + db.getDbName();
    }

    private Long getUserId(){
        AnyUser anyUser = authenticationGetter.getAnyUser();
        return anyUser.getUser().getId();
    }
    /**
     * 删除该数据库下的所有表信息和字段信息
     * */
    private void removeData(Long dbId){
        ExternalTableExample example = new ExternalTableExample();
        ExternalTableExample.Criteria criteria = example.createCriteria();
        criteria.andDbidEqualTo(dbId);
        externalTableMapper.deleteByExample(example);
        ExternalColumnExample exampleColumn = new ExternalColumnExample();
        ExternalColumnExample.Criteria criteriaColumn = exampleColumn.createCriteria();
        criteriaColumn.andDbidEqualTo(dbId);
        externalColumnMapper.deleteByExample(exampleColumn);
    }

    private ExternalDB dbDtoToRecord(ExternalDBDto db){
        ExternalDB record = new ExternalDB();
        record.setDbname(db.getDbName());
        record.setAliasname(db.getAliasName());
        record.setDrivername(db.getDriverName());
        record.setUsername(db.getUsername());
        record.setPassword(db.getPassword());
        record.setIp(db.getIp());
        record.setPort(db.getPort());
        record.setMapperpath("");
        record.setMapperpackage("");
        record.setComment(db.getComment());
        record.setSyncuser(this.getUserId());
        record.setStatus(db.getStatus());
        record.setSynctime(db.getSyncTime());
        return record;
    }

    private ExternalDBDto dbRecordToDto(ExternalDB record){
        ExternalDBDto db = new ExternalDBDto();
        db.setId(record.getId());
        db.setDbName(record.getDbname());
        db.setAliasName(record.getAliasname());
        db.setDriverName(record.getDrivername());
        db.setUsername(record.getUsername());
        db.setPassword(record.getPassword());
        db.setIp(record.getIp());
        db.setPort(record.getPort());
        db.setMapperPath(record.getMapperpath());
        db.setMapperPackage(record.getMapperpackage());
        db.setComment(record.getComment());
        db.setSyncTime(record.getSynctime());
        db.setStatus(record.getStatus());
        return db;
    }

    private ExternalTableDto tableRecordToDto(ExternalTable record){
        ExternalTableDto db = new ExternalTableDto();
        db.setDbname(record.getDbname());
        db.setTablename(record.getTablename());
        db.setDatacount(record.getDatacount());
        db.setComment(record.getComment());
        db.setSynctime(record.getSynctime());
        ExternalColumnExample exampleColumn = new ExternalColumnExample();
        ExternalColumnExample.Criteria criteria1Column = exampleColumn.createCriteria();
        criteria1Column.andTableidEqualTo(record.getId());
        List<ExternalColumn> externalColumnList = externalColumnMapper.selectByExample(exampleColumn);
        List<ExternalColumnDto> externalColumnDtoList = new ArrayList<>();
        externalColumnList.forEach(externalColumn ->{
            ExternalColumnDto dto = new ExternalColumnDto();
            dto.setDbname(externalColumn.getDbname());
            dto.setTablename(externalColumn.getTablename());
            dto.setColumnname(externalColumn.getColumnname());
            dto.setDatatype(externalColumn.getDatatype());
            dto.setDatatypevalue(externalColumn.getDatatypevalue());
            dto.setComment(externalColumn.getComment());
            dto.setSynctime(externalColumn.getSynctime());
            externalColumnDtoList.add(dto);
        });
        db.setColumns(externalColumnDtoList);
        return db;
    }

    private ExternalTableDto columnRecordToDto(ExternalTable record){
        ExternalTableDto db = new ExternalTableDto();
        db.setDbname(record.getDbname());
        db.setTablename(record.getTablename());
        db.setDatacount(record.getDatacount());
        db.setComment(record.getComment());
        db.setSynctime(record.getSynctime());
        return db;
    }
}

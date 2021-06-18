package com.sundeinfo.sypglass.apis.metadata;


import com.github.pagehelper.PageHelper;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.ExternalColumnDto;
import com.sundeinfo.sypglass.dto.ExternalDBDto;
import com.sundeinfo.sypglass.dto.ExternalTableDto;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.service.ExternalDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "外部数据API")
@RestController
@RequestMapping("external")
public class ExternalDataSourceController extends AbstractController<ExternalDataSourceController> {


    @Autowired
    private ExternalDataSourceService externalDataSourceService;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="批量查找数据库列表", notes="批量查找数据库列表")
    @GetMapping(value = "dbs/{pagenum}-{pagesize}")
    public ResponseEntity<Result<List<ExternalDBDto>>> getDBList(@RequestParam(value = "name") String name,
                                                                @PathVariable(value = "pagenum") int pagenum,
                                                                @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<List<ExternalDBDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ExternalDBDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        return externalDataSourceService.findDBList(name);
                    }
                }
        );
    }

    @ApiOperation(value="依据id查找元数据信息", notes="依据id查找元数据信息")
    @GetMapping(value = "dbById/{id}")
    public ResponseEntity<Result<ExternalDBDto>> getDBById(@PathVariable(value = "id") Long id){
        return requestHandler.doGet(
                new GetCallback<ExternalDBDto>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public ExternalDBDto invoke() throws Exception {
                        return externalDataSourceService.findDBById(id);
                    }
                }
        );
    }

    @ApiOperation(value="依据id查找指定数据库表列表", notes="依据id查找指定数据库表列表")
    @GetMapping(value = "dbTableById/{id}")
    public ResponseEntity<Result<List<ExternalTableDto>>> getDBByIdTable(@PathVariable(value = "id") Long id){
        return requestHandler.doGet(
                new GetCallback<List<ExternalTableDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ExternalTableDto> invoke() throws Exception {
                        return externalDataSourceService.findTableListByDBId(id);
                    }
                }
        );
    }

    @ApiOperation(value="依据id查找指定数据表结构", notes="依据id查找指定数据表结构")
    @GetMapping(value = "dbColumnsById/{id}")
    public ResponseEntity<Result<List<ExternalColumnDto>>> getColumnsById(@PathVariable(value = "id") Long id){
        return requestHandler.doGet(
                new GetCallback<List<ExternalColumnDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ExternalColumnDto> invoke() throws Exception {
                        return externalDataSourceService.findTableColumnsByTableId(id);
                    }
                }
        );
    }

    @ApiOperation(value="依据表name查找指定数据库表内容", notes="依据表name查找指定数据库表内容")
    @GetMapping(value = "findTableContent/{dbId}/{name}/{pageNum}-{pageSize}")
    public ResponseEntity<Result<String>> getTableContent(@PathVariable(value = "dbId") Long dbId,
                                                          @PathVariable(value = "name") String name,
                                                          @PathVariable(value = "pageNum") Integer pageNum,
                                                          @PathVariable(value = "pageSize") Integer pageSize){
        return requestHandler.doGet(
                new GetCallback<String>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public String invoke() throws Exception {
                        return externalDataSourceService.findTableContent(dbId,name,pageNum,pageSize);
                    }
                }
        );
    }

    @ApiOperation(value="新增一个数据库配置", notes="新增一个数据库配置")
    @PostMapping(value = "db")
    public ResponseEntity<Result> postDB(@Valid @RequestBody ExternalDBDto db){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(db.getIp())||
                                StringUtils.isBlank(db.getDbName())||
                                StringUtils.isBlank(db.getDriverName())||
                                StringUtils.isBlank(db.getUsername())||
                                StringUtils.isBlank(db.getPassword())||
                                StringUtils.isBlank(db.getPort())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() {
                    int i = externalDataSourceService.save(db);
                    return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="编辑一个数据库配置", notes="编辑一个数据库配置")
    @PutMapping(value = "dbUpdate")
    public ResponseEntity<Result> postDBUpdate(@Valid @RequestBody ExternalDBDto db){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(db.getIp())||
                                StringUtils.isBlank(db.getDbName())||
                                StringUtils.isBlank(db.getDriverName())||
                                StringUtils.isBlank(db.getUsername())||
                                StringUtils.isBlank(db.getPassword())||
                                StringUtils.isBlank(db.getPort())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() {
                    int i = externalDataSourceService.update(db);
                    return i > 0 ? RequestState.SUCCESS : RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="数据库配置无效化", notes="数据库配置无效化")
    @PutMapping(value = "dbInvalid")
    public ResponseEntity<Result> postDBInvalid(@Valid @RequestBody ExternalDBDto db){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = externalDataSourceService.update(db);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="批量查找数据库驱动", notes="批量查找数据库驱动")
    @GetMapping(value = "dbDriver")
    public ResponseEntity<Result<List<Config>>> getDbDriverList(){
        return requestHandler.doGet(
                new GetCallback<List<Config>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<Config> invoke() throws Exception {
                        return configService.findByNameAndCode(SypGlassConfig.DB_DRIVER_NAME,SypGlassConfig.DB_DRIVER_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="测试数据库连接", notes="测试数据库连接")
    @PostMapping(value = "db/test")
    public ResponseEntity<Result> testDBConnect(@Valid @RequestBody ExternalDBDto db){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(db.getIp())||
                                StringUtils.isBlank(db.getDbName())||
                                StringUtils.isBlank(db.getDriverName())||
                                StringUtils.isBlank(db.getUsername())||
                                StringUtils.isBlank(db.getPassword())||
                                StringUtils.isBlank(db.getPort())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        String res = externalDataSourceService.connect(db);
                        if("success".equals(res)){
                            return  RequestState.SUCCESS ;
                        }
                        return RequestState.REQUEST_DATABASE_NOT_CONNECT;
                    }
                }
        );
    }

    @ApiOperation(value="导入外部数据库结构", notes="导入外部数据库结构")
    @GetMapping(value = "db/{id}/input")
    public ResponseEntity<Result<String>> postDB(@PathVariable(value = "id") Long id){
        return requestHandler.doGet(
                new GetCallback<String>() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        ExternalDBDto db = externalDataSourceService.findDBById(id);
                        String res = externalDataSourceService.connect(db);
                        if("failed".equals(res)){
                            return  RequestState.REQUEST_DATABASE_NOT_CONNECT ;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public String invoke() throws Exception {
                        return externalDataSourceService.inputSchema(id);
                    }
                }
        );
    }

    @ApiOperation(value="删除数据库配置", notes="删除数据库配置")
    @DeleteMapping(value = "db")
    public ResponseEntity<Result> deleteDB(@Valid @RequestBody ExternalDBDto db){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = externalDataSourceService.delete(db);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

}

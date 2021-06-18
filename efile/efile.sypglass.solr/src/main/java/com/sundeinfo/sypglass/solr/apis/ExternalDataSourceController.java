package com.sundeinfo.sypglass.solr.apis;


import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.solr.service.ExternalDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "SolrAPI")
@RestController
@RequestMapping("relay")
public class ExternalDataSourceController extends AbstractController<ExternalDataSourceController> {

    @Autowired
    private ExternalDataSourceService externalDataSourceService;


    @ApiOperation(value="导入外部数据表结构", notes="导入外部数据表结构")
    @GetMapping("/dataInput")
    public ResponseEntity<Result<List<Map<String,Object>>>> inputDBSchema(@RequestParam(value = "ip") String ip,
                                                                      @RequestParam(value = "prot") String prot,
                                                                      @RequestParam(value = "username") String username,
                                                                      @RequestParam(value = "password") String password,
                                                                      @RequestParam(value = "drivername") String driverName,
                                                                      @RequestParam(value = "dbname") String dbName){
        return requestHandler.doGet(
                new GetCallback<List<Map<String, Object>>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public List<Map<String, Object>> invoke() throws Exception {
                        return externalDataSourceService.getDBSchema(ip,prot,username,password,driverName,dbName);
                    }
                }

        );
    }

    @ApiOperation(value="测试数据库连接", notes="测试数据库连接")
    @GetMapping("/testConnect")
    public ResponseEntity testConnect(@RequestParam(value = "ip") String ip,
                                      @RequestParam(value = "prot") String prot,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam(value = "drivername") String driverName,
                                      @RequestParam(value = "dbname") String dbName){
        return requestHandler.doGet(
                new GetCallback<String>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public String invoke() throws Exception {
                        return externalDataSourceService.getDBTime(ip,prot,username,password,driverName,dbName);
                    }
                }

        );
    }

    @ApiOperation(value="查询外部数据库表数据", notes="查询外部数据库表数据")
    @GetMapping("/dataSource")
    public ResponseEntity select(@RequestParam(value = "ip") String ip,
                                           @RequestParam(value = "prot") String prot,
                                           @RequestParam(value = "username") String username,
                                           @RequestParam(value = "password") String password,
                                           @RequestParam(value = "drivername") String driverName,
                                           @RequestParam(value = "dbname") String dbName,
                                           @RequestParam(value = "tableName") String tableName,
                                           @RequestParam(value = "orderCondition") String orderCondition,
                                           @RequestParam(value = "pageNum") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize){
        return requestHandler.doGet(
                new GetCallback<String>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public String invoke() throws Exception {
                        return externalDataSourceService.getData(ip,prot,username,password,driverName,dbName,tableName,pageNum,pageSize,orderCondition);
                    }
                }

        );
    }

}

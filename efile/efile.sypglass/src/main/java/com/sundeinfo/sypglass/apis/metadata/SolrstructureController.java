package com.sundeinfo.sypglass.apis.metadata;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.SolrStructureDto;
import com.sundeinfo.sypglass.service.SolrStructureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "元数据API")
@RestController
@RequestMapping("external")
public class SolrstructureController extends AbstractController<SolrstructureController> {
    @Autowired
    private SolrStructureService solrStructureService;

    @ApiOperation(value="批量查找solr字段列表", notes="批量查找solr字段列表")
    @GetMapping(value = "solr/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<SolrStructureDto>>> getSolrList(@RequestParam(value = "name") String name,
                                                                     @PathVariable(value = "pagenum") int pagenum,
                                                                     @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<SolrStructureDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<SolrStructureDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<SolrStructureDto> listSolrStructureDto = solrStructureService.findAll(name);
                        return new PageInfo<>(listSolrStructureDto);
                    }
                }
        );
    }

    @ApiOperation(value="依据name和元数据类型查找指定solr字段", notes="依据name和元数据类型查找指定solr字段")
    @GetMapping(value = "solr/type/{docType}")
    public ResponseEntity<Result<List<SolrStructureDto>>> getSolr(@PathVariable(value = "docType") Integer docType, @RequestParam(value = "name") String name){
        return requestHandler.doGet(
                new GetCallback<List<SolrStructureDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<SolrStructureDto> invoke() throws Exception {
                        return solrStructureService.findByNameAndDocType(name,docType);
                    }
                }
        );
    }

    @ApiOperation(value="新增一个solr字段配置", notes="新增一个solr字段配置")
    @PostMapping(value = "solrInsert")
    public ResponseEntity<Result> postSolrInsert(@Valid @RequestBody SolrStructureDto solrStructureDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = solrStructureService.save(solrStructureDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="编辑一个solr字段配置", notes="编辑一个solr字段配置")
    @PutMapping(value = "solrUpdate")
    public ResponseEntity<Result> putSolrUpdate(@Valid @RequestBody SolrStructureDto solrStructureDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = solrStructureService.update(solrStructureDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="设置solr字段无效化", notes="设置solr字段无效化")
    @PutMapping(value = "solrInvalid")
    public ResponseEntity<Result> putSolrInvalid(@Valid @RequestBody SolrStructureDto solrStructureDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = solrStructureService.update(solrStructureDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="删除solr字段", notes="删除solr字段")
    @DeleteMapping(value = "solr")
    public ResponseEntity<Result> delete(@Valid @RequestBody SolrStructureDto solrStructureDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = solrStructureService.delete(solrStructureDto);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

}

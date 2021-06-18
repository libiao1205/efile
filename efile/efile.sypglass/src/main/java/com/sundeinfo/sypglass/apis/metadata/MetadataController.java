package com.sundeinfo.sypglass.apis.metadata;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.MetadataDto;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.service.MetadataService;
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
public class MetadataController extends AbstractController<MetadataController> {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="批量查找元数据类型列表", notes="批量查找元数据类型列表")
    @GetMapping(value = "metadataType")
    public ResponseEntity<Result<List<Config>>> getMetadataTypeList(){
        return requestHandler.doGet(
                new GetCallback<List<Config>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<Config> invoke() throws Exception {
                        return configService.findByNameAndCode(SypGlassConfig.DOC_TYPE_NAME,SypGlassConfig.DOC_TYPE_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="批量查找元数据列表", notes="批量查找元数据列表")
    @GetMapping(value = "metadata/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<MetadataDto>>> getMetadataList(@RequestParam(value = "name") String name,
                                                                        @PathVariable(value = "pagenum") int pagenum,
                                                                        @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<MetadataDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<MetadataDto> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<MetadataDto> listMetadataDto = metadataService.findAll(name);
                        return new PageInfo<>(listMetadataDto);
                    }
                }
        );
    }

    @ApiOperation(value="依据类型文档类型id查找指定元数据", notes="依据类型文档类型id查找指定元数据")
    @GetMapping(value = "metadata/type/{docType}")
    public ResponseEntity<Result<List<MetadataDto>>> getMetadataList(@PathVariable(value = "docType") Integer docType){
        return requestHandler.doGet(
                new GetCallback<List<MetadataDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<MetadataDto> invoke() throws Exception {
                        return metadataService.findByDocType(docType);
                    }
                }
        );
    }


    @ApiOperation(value="新增一个元数据配置", notes="新增一个元数据配置")
    @PostMapping(value = "metadata")
    public ResponseEntity<Result> postMetadata(@Valid @RequestBody MetadataDto metadata){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = metadataService.save(metadata);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="编辑一个元数据配置", notes="编辑一个元数据配置")
    @PutMapping(value = "metadata")
    public ResponseEntity<Result> putMetadata(@Valid @RequestBody MetadataDto metadata){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = metadataService.update(metadata);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }
    @ApiOperation(value="设置元数据无效化", notes="设置元数据无效化")
    @PutMapping(value = "metadataInvalid")
    public ResponseEntity<Result> putMetadataInvalid(@Valid @RequestBody MetadataDto metadata){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = metadataService.update(metadata);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }
    @ApiOperation(value="删除元数据", notes="删除元数据")
    @DeleteMapping(value = "metadata")
    public ResponseEntity<Result> delete(@Valid @RequestBody MetadataDto metadata){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = metadataService.delete(metadata);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }
}

package com.sundeinfo.sypglass.apis.admin;

import com.mongodb.gridfs.GridFSDBFile;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.model.Config;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(description = "管理系统API")
@RestController
@RequestMapping("setting")
public class SettingController extends AbstractController<SettingController> {

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="密码修改提醒设置", notes="密码修改提醒设置")
    @PostMapping(value = "passwordUpdateRemind")
    public ResponseEntity<Result> passwordUpdateRemind(@RequestBody PasswordExceedDto passwordExceedDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(passwordExceedDto.getUsableDay()) ||
                                StringUtils.isBlank(passwordExceedDto.getAheadDay()) ||
                                StringUtils.isBlank(passwordExceedDto.getState())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.updatePasswordExceed(passwordExceedDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="密码锁定设置", notes="密码锁定设置")
    @PostMapping(value = "passwordLock")
    public ResponseEntity<Result> passwordLock(@RequestBody PasswordLockDto passwordLockDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(passwordLockDto.getPsdMistakeCount())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.updatePasswordMistakeCount(passwordLockDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }
    @ApiOperation(value="密码复杂度设置", notes="密码复杂度设置")
    @PostMapping(value = "passwordComplexity")
    public ResponseEntity<Result> passwordComplexity(@RequestBody PasswordComplexityDto passwordComplexityDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(passwordComplexityDto.getPsdCheck()) || StringUtils.isBlank(passwordComplexityDto.getPsdLength())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        configService.updatePasswordComplexity(passwordComplexityDto);
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="获取密码修改提醒内容", notes="获取密码修改提醒内容")
    @GetMapping(value = "getPasswordExceed")
    public ResponseEntity<Result<PasswordExceedDto>> getPasswordExceed(){
        return requestHandler.doGet(
                new GetCallback<PasswordExceedDto>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public PasswordExceedDto invoke() throws Exception {
                        return configService.psdExceedFindByName();
                    }
                }
        );
    }

    @ApiOperation(value="获取密码锁定内容", notes="获取密码锁定内容")
    @GetMapping(value = "getPasswordLock")
    public ResponseEntity<Result<Config>> getPasswordLock(){
        return requestHandler.doGet(
                new GetCallback<Config>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public Config invoke() throws Exception {
                        return configService.findOneByNameAndCode(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT);
                    }
                }
        );
    }

    @ApiOperation(value="获取密码等级内容", notes="获取密码等级内容")
    @GetMapping(value = "getPasswordComplexity")
    public ResponseEntity<Result<PasswordComplexityDto>> PasswordComplexityDto(){
        return requestHandler.doGet(
                new GetCallback<PasswordComplexityDto>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public PasswordComplexityDto invoke() throws Exception {
                        return configService.psdComplexityFindByName();
                    }
                }
        );
    }

    @ApiOperation(value="获取设置备份文件自动删除周期", notes="获取设置备份文件自动删除周期")
    @GetMapping(value = "getBackupPeriod")
    public ResponseEntity<Result<Config>> getDeleteBackupPeriod(){
        return requestHandler.doGet(
                new GetCallback<Config>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public Config invoke() throws Exception {
                        return configService.findOneByNameAndCode(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME,SypGlassConfig.DELETE_BACKUP_PERIOD_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="设置备份文件自动删除周期", notes="设置备份文件自动删除周期")
    @PostMapping(value = "deleteBackupPeriod")
    public ResponseEntity<Result> deleteBackupPeriod(@RequestBody DeleteBackupDto deleteBackupDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(deleteBackupDto.getMonth())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.updateDeleteBackup(deleteBackupDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="获取文档根目录logo", notes="获取文档根目录logo")
    @GetMapping(value = "getLogoImg")
    public void getLogoImg(HttpServletResponse response,@RequestParam String seed){
        configService.getLogoImg(response,SypGlassConfig.FOLDER_ROOT_LOGO_NAME,SypGlassConfig.FOLDER_ROOT_LOGO_CODE);
    }

    @ApiOperation(value="设置文档根目录logo", notes="设置文档根目录logo")
    @PostMapping(value = "logoImg")
    public ResponseEntity<Result> logoImg(@RequestPart("file") MultipartFile[] files){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        return configService.saveLogoImg(files);
                    }
                }
        );
    }

    @ApiOperation(value="获取支持结构化数据开关", notes="获取支持结构化数据开关")
    @GetMapping(value = "getConstructionData")
    public ResponseEntity<Result<Config>> getConstructionData(){
        return requestHandler.doGet(
                new GetCallback<Config>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public Config invoke() throws Exception {
                        return configService.findOneByNameAndCode(SypGlassConfig.CONSTRUCTION_DATA_SWITCH_NAME,SypGlassConfig.CONSTRUCTION_DATA_SWITCH_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="设置支持结构化数据开关", notes="设置支持结构化数据开关")
    @PostMapping(value = "constructionData")
    public ResponseEntity<Result> ConstructionData(@RequestBody SystemSwitchDto systemSwitchDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(systemSwitchDto.getValue())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.constructionData(systemSwitchDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="获取solr同步模式", notes="获取solr同步模式")
    @GetMapping(value = "getSolrSynchronizationSwitch")
    public ResponseEntity<Result<Config>> getSolrSynchronizationSwitch(){
        return requestHandler.doGet(
                new GetCallback<Config>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public Config invoke() throws Exception {
                        return configService.findOneByNameAndCode(SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_NAME,SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="设置solr同步模式", notes="设置solr同步模式")
    @PostMapping(value = "solrSynchronizationSwitch")
    public ResponseEntity<Result> SolrSynchronizationSwitch(@RequestBody SystemSwitchDto systemSwitchDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(systemSwitchDto.getValue())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.solrSynchronizationSwitch(systemSwitchDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }
    @ApiOperation(value="获取自定义solr检索语句", notes="获取自定义solr检索语句")
    @GetMapping(value = "solrQueryTemplate")
    public ResponseEntity<Result<Config>> getSolrSearchSwitch(){
        return requestHandler.doGet(
                new GetCallback<Config>() {
                    @Override
                    public RequestState doCheck() {return RequestState.SUCCESS;}
                    @Override
                    public Config invoke() throws Exception {
                        return configService.findOneByNameAndCode(SypGlassConfig.SOLR_QUERY_TEMPLATE_NAME,SypGlassConfig.SOLR_QUERY_TEMPLATE_CODE);
                    }
                }
        );
    }

    @ApiOperation(value="设置自定义solr检索语句", notes="设置自定义solr检索语句")
    @PostMapping(value = "solrQueryTemplate")
    public ResponseEntity<Result> SolrSearchSwitch(@RequestBody SystemSwitchDto systemSwitchDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        if(StringUtils.isBlank(systemSwitchDto.getQueryTemplate())){
                            return RequestState.PARAM_NULL;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = configService.solrQueryTemplate(systemSwitchDto);
                        if(i > 0){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.DB_UPDATE_ERROR;
                    }
                }
        );
    }

}

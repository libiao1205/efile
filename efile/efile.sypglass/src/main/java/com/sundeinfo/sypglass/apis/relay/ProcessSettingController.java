package com.sundeinfo.sypglass.apis.relay;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.dto.ProcessSettingDto;
import com.sundeinfo.sypglass.service.ProcessSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "OA导入API")
@RestController
@RequestMapping("relay")
public class ProcessSettingController extends AbstractController<ProcessSettingController> {

    @Autowired
    private ProcessSettingService processSettingService;

    @ApiOperation(value="查找批处理配置", notes="查找批处理配置")
    @GetMapping(value = "process/settings")
    public ResponseEntity<Result<List<ProcessSettingDto>>> getSetting(){
        return requestHandler.doGet(
                new GetCallback<List<ProcessSettingDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ProcessSettingDto> invoke() throws Exception {
                        return processSettingService.findAll();
                    }
                }
        );
    }

    @ApiOperation(value="编辑批处理配置", notes="编辑批处理配置")
    @PutMapping(value = "process/cron")
    public ResponseEntity<Result> putCronSetting(@RequestBody ProcessSettingDto processSetting){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = processSettingService.updateCron(processSetting.getId(),processSetting.getCron());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }

    @ApiOperation(value="暂停流程转发批处理", notes="暂停流程转发批处理")
    @PutMapping(value="process/pause")
    public ResponseEntity<Result> pauseJob(@RequestBody ProcessSettingDto processSetting)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        processSettingService.pause(processSetting.getId());
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="重开流程转发批处理", notes="重开流程转发批处理")
    @PutMapping(value="process/resume")
    public ResponseEntity<Result> resumeJob(@RequestBody ProcessSettingDto processSetting)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        processSettingService.resume(processSetting.getId());
                        return RequestState.SUCCESS;
                    }
                }
        );
    }



}
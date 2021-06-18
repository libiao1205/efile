package com.sundeinfo.sypglass.relay.apis;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.relay.model.relay.ProcessSetting;
import com.sundeinfo.sypglass.relay.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "批处理API")
@RestController
@RequestMapping("relay")
public class RelayJobController extends AbstractController<RelayJobController> {

    @Autowired
    private JobService jobService;

    @ApiOperation(value="查找批处理配置", notes="查找批处理配置")
    @GetMapping(value="/jobs")
    public ResponseEntity<Result<List<ProcessSetting>>> getJobSetting()
    {
        return requestHandler.doGet(
                new GetCallback<List<ProcessSetting>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ProcessSetting> invoke() throws Exception {
                        return jobService.findAllJobSettings();
                    }
                }
        );
    }

    @ApiOperation(value="手动执行一个导入过程", notes="手动执行一个导入过程")
    @PutMapping(value="/job/{id}/start")
    public ResponseEntity<Result> startJob(@PathVariable long id)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        return RequestState.SUCCESS;
                    }
                }
        );
    }

    @ApiOperation(value="编辑流程转发批处理的执行时间设定", notes="编辑流程转发批处理的执行时间设定")
    @PutMapping(value="/job/{id}/cron")
    public ResponseEntity<Result> putJobCron(@PathVariable long id,@RequestParam(value="cron")String cron)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = jobService.reScheduleJob(id,cron);
                        return (i > 0 ? RequestState.SUCCESS : RequestState.ERROR) ;
                    }
                }
        );
    }

    @ApiOperation(value="暂停流程转发批处理", notes="暂停流程转发批处理")
    @PutMapping(value="/job/{id}/pause")
    public ResponseEntity<Result> pauseJob(@PathVariable long id)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = jobService.pauseJob(id);
                        return (i > 0 ? RequestState.SUCCESS : RequestState.ERROR) ;
                    }
                }
        );
    }

    @ApiOperation(value="恢复流程转发批处理", notes="恢复流程转发批处理")
    @PutMapping(value="/job/{id}/resume")
    public ResponseEntity<Result> resumeJob(@PathVariable long id)
    {
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = jobService.resumeJob(id);
                        return (i > 0 ? RequestState.SUCCESS : RequestState.ERROR) ;
                    }
                }
        );
    }
}

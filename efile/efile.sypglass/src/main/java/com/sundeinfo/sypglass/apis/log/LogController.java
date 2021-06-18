package com.sundeinfo.sypglass.apis.log;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.ActionLogMapper;
import com.sundeinfo.sypglass.model.ActionLog;
import com.sundeinfo.sypglass.model.ActionLogExample;
import com.sundeinfo.sypglass.service.CsvService;
import com.sundeinfo.sypglass.service.UserService;
import com.sundeinfo.sypglass.utility.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class LogController extends AbstractController<LogController> {

    @Autowired
    private ActionLogMapper actionLogMapper;

    @Autowired
    private CsvService csvService;
    @Autowired
    private UserService userService;

    @ApiOperation(value="日志信息检索", notes="日志信息检索")
    @GetMapping(value = "logs/{pagenum}-{pagesize}/filter")
    public ResponseEntity<Result<PageInfo<ActionLog>>> getLogsByFilter(@PathVariable(value = "pagenum") int pagenum,
                                                                       @PathVariable(value = "pagesize") int pagesize,
                                                                       @RequestParam String kind,
                                                                       @RequestParam long id,
                                                                       @RequestParam String startTime,
                                                                       @RequestParam String endTime){
        return requestHandler.doGet(
                new GetCallback<PageInfo<ActionLog>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<ActionLog> invoke() throws Exception {
                        List<ActionLog> logs = getLogPage(kind,id,startTime,endTime,pagenum,pagesize);
                        return new PageInfo<>(logs);
                    }
                }
        );
    }

    @ApiOperation(value="删除日志信息", notes="删除日志信息")
    @DeleteMapping(value = "deleteLogs")
    public ResponseEntity<Result> deleteLogs(@RequestParam String startTime,
                                              @RequestParam String endTime){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = deleteLog(true,"",0,startTime,endTime);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_UPDATE_ERROR;
                    }
                }
        );
    }

    @ApiOperation(value="日志下载", notes="日志下载")
    @PostMapping("logs/download")
    public void downloadFileByFilter(@RequestParam String kind,
                                     @RequestParam long id,
                                     @RequestParam String startTime,
                                     @RequestParam String endTime,
                                     @RequestParam Boolean isDelete,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

        List<ActionLog> logList = getLogPage(kind,id,startTime,endTime,0,0);


        String sTitle = "id,操作详细,IP,URL";
        String fName = "log_";
        String mapKey = "id,execinfo,ip,url";
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (ActionLog order : logList) {
            map = new HashMap<>();
            map.put("id", order.getId());
            map.put("execinfo", order.getExecinfo());
            map.put("ip", order.getIp());
            map.put("url", order.getUrl());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
            csvService.responseSetProperties(fName, response);
            csvService.doExport(dataList, sTitle, mapKey, os);
            deleteLog(isDelete,kind,id,startTime,endTime);
        } catch (Exception e) {
            logger.error("生成csv文件失败", e);
        }
        //return ResultUtil.failure("数据导出出错");
    }

    private List<ActionLog> getLogPage(String kind,long id,String startTime,String endTime,int pagenum,int pagesize)
            throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Date start = DateUtils.strToDate(SypGlass.START_DATE);
        if(!"".equals(startTime)){
            start = DateUtils.strToDate(startTime);
        }
        Date end = DateUtils.strToDate(SypGlass.END_DATE);
        if(!"".equals(startTime)){
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtils.strToDate(endTime));
            c.add(Calendar.DAY_OF_MONTH, 1);//+1天
            end = c.getTime();
        }
        if(id > 0){
            return getLogsByMemberAndPeriod(kind,id,start,end,pagenum,pagesize);
        }else{
            return getLogByPeriod(start,end,pagenum,pagesize);
        }
    }

    private List<ActionLog> getLogsByMemberAndPeriod(String kind,long id,Date start,Date end,int pagenum, int pagesize)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        ActionLogExample example = new ActionLogExample();
        ActionLogExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        setUserInfoCriteria(criteria,kind,id);
        criteria.andStarttimeBetween(start.getTime(),end.getTime());
        example.setOrderByClause("id DESC");
        if (pagenum > 0){
            PageHelper.startPage(pagenum, pagesize);
        }
        return actionLogMapper.selectByExample(example);
    }

    private List<ActionLog> getLogByPeriod(Date start,Date end,int pagenum, int pagesize){
        ActionLogExample example = new ActionLogExample();
        ActionLogExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andStarttimeBetween(start.getTime(),end.getTime());

        example.setOrderByClause("id DESC");
        if (pagenum > 0){
            PageHelper.startPage(pagenum, pagesize);
        }
        return actionLogMapper.selectByExample(example);
    }

    private int deleteLog(boolean isDelete,String kind,long id,String startTime,String endTime) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if(isDelete){
            Date start = DateUtils.strToDate(SypGlass.START_DATE);
            if(!"".equals(startTime)){
                start = DateUtils.strToDate(startTime);
            }
            Date end = DateUtils.strToDate(SypGlass.END_DATE);
            if(!"".equals(startTime)){
                Calendar c = Calendar.getInstance();
                c.setTime(DateUtils.strToDate(endTime));
                c.add(Calendar.DAY_OF_MONTH, 1);//+1天
                end = c.getTime();
            }
            ActionLogExample example = new ActionLogExample();
            ActionLogExample.Criteria criteria = example.createCriteria();
            criteria.andIdIsNotNull();
            setUserInfoCriteria(criteria,kind,id);
            criteria.andStarttimeBetween(start.getTime(),end.getTime()+86400000);
            return actionLogMapper.deleteByExample(example);
        }
        return 1;
    }

    private void setUserInfoCriteria(ActionLogExample.Criteria criteria,String kind,long id) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if (SypGlass.MEMBER_KIND_DEPT.equals(kind)){
            if (id > 1 ){
                List<UserDto> userDtos = userService.findByDeptWithChildren(id);
                List<Long> inUserIds = new ArrayList<>();
                if(userDtos.size()>0){
                    inUserIds = ReflectUtils.reflectDataList(userDtos,"id",Long.class);
                }
                if(inUserIds.size() > 0) {
                    criteria.andUseridIn(inUserIds);
                }
            }
        }else if(SypGlass.MEMBER_KIND_USER.equals(kind)){
            criteria.andUseridEqualTo(id);
        }
    }
}

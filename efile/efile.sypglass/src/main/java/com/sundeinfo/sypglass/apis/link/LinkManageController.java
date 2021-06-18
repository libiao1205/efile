package com.sundeinfo.sypglass.apis.link;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.LinkDto;
import com.sundeinfo.sypglass.dto.LinkResourceDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.service.CsvService;
import com.sundeinfo.sypglass.service.LinkService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class LinkManageController extends AbstractController<LinkManageController> {

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @ApiOperation(value="日志下载", notes="日志下载")
    @PostMapping("dept/{id}/links/download/{startdate}/{enddate}")
    public void downloadFile(@PathVariable(value = "id") int id,
                             @PathVariable(value = "startdate") String startdate, @PathVariable(value = "enddate") String enddate,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> dataList = null;
        List<LinkDto> linkDtos = null;
        if (id == 0){
            if ("0000-00-00".equals(startdate) && "9999-99-99".equals(enddate)){
                linkDtos = linkService.findAll();
            }else{
                linkDtos = linkService.findAllByPeriod(startdate,enddate);
            }

        }else {
            List<UserDto> userDtos = userService.findByDept(id);
            List<Long> ids = ReflectUtils.reflectDataList(userDtos,"id",Long.class);
            if ("0000-00-00".equals(startdate) && "9999-99-99".equals(enddate)){
                linkDtos = linkService.findByUsers(ids);
            }else{
                linkDtos = linkService.findByUsersAndPeriod(ids,startdate,enddate);
            }

        }

        if (linkDtos == null || linkDtos.size() == 0) {
            return;
        }
        String sTitle = "外链地址,外链发布者,外链开始时间,外链结束时间,外链状态,外链文件名";
        String fName = "log_";
        String mapKey = "fullUrl,user,startDate,endDate,status,name";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (LinkDto order : linkDtos) {
            for (LinkResourceDto resource:order.getResources()) {
                map = new HashMap<>();
                map.put("fullUrl", order.getFullUrl());
                map.put("user", order.getUser().getName());
                map.put("startDate", DateUtils.getReqDate(order.getStartDate()));
                if (order.getEndDate().compareTo(DateUtils.strToDate(SypGlass.INDEFINITE_DATE)) < 0){
                    map.put("endDate", DateUtils.getReqDate(order.getEndDate()));
                }else{
                    map.put("endDate", "无期限");
                }

                map.put("status", order.getStatus().value());
                if (SypGlass.RESOURCE_TYPE_FOLDER.equals(resource.getKind())){
                    map.put("name", "文件夹：" + resource.getName());
                }else{
                    map.put("name", resource.getName() + "." + resource.getSuffix() );
                }
                dataList.add(map);
            }
        }

        try (final OutputStream os = response.getOutputStream()) {
            csvService.responseSetProperties(fName, response);
            csvService.doExport(dataList, sTitle, mapKey, os);
        } catch (Exception e) {
            logger.error("生成csv文件失败", e);
        }
    }

    @ApiOperation(value="按照部门检索外链信息", notes="按照部门检索外链信息")
    @GetMapping(value = "dept/{id}/links/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<LinkDto>>> getUserLinks(@PathVariable(value = "id") int id, @PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<LinkDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<LinkDto> invoke() throws Exception {
                        if (id == 0){
                            PageHelper.startPage(pagenum, pagesize);
                            List<LinkDto> linkDtos = linkService.findAllForPage();
                            PageInfo<LinkDto> pageInfo = new PageInfo<>(linkDtos);
                            return pageInfo;
                        }else {
                            List<UserDto> userDtos = userService.findByDept(id);
                            List<Long> ids = ReflectUtils.reflectDataList(userDtos,"id",Long.class);
                            PageHelper.startPage(pagenum, pagesize);
                            List<LinkDto> linkDtos = linkService.findByUsersForPage(ids);
                            PageInfo<LinkDto> pageInfo = new PageInfo<>(linkDtos);
                            return pageInfo;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="删除外链", notes="删除外链")
    @DeleteMapping(value = "link")
    //TODO:@Permissionable(function = FunctionType.DELETE)
    public ResponseEntity<Result> deleteLink(@RequestBody LinkDto linkDto){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = linkService.modToDelete(linkDto.getId(),authenticationGetter.getAnyUser().getUser().getId());
                        return i > 0? RequestState.SUCCESS : RequestState.DB_DELETE_ERROR;
                    }
                }
        );
    }

}

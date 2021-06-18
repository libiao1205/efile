package com.sundeinfo.sypglass.apis.relay;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.dto.ConfigDto;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.FolderSetting;
import com.sundeinfo.sypglass.service.FolderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(description = "OA导入API")
@RestController
@RequestMapping("relay")
public class FolderSettingController extends AbstractController<FolderSettingController> {

    @Autowired
    private FolderSettingService folderSettingService;

    @Autowired
    private ConfigService configService;

    @ApiOperation(value="查找导入配置列表", notes="查找导入配置列表")
    @GetMapping(value = "settings")
    public ResponseEntity<Result<List<FolderSetting>>> getDept(){
        return requestHandler.doGet(
                new GetCallback<List<FolderSetting>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<FolderSetting> invoke() throws Exception {
                        return folderSettingService.findAll();
                    }
                }
        );
    }

    @ApiOperation(value="新增配置", notes="新增配置")
    @PostMapping(value = "setting")
    public ResponseEntity<Result<FolderSetting>> postSetting(@RequestBody FolderSetting folderSetting){
        return requestHandler.doPost(
                new ResponseCallback<FolderSetting>(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        FolderSetting dto = folderSettingService.findByKey(
                                folderSetting.getWorkflowid(),
                                folderSetting.getFirstdictcode(),
                                folderSetting.getSeconddictcode(),
                                folderSetting.getThirddictcode());
                        if(dto != null){
                            return RequestState.RELAY_SETTING_REPEAT;
                        }
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        List<Config> configs = null;
                        configs = configService.findByNameAndCode("relay_setting","workflow");
                        for (Config config:configs) {
                            String[] setting = config.getValue().split("\\^");
                            if (folderSetting.getWorkflowid().toString().equals(setting[0])){
                                folderSetting.setWorkflowname(setting[1]);
                                break;
                            }
                        }
                        configs = configService.findByNameAndCode("relay_setting","dept");
                        for (Config config:configs) {
                            String[] setting = config.getValue().split("\\^");
                            if (folderSetting.getFirstdictcode().equals(setting[0])){
                                folderSetting.setFirstdictname(setting[1]);
                                break;
                            }
                        }
                        if ("1".equals(folderSetting.getSeconddictcode())){
                            folderSetting.setSeconddictname("采购");
                        }else if("2".equals(folderSetting.getSeconddictcode())){
                            folderSetting.setSeconddictname("销售");
                        }else{
                            folderSetting.setSeconddictname("规章制度");
                        }
                        int i = folderSettingService.save(folderSetting);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                    @Override
                    public FolderSetting response() throws Exception {
                        return folderSetting;
                    }
                }
        );
    }


    @ApiOperation(value="修改配置", notes="修改配置")
    @PostMapping(value = "editSetting")
    public ResponseEntity<Result<FolderSetting>> editSetting(@RequestBody FolderSetting folderSetting){
        return requestHandler.doPost(
                new ResponseCallback<FolderSetting>(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        FolderSetting dto = folderSettingService.findById(folderSetting.getId());
                        if(dto != null){
                            return RequestState.SUCCESS;
                        }
                        return RequestState.RELAY_SETTING_ERROR;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = folderSettingService.edit(folderSetting);
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                    @Override
                    public FolderSetting response() throws Exception {
                        return folderSetting;
                    }
                }
        );
    }

    @ApiOperation(value="删除配置", notes="删除配置")
    @DeleteMapping(value = "setting")
    public ResponseEntity<Result> deleteSetting(@RequestBody FolderSetting folderSetting){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {

                        int i = folderSettingService.delete(folderSetting.getId());
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }

    @ApiOperation(value="配置信息查询", notes="配置信息查询")
    @GetMapping(value = "setting/{name}/{code}")
    public ResponseEntity<Result<List<ConfigDto>>> getConfig(@PathVariable String name, @PathVariable String code){
        return requestHandler.doGet(
                new GetCallback<List<ConfigDto>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public List<ConfigDto> invoke() throws Exception {
                        List<ConfigDto> result = new ArrayList<>();
                        if (!"relay_setting".equals(name)) return result;
                        switch (code){
                            case "workflow":
                            case "dept":
                                List<Config> configs = configService.findByNameAndCode(name,code);
                                for (Config config:configs) {
                                    String[] setting = config.getValue().split("\\^");
                                    result.add(new ConfigDto(setting[0],setting[1]));
                                }
                                return result;
                            case "kind":
                                result.add(new ConfigDto("1","采购"));
                                result.add(new ConfigDto("2","销售"));
                                result.add(new ConfigDto("3","规章制度"));
                                return result;
                            default:
                                return result;
                        }


                    }
                }
        );
    }

}

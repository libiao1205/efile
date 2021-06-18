package com.sundeinfo.sypglass.apis.relay;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.sypglass.service.JoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "OA导入API")
@RestController
@RequestMapping("relay")
public class SynchronizeController extends AbstractController<SynchronizeController> {

    @Autowired
    private JoinService joinService;

    @ApiOperation(value="组织部门批量导入", notes="组织部门批量导入")
    @PostMapping(value = "depts")
    public ResponseEntity<Result> postDepts(){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = joinService.inputDepartment();
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }


    @ApiOperation(value="用户批量导入", notes="用户批量导入")
    @PostMapping(value = "users")
    public ResponseEntity<Result> postUsers(){
        return requestHandler.doPost(
                new PostCallback(){
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int i = joinService.inputUser();
                        return i > 0? RequestState.SUCCESS : RequestState. DB_INSERT_ERROR;
                    }
                }

        );
    }
}
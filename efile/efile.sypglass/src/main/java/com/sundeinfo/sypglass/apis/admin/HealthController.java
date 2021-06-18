package com.sundeinfo.sypglass.apis.admin;

import com.sundeinfo.core.actuate.ServerHealthIndicator;
import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.actuate.HealthMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "管理系统API")
@RestController
@RequestMapping("admin")
public class HealthController extends AbstractController<HealthController> {

    @Autowired
    private ServerHealthIndicator serverHealthIndicator;

    @ApiOperation(value="服务器健康检查", notes="服务器健康检查")
    @GetMapping(value = "health")
    @ActionLog()
    public ResponseEntity<Result<HealthMap>> refreshAuthenticationToken(HttpServletRequest request){

        return requestHandler.doGet(
                new GetCallback<HealthMap>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }

                    @Override
                    public HealthMap invoke() throws Exception {
                        return serverHealthIndicator.executor();
                    }
                }
        );
    }

}

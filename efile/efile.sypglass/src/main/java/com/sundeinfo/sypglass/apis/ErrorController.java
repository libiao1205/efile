package com.sundeinfo.sypglass.apis;

import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.request.result.ResultFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ErrorController extends AbstractController<ErrorController> {

    @RequestMapping(value = "/400")
    public ResponseEntity<Result> error400(){
        return ResultFactory.getDataMessage(RequestState.ERROR400);
    }

    @RequestMapping(value = "/401")
    public ResponseEntity<Result> error401(){
        return ResultFactory.getDataMessage(RequestState.ERROR401);
    }

    @RequestMapping(value = "/403")
    public ResponseEntity<Result> error403(){
        return ResultFactory.getDataMessage(RequestState.ERROR403);
    }

    @RequestMapping(value = "/404")
    public ResponseEntity<Result> error404(){
        return ResultFactory.getDataMessage(RequestState.ERROR404);
    }

    @RequestMapping(value = "/405")
    public ResponseEntity<Result> error405(){
        return ResultFactory.getDataMessage(RequestState.ERROR405);
    }

    @RequestMapping(value = "/406")
    public ResponseEntity<Result> error406(){
        return ResultFactory.getDataMessage(RequestState.ERROR406);
    }

    @RequestMapping(value = "/407")
    public ResponseEntity<Result> error407(){
        return ResultFactory.getDataMessage(RequestState.ERROR407);
    }

    @RequestMapping(value = "/408")
    public ResponseEntity<Result> error408(){
        return ResultFactory.getDataMessage(RequestState.ERROR408);
    }

    @RequestMapping(value = "/409")
    public ResponseEntity<Result> error409(){
        return ResultFactory.getDataMessage(RequestState.ERROR409);
    }

    @RequestMapping(value = "/500")
    public ResponseEntity<Result> error500(){
        return ResultFactory.getDataMessage(RequestState.ERROR500);
    }

    @RequestMapping(value = "/501")
    public ResponseEntity<Result> error501(){
        return ResultFactory.getDataMessage(RequestState.ERROR501);
    }

    @RequestMapping(value = "/503")
    public ResponseEntity<Result> error503(){
        return ResultFactory.getDataMessage(RequestState.ERROR503);
    }
}
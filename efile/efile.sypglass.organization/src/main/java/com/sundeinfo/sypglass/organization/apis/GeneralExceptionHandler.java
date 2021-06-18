package com.sundeinfo.sypglass.organization.apis;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.request.result.ResultFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GeneralExceptionHandler extends AbstractComponent<GeneralExceptionHandler> {

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Result> errorHandler(Exception ex,HttpServletRequest request) {
        logger.error("-----------------------服务器发生异常-----------------------");
        ex.printStackTrace();
        logger.error("错误链接:" + request.getRequestURL().toString());
        logger.error("错误信息:" + ex.getMessage());
        return ResultFactory.getErrorMessage(RequestState.ERROR,ex.getMessage());
    }

    /**
     * 全局参数校验异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> errorHandler(MethodArgumentNotValidException ex,HttpServletRequest request) {
        logger.error("-----------------------参数校验异常-----------------------");
        logger.error("错误链接:" + request.getRequestURL().toString());
        logger.error("错误信息:" + ex.getMessage());
        return ResultFactory.getErrorMessage(RequestState.ERROR400,ex.getBindingResult().getFieldError().getDefaultMessage());
    }
}

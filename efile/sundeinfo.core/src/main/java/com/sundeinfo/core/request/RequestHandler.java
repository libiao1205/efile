package com.sundeinfo.core.request;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.core.request.result.ResultFactory;
import com.sundeinfo.core.utility.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
@SuppressWarnings("unchecked")
public class RequestHandler extends AbstractComponent<RequestHandler> {

    @Autowired
    HttpServletRequest request;

    /**
     * 执行Get请求
     */
    public <T> ResponseEntity<Result<T>> doGet(final GetCallback<T> callback){
        if (request != null){
            logger.info("请求:[" + request.getMethod() + "] " + request.getRequestURL());
        }
        try{
            RequestState state = callback.doCheck();
            //结果检查
            if (state != RequestState.SUCCESS)
            {
                //异常发生时直接返回
                return ResultFactory.getDataMessage(state,null);
            }
            //返回值取得
            T t = callback.invoke();
            //返回值检查
            if (t == null){
                logger.warn("被执行的请求没有有效响应结果：data=null");
                return ResultFactory.getDataMessage(RequestState.SUCCESS,null);
            }
            //正常返回
            if (logger.isDebugEnabled()){logger.debug("执行请求成功：data=" + t.toString());}
            return ResultFactory.getDataMessage(RequestState.SUCCESS,t);
        }catch (Exception e)
        {
            logger.error("执行请求失败:", e);
            e.printStackTrace();
            return ResultFactory.getDataMessage(RequestState.ERROR,null);
        }
    }

    /**
     * 执行Post请求:无返回
     */
    public ResponseEntity<Result> doPost(final PostCallback callback) {
        if (request != null){
            logger.info("请求:[" + request.getMethod() + "] " + request.getRequestURL());
        }
        try {
            //参数校验
            RequestState checkState = callback.doCheck();
            if (checkState != RequestState.SUCCESS)
            {
                //异常发生时直接返回
                return ResultFactory.getDataMessage(checkState);
            }
            //数据更新
            RequestState invokeState = callback.invoke();
            //结果返回
            return ResultFactory.getDataMessage(invokeState);
        }catch (Exception e)
        {
            logger.error("执行请求失败:", e);
            return ResultFactory.getDataMessage(RequestState.ERROR);
        }
    }

    /**
     * 执行Post请求:无返回
     */
    public ResponseEntity<Result> doPost(final PostCheckResultCallback callback) {
        if (request != null){
            logger.info("请求:[" + request.getMethod() + "] " + request.getRequestURL());
        }
        try {
            //参数校验
            RequestState checkState = callback.doCheck();
            if (checkState != RequestState.SUCCESS)
            {
                String checkMessage = callback.getCheckMessage(checkState);
                //异常发生时直接返回
                if (StringUtils.isEmpty(checkMessage)){
                    return ResultFactory.getDataMessage(checkState);
                }else{
                    return ResultFactory.getErrorMessage(checkState,checkMessage);
                }
            }
            //数据更新
            RequestState invokeState = callback.invoke();

            //结果检查
            if (invokeState != RequestState.SUCCESS)
            {
                String invokeMessage = callback.getInvokeMessage(invokeState);
                //异常发生时直接返回
                if (StringUtils.isEmpty(invokeMessage)){
                    return ResultFactory.getDataMessage(invokeState);
                }else{
                    return ResultFactory.getErrorMessage(invokeState,invokeMessage);
                }
            }else{
                return ResultFactory.getDataMessage(invokeState);
            }
        }catch (Exception e)
        {
            logger.error("执行请求失败:", e);
            return ResultFactory.getDataMessage(RequestState.ERROR);
        }
    }

    /**
     * 执行Post请求:有返回
     */
    public <T> ResponseEntity<Result<T>> doPost(final ResponseCallback<T> responseCallback) {
        if (request != null){
            logger.info("请求:[" + request.getMethod() + "] " + request.getRequestURL());
        }
        try {
            //参数校验
            RequestState checkState = responseCallback.doCheck();
            if (checkState != RequestState.SUCCESS)
            {
                //异常发生时直接返回
                return ResultFactory.getDataMessage(checkState,null);
            }
            //数据更新
            RequestState invokeState = responseCallback.invoke();
            //结果检查
            if (invokeState != RequestState.SUCCESS)
            {
                //异常发生时直接返回
                return ResultFactory.getDataMessage(invokeState,null);
            }
            //返回值取得
            T t = responseCallback.response();
            //返回值检查
            if (t == null){
                logger.warn("被执行的请求没有有效响应结果");
                return ResultFactory.getDataMessage(RequestState.SUCCESS,null);
            }
            //正常返回
            if (logger.isDebugEnabled()){logger.debug("执行请求成功");}
            return ResultFactory.getDataMessage(RequestState.SUCCESS,t);
        }catch (BadCredentialsException be){
            logger.error("登录失败:", be);
            return ResultFactory.getDataMessage(RequestState.PARAM_USELESS_USERNAME_PASSWORD,null);
        }
        catch (Exception e)
        {
            logger.error("执行请求失败:", e);
            return ResultFactory.getDataMessage(RequestState.ERROR,null);
        }
    }

    /**
     * 执行Post请求:有返回
     */
    public <T> ResponseEntity<Result<T>> doPost(final ResponseCheckResultCallback<T> responseCheckResultCallback) {
        if (request != null){
            logger.info("请求:[" + request.getMethod() + "] " + request.getRequestURL());
        }
        try {
            //参数校验
            RequestState checkState = responseCheckResultCallback.doCheck();
            if (checkState != RequestState.SUCCESS)
            {
                String checkMessage = responseCheckResultCallback.getCheckMessage(checkState);
                //异常发生时直接返回
                if (StringUtils.isEmpty(checkMessage)){
                    return ResultFactory.getDataMessage(checkState,null);
                }else{
                    return ResultFactory.getDataMessage(checkState,checkMessage,null);
                }
            }
            //数据更新
            RequestState invokeState = responseCheckResultCallback.invoke();
            //结果检查
            if (invokeState != RequestState.SUCCESS)
            {
                String invokeMessage = responseCheckResultCallback.getInvokeMessage(invokeState);
                //异常发生时直接返回
                if (StringUtils.isEmpty(invokeMessage)){
                    return ResultFactory.getDataMessage(invokeState,null);
                }else{
                    return ResultFactory.getDataMessage(invokeState,invokeMessage,null);
                }
            }
            //返回值取得
            T t = responseCheckResultCallback.response();
            //返回值检查
            if (t == null){
                logger.warn("被执行的请求没有有效响应结果");
                return ResultFactory.getDataMessage(RequestState.SUCCESS,null);
            }
            //正常返回
            if (logger.isDebugEnabled()){logger.debug("执行请求成功");}
            return ResultFactory.getDataMessage(RequestState.SUCCESS,t);
        }catch (BadCredentialsException be){
            logger.error("登录失败:", be);
            return ResultFactory.getDataMessage(RequestState.PARAM_USELESS_USERNAME_PASSWORD,null);
        }
        catch (Exception e)
        {
            logger.error("执行请求失败:", e);
            return ResultFactory.getDataMessage(RequestState.ERROR,null);
        }
    }
}


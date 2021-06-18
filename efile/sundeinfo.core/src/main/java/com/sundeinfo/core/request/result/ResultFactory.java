package com.sundeinfo.core.request.result;

import com.sundeinfo.core.request.RequestState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;

@SuppressWarnings("unchecked")
public class ResultFactory {

    public static ResponseEntity<Result> getDataMessage(RequestState state)
    {
        EmptyResult emptyResult = new EmptyResult();
        emptyResult.setMsg(state.id(),state.value());
        return new ResponseEntity<Result>(emptyResult,RequestState.valueOfHttpStatus(state));
    }

    public static <T> ResponseEntity<Result<T>> getDataMessage(RequestState state, T ctt){
        Result<T> result = new Result<T>();
        result.setMsg(state.id(), state.value(),ctt);
        return new ResponseEntity<Result<T>>(result, RequestState.valueOfHttpStatus(state));
    }

    public static <T> ResponseEntity<Result<T>> getDataMessage(RequestState state,String message, T ctt){
        Result<T> result = new Result<T>();
        result.setMsg(state.id(), message,ctt);
        return new ResponseEntity<Result<T>>(result, RequestState.valueOfHttpStatus(state));
    }

    public static ResponseEntity<Result> getNoImplMessage()
    {
        EmptyResult emptyResult = new EmptyResult(RequestState.NO_IMPLEMENTED.id(), RequestState.NO_IMPLEMENTED.value());
        return new ResponseEntity<Result>(emptyResult,HttpStatus.OK);
    }

    public static <T> ResponseEntity<Result<T>> getNoImplMessage(T t)
    {
        EmptyResult emptyResult = new EmptyResult(RequestState.NO_IMPLEMENTED.id(), RequestState.NO_IMPLEMENTED.value());
        return new ResponseEntity<Result<T>>(emptyResult,HttpStatus.OK);
    }

    public static ResponseEntity<CallbackResult> getCallbackMessage(RequestState state)
    {
        CallbackResult callbackResult = new CallbackResult();
        callbackResult.setMsg(state.id(), state.value());
        return new ResponseEntity<CallbackResult>(callbackResult,RequestState.valueOfHttpStatus(state));
    }

    public static ResponseEntity<CallbackResult> getCallbackMessage(RequestState state, HashMap<String,String> cb)
    {
        CallbackResult callbackResult = new CallbackResult(cb);
        callbackResult.setMsg(state.id(), state.value());
        return new ResponseEntity<CallbackResult>(callbackResult,RequestState.valueOfHttpStatus(state));
    }

    public static <T> ResponseEntity<CallbackResult<T>> getCallbackMessage(RequestState state, T ctt, HashMap<String,String> cb)
    {
        CallbackResult<T> callbackResult = new CallbackResult<T>(cb);
        callbackResult.setMsg(state.id(), state.value(),ctt);
        return new ResponseEntity<CallbackResult<T>>(callbackResult,RequestState.valueOfHttpStatus(state));
    }

    public static ResponseEntity<Result> getErrorMessage(RequestState state,String message)
    {
        EmptyResult emptyResult = new EmptyResult();
        emptyResult.setMsg(state.id(),message);
        return new ResponseEntity<Result>(emptyResult,RequestState.valueOfHttpStatus(state));
    }

}

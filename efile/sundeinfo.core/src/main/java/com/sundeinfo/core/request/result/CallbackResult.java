package com.sundeinfo.core.request.result;

import java.util.HashMap;

public class CallbackResult<T> extends Result<T> {
    private HashMap<String,String> cb;

    public CallbackResult() {
        super();
        cb = new HashMap<String,String>();
    }

    public CallbackResult(HashMap<String, String> cb) {
        super();
        this.cb = cb;
    }

    public CallbackResult(int code, String message, HashMap<String, String> cb) {
        super(code, message);
        this.cb = cb;
    }

    public CallbackResult(int code, String message, T content, HashMap<String, String> cb) {
        super(code, message, content);
        this.cb = cb;
    }

    public HashMap<String, String> getCb() {
        return cb;
    }

    public void setCb(HashMap<String, String> cb) {
        this.cb = cb;
    }

    public void putCb(String k,String v){
        if(cb.containsKey(k)){
            cb.put(k,v);
        }
    }
}

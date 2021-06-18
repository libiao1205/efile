package com.sundeinfo.core.request.result;

public class Result<T> {
    private int c;
    private String msg;
    private T ctt;

    public int getC() {
        return c;
    }

    public String getMsg() {
        return msg;
    }

    public T getCtt() {
        return ctt;
    }

    public Result() {
    }

    public Result(int code, String message) {
        this();
        this.c = code;
        this.msg = message;
    }

    public Result(int code, String message, T content) {
        this();
        this.c = code;
        this.msg = message;
        this.ctt = content;
    }

    public void setMsg(int code, String message) {
        this.c = code;
        this.msg = message;
        this.ctt = null;
    }

    public void setMsg(int code, String message, T content) {
        this.c = code;
        this.msg = message;
        this.ctt = content;
    }
}
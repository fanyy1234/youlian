package com.bby.youlianwallet.model;

import java.io.Serializable;

/**
 * Created by fanyy on 2018/4/6.
 */

public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = -1467576157657126613L;

    private int code;
    private String message;
    private T result;


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }



}

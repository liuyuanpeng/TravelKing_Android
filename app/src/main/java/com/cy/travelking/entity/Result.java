package com.cy.travelking.entity;

import com.cy.travelking.constant.ResultCode;

/**
 * Created by AAA on 2018/8/8.
 */

public class Result<T> {

    /**
     * 返回结果码
     */
    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.equals(code);
    }
}

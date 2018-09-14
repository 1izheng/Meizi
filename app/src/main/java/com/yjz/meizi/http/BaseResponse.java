package com.yjz.meizi.http;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author lizheng
 * @date created at 2018/9/1 下午5:28
 */
public class BaseResponse<T> implements Serializable {

    private boolean error;
    private int code;
    private String msg;

    @SerializedName("results")
    private T data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess() {
        return !error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

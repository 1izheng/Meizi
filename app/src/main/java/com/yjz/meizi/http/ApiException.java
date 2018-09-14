package com.yjz.meizi.http;

import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * 自定义异常
 * @author lizheng 
 * created at 2018/9/6 上午11:47
 */

public class ApiException extends Exception {

    private int code;
    private String msg;

    public ApiException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public static final int UN_KNONE_ERROR = 7000;
    public static final int SERVER_DATA_ERROR = 7001;
    public static final int CONNECT_ERROR = 7002;
    public static final int TIME_OUT_ERROR = 7003;

    public static ApiException handlerException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof MalformedJsonException) {
            ex = new ApiException(e, SERVER_DATA_ERROR);
            ex.setMsg("数据解析错误");
        } else if (e instanceof HttpException) {
            ex = new ApiException(e, ((HttpException) e).code());
            ex.setMsg("网络错误");
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, CONNECT_ERROR);
            ex.setMsg("网络连接异常");
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, TIME_OUT_ERROR);
            ex.setMsg("网络连接超时");
        } else {
            ex = new ApiException(e, UN_KNONE_ERROR);
            ex.setMsg("未知错误");
        }
        return ex;
    }
}

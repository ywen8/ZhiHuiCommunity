package com.rain.zhihui_community.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Tamic on 2016-08-12.
 */
public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.RESCODE_EXIST);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.Message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.State);
            ex.Message = resultException.Message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_EXCEPTION);
            ex.Message = "请求抛出异常";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_NOLOGIN);
            ex.Message = "登录失效请重新登录";
            return ex;
        } else if (e instanceof java.lang.IllegalStateException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_NOEXIST);
            ex.Message = "查询为空";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_NOAUTH);
            ex.Message = "无操作权限";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_TOKENEXCEPTION);
            ex.Message = "连接超时";
            return ex;
        } else if (e instanceof java.lang.RuntimeException) {
            ex = new ResponeThrowable(e, ERROR.RESCODE_EXIST);
            ex.Message = "暂无数据";
            return ex;
        } else {
            ex = new ResponeThrowable(e, ERROR.RESCODE_MASSAGEEXCEPTION);
            ex.Message = "业务错误";
            return ex;
        }
    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 请求抛出异常
         */
        public static final int RESCODE_EXCEPTION = 1002;
        /**
         * 未登陆状态
         */
        public static final int RESCODE_NOLOGIN = 1003;
        /**
         * 查询结果为空
         */
        public static final int RESCODE_NOEXIST = 1004;
        /**
         * 无操作权限
         */
        public static final int RESCODE_NOAUTH = 1005;
        /**
         * 设备id异常
         */
        public static final int RESCODE_TOKENEXCEPTION = 1006;
        /**
         * 业务错误
         */
        public static final int RESCODE_MASSAGEEXCEPTION = 1007;
        /**
         * 失败
         */
        public static final int RESCODE_EXIST = 1008;
    }

    public static class ResponeThrowable extends Exception {
        public int State;
        public String Message;

        public ResponeThrowable(Throwable throwable, int State) {
            super(throwable);
            this.State = State;

        }
    }

    public class ServerException extends RuntimeException {
        public int State;
        public String Message;
    }

}


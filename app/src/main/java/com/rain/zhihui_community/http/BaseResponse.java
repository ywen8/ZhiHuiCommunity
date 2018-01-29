package com.rain.zhihui_community.http;

/**
 * 网络返回基类 支持泛型
 * Created by Rain on 2017/6/13 0013.
 */

public class BaseResponse<T> {

    public String describe;
    public int code;
    private T messageBody;

    public String getMessage() {
        return describe;
    }

    public void setMessage(String describe) {
        describe = describe;
    }

    public int getState() {
        return code;
    }

    public void setState(int code) {
        code = code;
    }

    public T getData() {
        return messageBody;
    }

    public void setData(T data) {
        messageBody = data;
    }

    public boolean isOk() {
        return code == 1000;
    }
}

package edu.jnu.utils;

import edu.jnu.enums.ResponseEnum;

/**
 * @author Guo zifan
 * @date 2022年01月24日 19:14
 */
public class JsonResponse<T> {
    private int code;
    private String msg;
    private T data;

    public JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    // 数据默认返回null
    public JsonResponse(ResponseEnum responseEnum) {
        this.data = null;
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getErrMsg();
    }

    public JsonResponse(ResponseEnum responseEnum, T data) {
        this.data = data;
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getErrMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

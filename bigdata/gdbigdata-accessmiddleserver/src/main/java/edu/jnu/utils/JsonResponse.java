package edu.jnu.utils;

import edu.jnu.response.controller.CtrlResEnum;
import edu.jnu.response.service.SrvResEnum;

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
    public JsonResponse(CtrlResEnum ctrlResEnum) {
        this.data = null;
        this.code = ctrlResEnum.getCode();
        this.msg = ctrlResEnum.getErrMsg();
    }

    public JsonResponse(CtrlResEnum ctrlResEnum, T data) {
        this.data = data;
        this.code = ctrlResEnum.getCode();
        this.msg = ctrlResEnum.getErrMsg();
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

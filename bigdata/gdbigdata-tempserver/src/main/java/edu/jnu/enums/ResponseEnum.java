package edu.jnu.enums;

public enum ResponseEnum {
    // code格式为xxxx，（除成功为200, 500）
    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    EXCEPTION(500, "未被考虑到的异常"),
    VALID_FAIL(600,"输入值未通过数据校验");

    private int code;
    private String errMsg;

    private ResponseEnum(int code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }

    public static String getErrMsg(int code) {
        for (ResponseEnum responseEnum : ResponseEnum.values()) {
            if (responseEnum.getCode() == code) {
                return responseEnum.errMsg;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

package edu.jnu.response.controller;

public enum CtrlResEnum {
    // code格式为xxxx，（除成功为200, 500）
    SUCCESS(200, "请求成功"),
    EXCEPTION(500, "未被考虑到的异常"),
    VALID_FAIL(600,"输入值未通过数据校验"),

    SELECTION_PHASE_FAIL(1010, "搜索失败,解密失败"),

    CREATE_USER_FAIL(2010, "创建用户失败，请检查输入值的类型"),

    GET_USER_INFO_FAIL(3010, "检索用户信息失败"),

    USER_NO_EXIST(4010, "用户不存在");



    private int code;
    private String errMsg;

    private CtrlResEnum(int code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }

    public static String getErrMsg(int code) {
        for (CtrlResEnum ctrlResEnum : CtrlResEnum.values()) {
            if (ctrlResEnum.getCode() == code) {
                return ctrlResEnum.errMsg;
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

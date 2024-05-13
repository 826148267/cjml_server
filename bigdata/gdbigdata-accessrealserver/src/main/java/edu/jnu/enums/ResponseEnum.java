package edu.jnu.enums;

public enum ResponseEnum {
    // code格式为xxxx，（除成功为200, 500）
    SUCCESS(200, "请求成功"),
    EXCEPTION(500, "未被考虑到的异常"),
    VALID_FAIL(600,"输入值未通过数据校验"),

    SELECTION_PHASE_FAIL(1010, "搜索阶段失败,结果R为null"),
    SELECTION_PHASE_FAIL_NOT_FIND_TARGET(1011, "搜索阶段失败,数据库中不存在目标id相关信息"),
    SELECTION_PHASE_FAIL_CANNOT_GET_FIELD(1012, "搜索阶段失败，获取field对象失败"),
    SELECTION_PHASE_FAIL_CANNOT_TRANSLATE(1013, "搜索阶段失败，从field对象中获得的数据无法转化为String"),
    SELECTION_PHASE_FAIL_HOMOMUL_FAIL(1014, "搜索阶段失败，同态乘法计算出现错误"),
    SELECTION_PHASE_FAIL_HOMOADD_FAIL(1015, "搜索阶段失败，同态加法计算出现错误"),

    UPDATE_PHASE_FAIL_COMPUTE_FRESH_CIPHERTEXT_FAIL(1021, "更新阶段失败，计算新密文时发生错误"),
    UPDATE_PHASE_FAIL_SET_FIELD_FAIL(1022, "更新阶段失败，重新设置实例字段错误");

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

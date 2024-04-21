package edu.jnu.response.service;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年02月14日 21:00
 */
public enum SrvResEnum {
    SUCCESS(200, "执行成功"),
    FAIL(400, "执行失败"),
    EXCEPTION(500, "未知异常"),

    CREATE_USER_FAIL_VALUE_TOO_LONG(501, "创建用户失败，输入的字段值太大"),
    CREATE_USER_FAIL_BAD_CALL_REAL_SERVER(502, "创建用户失败，服务器之间的通信错误"),
    CREATE_USER_FAIL_TO_CALL_API(503, "创建用户失败，调用osu后端服务器接口时出错"),
    CREATE_USER_FAIL_BAD_PARAMS_FORMAT(504, "创建用户失败，传入的参数格式不合法"),
    CREATE_USER_FAIL_BAD_ENCRYPT(505, "创建用户失败，加密参数时发生错误"),

    CREATE_DTO1_FAIL(100, "osu协议创建第一阶段DTO失败"),
    OSU_PARAM_VALID_FAIL(101, "osu协议系统参数未通过检验"),
    OSU_GET_V_DEC_FAIL(102, "osu协议通过R获取V时解密失败"),
    OSU_GET_PLAIN_DEC_FAIL(103, "osu协议通过V获取明文值时解密失败"),
    ENC_USERINFO_FAIL(104, "加密用户信息时失败");

    private int code;
    private String errMsg;

    private SrvResEnum(int code, String errMsg){
        this.code = code;
        this.errMsg = errMsg;
    }

    public static String getErrMsg(int code) {
        for (SrvResEnum srvResEnum : SrvResEnum.values()) {
            if (srvResEnum.getCode() == code) {
                return srvResEnum.errMsg;
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

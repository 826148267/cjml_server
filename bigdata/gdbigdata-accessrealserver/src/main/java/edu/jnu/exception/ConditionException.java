package edu.jnu.exception;

import edu.jnu.enums.ResponseEnum;

/**
 * @author Guo zifan
 * @date 2022年01月24日 19:58
 */
public class ConditionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    public ConditionException(ResponseEnum responseEnum) {
        super(responseEnum.getErrMsg());
        this.code = responseEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

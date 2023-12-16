package edu.jnu.exception;

/**
 * @author Guo zifan
 * @version 1.0
 * @date 2022年06月16日 10:06
 */
public class UnknownStorageTypeException extends RuntimeException {

    public UnknownStorageTypeException() {
        super("不支持此存储类型");
    }

    public UnknownStorageTypeException(String errMsg) {
        super(errMsg);
    }
}

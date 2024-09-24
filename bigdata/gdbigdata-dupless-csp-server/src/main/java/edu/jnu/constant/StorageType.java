package edu.jnu.constant;

/**
 * 存储类型常量
 */
public enum StorageType {
    OSS(1); // 存储在阿里云对象存储OSS中

    private int code;

    private StorageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

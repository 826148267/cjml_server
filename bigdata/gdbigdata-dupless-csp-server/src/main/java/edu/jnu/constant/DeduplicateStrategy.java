package edu.jnu.constant;

/**
 * 去重策略的常量
 */
public enum DeduplicateStrategy {
    SAVE_WITH_DEDUPLICATE(1),   // 存储前去重
    SAVE_WITHOUT_DEDUPLICATE(2);    // 存储后去重

    private int code;

    private DeduplicateStrategy(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

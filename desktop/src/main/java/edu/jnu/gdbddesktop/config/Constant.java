package edu.jnu.gdbddesktop.config;


/**
 * @作者: 郭梓繁
 * @邮箱: 826148267@qq.com
 * @版本: 1.0
 * @创建日期: 2023年05月01日 22时54分
 * @功能描述: 存放程序用到的常量
 */
public enum Constant {
    // 登录地址
    LOGIN_URL("http://localhost:10003/login"),
    // 注册地址
    REGISTER_URL("http://localhost:10003/register"),
    // 获取用户信息地址
    GET_USER_INFO_URL("http://localhost:10002/access/users"),
    // 上传数据文件地址
    UPLOAD_DATA_FILE_URL("http://localhost:10004/uploadDataFile"),
    // 上传标签文件和审计参数地址
    UPLOAD_SIGN_AND_PARAMS_URL("http://localhost:10004/uploadSignFile/noDedup"),
    // 上传审计参数地址
    UPLOAD_PARAMS_URL("http://localhost:10004/uploadSignFile/canDedup"),

    GET_USER_FILE_LIST_URL("http://localhost:10004/files"),

    DOWNLOAD_FILE_URL("http://localhost:10004/download"),
    // 注册场景
    REGISTER("register"),
    // 登陆场景
    LOGIN("login"),
    // 核心业务场景
    CORE("main"), GET_AUDIT_PARAMS_URL("http://localhost:10004/auditParams"), POST_CHALLENGES_URL("http://localhost:10004/proof");

    public final String value;


    Constant(String value) {
        this.value = value;
    }
}

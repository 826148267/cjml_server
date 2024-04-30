//
// Created by root on 4/19/24.
//

#ifndef CJML_SERVER_XFRESPONSE_H
#define CJML_SERVER_XFRESPONSE_H

#include "string"

using namespace std;

enum class XfStatus {
    NoThing = 0, // 啥也还没干
    Continue = 100, // 继续请求
    OK = 200,   // 指令响应成功
    BadCmd = 400,   // 客户端的错误指令
    InternalServerError = 500,  // 服务器内部错误
    NotImplemented = 501    // 尚未实现的指令
};

class XfResponse {
private:
    int statusCode_ = static_cast<int>(XfStatus::NoThing);
    string body_;
public:
    XfResponse();
    XfResponse(XfStatus status, string body);
};

#endif //CJML_SERVER_XFRESPONSE_H

//
// Created by root on 4/19/24.
//

#ifndef CJML_SERVER_XFPROTOCOL_H
#define CJML_SERVER_XFPROTOCOL_H


#include "cjml/proto/XfRequest.pb.h"
#include "cjml/proto/XfResponse.pb.h"
#include <event2/bufferevent.h>
#include <event2/buffer.h>


namespace cjml {
    class XfProtocol {
    public:
        /**
         * 获取缓冲区中的请求
         * 如果len字段为0，则说明读取失败
         * 如果len字段不为0，则说明读取成功
         * @param bev
         * @return
         */
        static XfRequest getRequest(struct bufferevent *bev);

        /**
         * 发送响应报文到客户端
         * @param response
         */
        static void sendResponse(struct bufferevent *bev, XfResponse& response);

    private:
        static size_t getContextLen(struct bufferevent *bev);
    };
}

#endif //CJML_SERVER_XFPROTOCOL_H

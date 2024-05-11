//
// Created by root on 4/19/24.
//
#include <event2/bufferevent.h>
#include <event2/buffer.h>
#include <iostream>
#include <cjml/session/XfProtocol.h>
#include <boost/algorithm/string.hpp>
#include <vector>
#include <cstdlib>

using namespace std;

namespace cjml
{
    XfRequest XfProtocol::getRequest(struct bufferevent *bev) {
        XfRequest request;
        request.set_len(0);
        string content;
        // 读取消息长度
        size_t len = getContextLen(bev);
        cout << "长度：" << len << endl;

        if (len <= 0 || len >= 100000)
        {
            return request;
        }
        
        // 根据消息长度从缓冲区中读取完整报文
        char buf[1];
        while (true)
        {
            size_t n = bufferevent_read(bev, buf, sizeof(buf));
            cout << "buf：" << buf[0] << endl;
            if (buf[0] == '\n')
            {
                break;
            }
            else
            {
                content = content + buf[0];
                --len;
            }
        }

        cout << "报文：" << content << endl;
        cout << "长度：" << len << endl;

        if (len != 0)
        {
            request.set_len(0);
            return request;
        }

        request.ParseFromString(content);
        return request;
    }

    size_t XfProtocol::getContextLen(struct bufferevent *bev) {
        char buf[4]; // 缓冲区，用于存储读取的数据
        size_t n; // 读取的字节数
        size_t len = 0;

        // 从输入缓冲区中读取一个字符
        n = bufferevent_read(bev, buf, sizeof(buf));

        // 如果读取四个字节
        if (n != 4) {
            cout << "数据报长度字段为" << buf << "，字段长度为" << n
            << ",规定长度字段串长度为4，" << "此数据报已被丢弃～" << endl;
            return 0;
        }
        try
        {
            len = stoi(buf);
        }
        catch(const std::exception& e)
        {
            cout << "数据报长度字段为" << buf << "，非法字段格式，格式应如0001、9999等" << endl;
            std::cerr << "Exception: " << e.what() << '\n';
            return 0;
        }
        return len;
    }

    void XfProtocol::sendResponse(bufferevent* bev ,XfResponse& res) {
        cout << "在发送响应报文中..." << endl;
        string context = res.SerializeAsString();
        cout << context << endl;
        // 由于char*是一个指针，所以sizeof(res.getBody().c_str())只会传前八个字节
        bufferevent_write(bev, context.c_str(), context.size());
        cout << "响应报文发送完毕！" << endl;
    }
} // namespace cjml
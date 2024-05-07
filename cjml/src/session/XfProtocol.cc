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
        vector<string> vec;
        string context;
        // 读取消息长度
        size_t len = getContextLen(bev);
        cout << "长度：" << len << endl;

        // 根据消息长度从缓冲区中读取完整报文
        if (len >= 1024) {
            return {len, "", "", ""};
        }
        while (len > 0) {
            size_t k = len > 1024 ? 1024 : len;
            vector<char> buffer(k);
            size_t n = bufferevent_read(bev, buffer.data(), k);
            context.append(buffer.data(), buffer.size());
            len -= n;
        }

        cout << "读完了" << context << endl;

        boost::split(vec, context, boost::is_any_of(","));
        cout << "从报文中解析出" << vec.size() << "段数据！" << endl;
        // 初始化请求体
        // 将报文封装到请求体中

        if (vec.size() != 3) {
            return {0, "", "", ""};
        }
        return {len, vec[0], vec[1], vec[2]};
    }

    size_t XfProtocol::getContextLen(struct bufferevent *bev) {
        char buf[1]; // 缓冲区，用于存储读取的数据
        size_t n; // 读取的字节数
        string result;
        size_t len = 0;

        while (true) {
            // 从输入缓冲区中读取一个字符
            n = bufferevent_read(bev, buf, sizeof(buf));

            // 如果读取成功，则判断是否为 ' '
            if (n > 0) {
                // 打印读取的字符
                cout << "Read character: " << buf[0] << endl;
                // 如果读取的字符为 ' '，则停止读取
                if (buf[0] == ' ') {
                    len = strtoul(result.c_str(), nullptr, 10);
                    cout << "报文长度:" << len << endl;
                    break;
                } else if (buf[0] >= '0' && buf[0] <= '9'){
                    result.append(buf, 1);
                    len = strtoul(result.c_str(), nullptr, 10);
                    // 如果超过上线长度了，也停止读取
                    if (len > 1024) break;
                } else {
                    len = 9999999;
                    break;
                }
            }
        }
        return len;
    }

    void XfProtocol::sendResponse(struct bufferevent* bev ,XfResponse& res) {
        cout << "在发送响应报文中..." << endl;
        cout << res.getBody().c_str() << endl;
        // 由于char*是一个指针，所以sizeof(res.getBody().c_str())只会传前八个字节
        bufferevent_write(bev, res.getBody().c_str(), sizeof(res.getBody()));
        cout << "响应报文发送完毕！" << endl;
    }
} // namespace cjml



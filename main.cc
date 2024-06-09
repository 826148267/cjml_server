#include <event2/bufferevent.h>
#include <event2/event.h>
#include <event2/listener.h>
#include <cjml/Handler.h>
#include <iostream>
#include <vector>

using namespace std;
using namespace cjml;

/**
 * 接收到监听请求之后，根据传输内容中的指令进行路由.
 *
 * @param listener
 * @param fd
 * @param sa
 * @param socklen
 * @param user_data
 */
static void listener_cb(struct evconnlistener *listener, evutil_socket_t fd,
                      struct sockaddr *sa, int socklen, void *user_data);
/**
 * 路由函数
 * 根据用户发送的数据报指令路由至目标函数
 * @param bev
 * @param user_data
 */
static void router_cb(struct bufferevent *bev, void *user_data);

int main() {
    struct event_base *base = event_base_new();
    struct sockaddr_in sin = {0};
    // 服务器端口号
    __uint16_t PORT = 9898;

    if (!base) {
        fprintf(stderr, "Could not initialize libevent!\n");
        return 1;
    }

    sin.sin_family = AF_INET;
    sin.sin_port = htons(PORT);

    struct evconnlistener *listener = evconnlistener_new_bind(base, listener_cb, base,
                                       LEV_OPT_REUSEABLE|LEV_OPT_CLOSE_ON_FREE, -1,
                                       (struct sockaddr*)&sin,
                                       sizeof(sin));

    if (!listener) {
        fprintf(stderr, "Could not getSingleton a listener!\n");
        return 1;
    }

    // 派发事件
    event_base_dispatch(base);

    // 清理资源
    evconnlistener_free(listener);
    event_base_free(base);

    return 0;
}

void listener_cb(struct evconnlistener *listener, evutil_socket_t fd,
               struct sockaddr *sa, int socklen, void *user_data) 
{
    auto *base = static_cast<event_base *>(user_data);
    struct bufferevent *bev;

    bev = bufferevent_socket_new(base, fd, BEV_OPT_CLOSE_ON_FREE);
    if (!bev) {
        fprintf(stderr, "Error constructing bufferevent!");
        event_base_loopbreak(base);
        return;
    }
    bufferevent_setcb(bev, router_cb, nullptr, nullptr, nullptr);
    bufferevent_enable(bev, EV_READ);
}

void router_cb(struct bufferevent *bev, void *user_data) 
{
    Handler::handle(bev, user_data);
    // Handler::hadler();
}
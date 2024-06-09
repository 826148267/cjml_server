#ifndef CJML_SERVER_HANDLER_H
#define CJML_SERVER_HANDLER_H

#include <event2/bufferevent.h>
#include <event2/event.h>

namespace cjml 
{
    class Handler {
    public:
        static void handle(struct bufferevent *bev, void *user_data);
    };
}

#endif
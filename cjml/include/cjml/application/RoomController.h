//
// Created by root on 4/19/24.
//
#ifndef CJML_SERVER_ROOMCONTROLLER_H
#define CJML_SERVER_ROOMCONTROLLER_H
#include <string>
#include "cjml/general/BaseController.h"
#include "cjml/general/Factory.hpp"
#include "UserService.h"

namespace cjml {
    class RoomController: public BaseController{
    public:
        // 进入房间
        XfResponse inRoom(const string& body) const;
        // 退出房间
        XfResponse outRoom(const string& body) const;

        RoomController();

        virtual ~RoomController();
    private:
        UserService* userService;
    };
}
#endif //CJML_SERVER_ROOMCONTROLLER_H
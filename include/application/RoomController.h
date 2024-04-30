//
// Created by root on 4/19/24.
//
#ifndef CJML_SERVER_ROOMCONTROLLER_H
#define CJML_SERVER_ROOMCONTROLLER_H
#include "string"
#include "session/XfResponse.h"
#include "general/BaseController.h"
#include "general/ControllerFactory.h"

class RoomController: public BaseController{
public:
    // 进入房间
    XfResponse inRoom(const string& body) const;
    // 退出房间
    XfResponse outRoom(const string& body) const;

    RoomController();
};

REGISTER_CONTROLLER("RoomController", RoomController);
#endif //CJML_SERVER_ROOMCONTROLLER_H

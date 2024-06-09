//
// Created by root on 4/19/24.
#include <iostream>
#include "cjml/application/RoomController.h"

namespace cjml {
    XfResponse RoomController::inRoom(const string &body) const 
    {
        XfResponse res;
        res.set_state(200);
        res.set_state_msg("成功进入房间");
        return res;
    }

    XfResponse RoomController::outRoom(const string &body) const 
    {
        cout << "进入 outRoom 函数体" << endl;
        cout << "body:" << body << endl;
        string result = "448812588";
        XfResponse res;
        res.set_state(200);
        res.set_body(result);
        return res;
    }

    RoomController::RoomController() : BaseController() 
    {
        addFunction("RoomController/inRoom", [this](const string& body){ return this->inRoom(body);});
        addFunction("RoomController/outRoom", [this](const string& body){ return this->outRoom(body);});
        this->userService = new UserService();
    }

    RoomController::~RoomController()
    {
        delete userService;
    }
}
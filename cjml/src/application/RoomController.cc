//
// Created by root on 4/19/24.
#include <iostream>
#include <cjml/application/RoomController.h>


namespace cjml {
    XfResponse RoomController::inRoom(const string &body) const {
        cout << "进入 inRoom 函数体" << endl;
        cout << "this:" << this << endl;
        cout << "body:" << body << endl;
        
        return {XfStatus::OK, "inRoom 函数计算结果:"+body};
    }

    XfResponse RoomController::outRoom(const string &body) const {
        cout << "进入 outRoom 函数体" << endl;
        cout << "body:" << body << endl;
        return {XfStatus::OK, "outRoom 函数计算结果:"+body};
    }

    RoomController::RoomController() : BaseController() {
        addFunction("RoomController/inRoom", [this](const string& body){ return this->inRoom(body);});
        addFunction("RoomController/outRoom", [this](const string& body){ return this->outRoom(body);});
    }
}
//
// Created by root on 4/19/24.
#include <iostream>
#include <cjml/application/RoomController.h>
#include <UserInfo.pb.h>

namespace cjml {
    XfResponse RoomController::inRoom(const string &body) const {
        XfResponse res;
        UserInfo userInfo;
        try
        {
            userInfo.ParseFromString(body);
        }
        catch(const std::exception& e)
        {
            std::cerr << e.what() << endl;
            res.set_state(500);
            res.set_state_msg("服务器内部错误");
            return res;
        }
        if (userInfo.uname() == "zeavan" && 
            userInfo.pwd() == "448812588" &&
            userInfo.uid() == 826148267L)
        {
            res.set_state(200);
            res.set_state_msg("登陆成功");
            return res;
        } 
        res.set_state(400);
        res.set_state_msg("登陆失败,口令错误");
        return res;
    }

    XfResponse RoomController::outRoom(const string &body) const {
        cout << "进入 outRoom 函数体" << endl;
        cout << "body:" << body << endl;
        string result = "448812588";
        XfResponse res;
        res.set_state(200);
        res.set_body(result);
        return res;
    }

    RoomController::RoomController() : BaseController() {
        addFunction("RoomController/inRoom", [this](const string& body){ return this->inRoom(body);});
        addFunction("RoomController/outRoom", [this](const string& body){ return this->outRoom(body);});
    }
}
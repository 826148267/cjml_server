//
// Created by root on 4/21/24.
//
#include "cjml/general/BaseController.h"
namespace cjml {
    void BaseController::addFunction(const string &route, function<XfResponse(const string &)> funcObj) 
    {
        cout << "载入指令:" << route << endl;
        routeMap[route] = std::move(funcObj);
    }

    XfResponse BaseController::callFunction(const string &route, const string &body) 
    {
        auto it = routeMap.find(route);
        if (it != routeMap.end()) {
            return it->second(body);
        } else {
            cout << "非法指令:" << route << endl;
        }
        return {};
    }

    BaseController::BaseController() = default;
}
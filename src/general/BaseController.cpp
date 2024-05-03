//
// Created by root on 4/21/24.
//
#include "session/XfResponse.h"
#include "general/BaseController.h"

void BaseController::addFunction(const string &route, function<XfResponse(const string &)> funcObj) {
    routeMap[route] = std::move(funcObj);
}

XfResponse BaseController::callFunction(const string &route, const string &body) {
    auto it = routeMap.find(route);
    cout << "routes.size:" << routeMap.size() << endl;
    cout << "it:" << it->first << endl;
    if (it != routeMap.end()) {
        return it->second(body);
    } else {
        cout << "Invalid route!" << endl;
    }
    return {};
}

BaseController::BaseController() = default;
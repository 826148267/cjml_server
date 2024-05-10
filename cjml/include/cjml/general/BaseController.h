//
// Created by root on 4/21/24.
//

#ifndef CJML_SERVER_BASECONTROLLER_H
#define CJML_SERVER_BASECONTROLLER_H

#include <string>
#include <functional>
#include <map>
#include <iostream>
#include <XfResponse.pb.h>



using namespace std;

namespace cjml {
    class BaseController {
    public:
        /**
         * 添加路由与对函数指针到路由映射表中
         * @param route
         * @param lambda
         */
        void addFunction(const string& route, function<XfResponse (const string&)> funcObj);

        /**
         * 根据路由调用指定函数
         * @param route
         * @param body
         * @return
         */
        XfResponse callFunction(const string& route, const string& body);

        BaseController();
    private:
        /**
         * 无论实现多少个控制器子类
         * 所有控制器都共用一个map
         * 此路由映射表仅能够在此类内直接访问
         */
        map<string, function<XfResponse (const string& body)>> routeMap;
    };
}
#endif //CJML_SERVER_BASECONTROLLER_H

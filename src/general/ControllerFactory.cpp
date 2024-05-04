//
// Created by root on 4/22/24.
//

#include "general/ControllerFactory.h"
#include <mutex>
#include <iostream>
#include "application/RoomController.h"

// 互斥锁
mutex mtx;

ControllerFactory &ControllerFactory::getInstance() 
{
    static ControllerFactory factory;
    return factory;
}

map<string, shared_ptr<BaseController>>& ControllerFactory::getInstances()
{
    static map<string, shared_ptr<BaseController> > instances;
    return instances;
}

shared_ptr<BaseController> ControllerFactory::getController(const string &className)
{
    auto &routeMap = getInstances();
    if (routeMap.find(className) != routeMap.end()) 
    {
        return routeMap[className];
    }
    cout << "找不到对应的控制器实例" << endl;
    return nullptr;
}

void ControllerFactory::registerController(const string &className, const shared_ptr<BaseController>& instance)
{
    // 互斥锁，防止并发插入使map处于未知状态
    lock_guard<mutex> lock(mtx);

    // 防止重复注册
    auto &routeMap = getInstances();
    if (routeMap.find(className) != routeMap.end())   return;
    cout << "插入" << className << "的实例" << endl;
    
    routeMap.insert({className, instance});
}
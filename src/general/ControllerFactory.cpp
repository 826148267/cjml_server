//
// Created by root on 4/22/24.
//

#include "general/ControllerFactory.h"

map<string, shared_ptr<BaseController>> ControllerFactory::instances;

ControllerFactory &ControllerFactory::getInstance() {
    static ControllerFactory factory;
    return factory;
}

void ControllerFactory::registerController(const string &className, const shared_ptr<BaseController>& instance) {
    instances.emplace(className, instance);
}

shared_ptr<BaseController> ControllerFactory::getSingleton(const string& className) {
    if (instances.find(className) != instances.end()) {
        return instances[className];
    } else return nullptr;
}

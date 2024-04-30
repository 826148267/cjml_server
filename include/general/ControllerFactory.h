//
// Created by root on 4/22/24.
//

#ifndef CJML_SERVER_CONTROLLERFACTORY_H
#define CJML_SERVER_CONTROLLERFACTORY_H

#include <memory>
#include "string"
#include "functional"
#include "BaseController.h"

using namespace std;

/**
 * 单例工厂
 */
class ControllerFactory {
public:
    static ControllerFactory& getInstance();

    /**
     * 控制器将自身的实例化能力提供给本类
     * @param className
     * @param lambda
     */
    void registerController(const string& className, const shared_ptr<BaseController>& instance);

    /**
     * 通过此方法创建的实例都是单例的
     * 饿汉模式
     * @param className
     * @return
     */
    shared_ptr<BaseController> getSingleton(const string& className);

    ControllerFactory(const ControllerFactory&) = delete;
    ControllerFactory(ControllerFactory&&) = delete;
private:
    ControllerFactory(){};
    // 控制器实例表
    static map<string, shared_ptr<BaseController>> instances;
};

#define REGISTER_CONTROLLER(name, className) \
    namespace { \
        struct className##Registrar { \
            className##Registrar() { \
                ControllerFactory::getInstance().registerController(name, make_shared<className>()); \
            } \
        }; \
        static className##Registrar registrar; \
    }
#endif //CJML_SERVER_CONTROLLERFACTORY_H

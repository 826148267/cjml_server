//
// Created by root on 4/22/24.
//

#ifndef CJML_SERVER_FACTORY_HPP
#define CJML_SERVER_FACTORY_HPP

#include <memory>
#include <string>
#include <functional>
#include "BaseController.h"

using namespace std;

namespace cjml 
{

    /**
     * 单例工厂
     */
    template<typename T>
    class Factory {
    public:
        /**
         * 获得工厂类的实例
         */
        static Factory& get()
        {
            static Factory instance;
            return instance;
        }

        template<typename subT>
        struct registrar 
        {
            registrar(const string& name)
            {
                if (get()._map.find(name) != get()._map.end())
                {
                    cout << "Controller name already exists" << endl;
                    return;
                }
                subT *p = new subT{};
                get()._map.emplace(name, p);
                cout << "register: " << name << endl;
            }
        };

        static T* produce(const string& name)
        {
            if (get()._map.find(name) == get()._map.end())
            {
                throw runtime_error("控制器："+name+"不存在");
            }
            return get()._map[name];
        }

        Factory(const Factory&) = delete;
        Factory(Factory&&) = delete;

        ~Factory()
        {
            for (auto& p : _map)
            {
                delete p.second;
            }
        }
    private:
        Factory(){};
        map<string, T*> _map = {};
    };
}

#define REGISTER_CONTROLLER(T, name) static Factory<BaseController>::registrar<T> reg_for_ctl_##T(name);
#endif //CJML_SERVER_FACTORY_HPP
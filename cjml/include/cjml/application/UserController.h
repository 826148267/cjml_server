#ifndef USERCONTROLLER_H
#define USERCONTROLLER_H

#include <string>
#include "cjml/general/BaseController.h"
#include "cjml/general/Factory.hpp"
#include "UserService.h"

using namespace std;

namespace cjml
{
    class UserController: public BaseController {
    public:
        UserController();

        virtual ~UserController();

        XfResponse login(const string& body) const;
    private:
        UserService* userService;
    };
}
#endif
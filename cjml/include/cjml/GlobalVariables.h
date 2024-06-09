#ifndef CJML_GLOBALVARIABLES_H
#define CJML_GLOBALVARIABLES_H

#include "general/Factory.hpp"
#include "application/RoomController.h"
#include "application/UserController.h"

namespace cjml
{
    REGISTER_CONTROLLER(RoomController, "RoomController");
    REGISTER_CONTROLLER(UserController, "UserController");
}

#endif //CJML_GLOBALVARIABLES_H
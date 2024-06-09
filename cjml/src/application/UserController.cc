#include "cjml/application/UserController.h"

namespace cjml
{
    UserController::UserController() : BaseController()
    {
        addFunction("UserController/login", [ this ](const string& body) { return login(body); });
        this->userService = new UserService();
    }

    UserController::~UserController()
    {
        delete this->userService;
    }

    XfResponse UserController::login(const string &body) const
    {
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

        if (this->userService->auth(userInfo))
        {
            res.set_state(200);
            res.set_state_msg("登陆成功");
        } 
        else
        {
            res.set_state(400);
            res.set_state_msg("登陆失败,口令错误");
        }
        return res;
    }
}

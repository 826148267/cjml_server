#ifndef USERSERVICE_H
#define USERSERVICE_H

#include "cjml/proto/UserInfo.pb.h"

namespace cjml
{
class UserService
{
private:
    /* data */
public:
    UserService(/* args */);
    ~UserService();
    bool auth(UserInfo &userInfo);
};
}

#endif
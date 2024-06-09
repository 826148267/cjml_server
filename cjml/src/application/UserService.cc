#include <mysql++/mysql++.h>
#include "cjml/application/UserService.h"

using namespace std;
namespace cjml
{
    UserService::UserService()
    {
    }

    UserService::~UserService()
    {
    }

    bool UserService::auth(UserInfo &userInfo)
    {
        // 从数据库中查询uid对应的pwd
        mysqlpp::Connection conn(false);
        cout << userInfo.uid() << endl;
        cout << userInfo.uname() << endl;
        cout << userInfo.pwd() << endl;
        if (conn.connect("cjml", "127.0.0.1", "cjml", "ii7A8QyhHyKihxXR", 3306))
        {
            cout << "connect mysql success" << endl;
            return true;
        }
        else
        {
            cout << "connect mysql failed" << endl;
            return false;
        }
        
        // 如果不匹配则返回false,反之返回true
        
        
    }
}

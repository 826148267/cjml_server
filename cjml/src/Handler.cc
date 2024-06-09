#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>
#include "cjml/Handler.h"
#include "cjml/proto/XfRequest.pb.h"
#include "cjml/proto/XfResponse.pb.h"
#include "cjml/session/XfProtocol.h"
#include "cjml/general/BaseController.h"
#include "cjml/general/Factory.hpp"
#include "cjml/GlobalVariables.h"

using namespace std;

namespace cjml
{
    void Handler::handle(bufferevent *bev, void *user_data)
    {
        cout << "---" << endl;
        XfRequest req = XfProtocol::getRequest(bev);
        const string& cmd = req.cmd();
        vector<string> vec;
        boost::split(vec, cmd, boost::is_any_of("/"));
        XfResponse res;
        if (vec.size() == 2) 
        {
            BaseController* controller;
            try
            {
                controller = Factory<BaseController>::produce(vec[0]);
            }
            catch(const std::exception& e)
            {
                cerr << e.what() << endl;
            }
            
            if (controller != nullptr) 
            {
                res = controller->callFunction(cmd, req.body());
            } 
            else 
            {
                res.set_state(404);
                res.set_state_msg("未能初始化的控制器："+vec[0]);
            }
        } else {
            res.set_state(404);
            res.set_state_msg("未能识别的指令");
        }
        XfProtocol::sendResponse(bev, res);
        cout << "请求处理完毕" << endl;
    }
}

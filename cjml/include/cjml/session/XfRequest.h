//
// Created by root on 4/19/24.
//

#ifndef CJML_SERVER_XFREQUEST_H
#define CJML_SERVER_XFREQUEST_H
#include <string>

using namespace std;

namespace cjml {
    class XfRequest {
    private:
        size_t len;
        string cmd;
        string header;
        string body;
    public:
        size_t getLen() const;

        void setLen(size_t len);

        const string &getCmd() const;

        const string &getHeader() const;

        const string &getBody() const;

        void setCmd(const string &basicString);

        void setHeader(const string &basicString);

        void setBody(const string &basicString);

        XfRequest(size_t len, const string &cmd, const string &header, const string &body);
    };
}
#endif //CJML_SERVER_XFREQUEST_H

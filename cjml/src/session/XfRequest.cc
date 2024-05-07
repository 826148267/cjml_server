//
// Created by root on 4/19/24.
//

#include <cjml/session/XfRequest.h>

namespace cjml {
    size_t XfRequest::getLen() const {
        return len;
    }

    void XfRequest::setLen(size_t len) {
        XfRequest::len = len;
    }

    const string &XfRequest::getCmd() const {
        return cmd;
    }

    const string &XfRequest::getHeader() const {
        return header;
    }

    const string &XfRequest::getBody() const {
        return body;
    }

    XfRequest::XfRequest(size_t len, const string &cmd = nullptr, const string &header = nullptr, const string &body = nullptr) : len(len),
                                                                                                        cmd(cmd),
                                                                                                        header(header),
                                                                                                        body(body) {
        XfRequest::len = len;
        XfRequest::cmd = cmd;
        XfRequest::header = header;
        XfRequest::body = body;
    }

    void XfRequest::setCmd(const string &basicString) {
        XfRequest::cmd = basicString;
    }

    void XfRequest::setHeader(const string &basicString) {
        XfRequest::header = basicString;
    }

    void XfRequest::setBody(const string &basicString) {
        XfRequest::body = basicString;
    }

}


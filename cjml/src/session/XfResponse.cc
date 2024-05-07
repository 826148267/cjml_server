//
// Created by root on 4/19/24.
//
#include <cjml/session/XfResponse.h>

namespace cjml {
    XfResponse::XfResponse(XfStatus status, string body) :
            statusCode_(static_cast<int>(status)), body_(std::move(body)){}

    XfResponse::XfResponse() :
            statusCode_(static_cast<int>(XfStatus::NoThing)) {}


    int XfResponse::getStatusCode() const {
        return statusCode_;
    }

    const string& XfResponse::getBody() const {
        return body_;
    }

    void XfResponse::setStatusCode(int statusCode) {
        statusCode_ = statusCode;
    }

    void XfResponse::setBody(const string &body) {
        body_ = body;
    }
}
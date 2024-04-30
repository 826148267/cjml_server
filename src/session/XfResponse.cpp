//
// Created by root on 4/19/24.
//
#include "session/XfResponse.h"

XfResponse::XfResponse(XfStatus status, string body) :
        statusCode_(static_cast<int>(status)), body_(std::move(body)){}

XfResponse::XfResponse() :
        statusCode_(static_cast<int>(XfStatus::NoThing)) {}

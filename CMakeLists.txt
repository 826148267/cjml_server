cmake_minimum_required(VERSION 3.22)
project(cjml_server)

set(CMAKE_CXX_STANDARD 11)

set(OPENSSL_ROOT_DIR "/usr/local/openssl")

include_directories(include src /usr/include)
link_directories(/usr/local/lib)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/lib)
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/lib)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)

add_executable(cjml_server src/main.cpp
        src/session/XfProtocol.cpp
        src/application/RoomController.cpp
        include/application/RoomController.h
        src/session/XfRequest.cpp
        include/session/XfRequest.h
        src/session/XfResponse.cpp
        include/session/XfResponse.h
        include/general/BaseController.h
        src/general/BaseController.cpp
        src/general/ControllerFactory.cpp
        include/general/ControllerFactory.h
)

target_link_libraries(cjml_server event)
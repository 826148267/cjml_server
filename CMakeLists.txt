cmake_minimum_required(VERSION 3.22)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
# 默认为发布模式
if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

# set(OPENSSL_ROOT_DIR "/usr/local/openssl")

# # 设置为调试模式
# set(CMAKE_BUILD_TYPE Debug)
# # -Wall开启所有警告 -ggdb告诉编译器生成适用于 GDB 调试器的调试信息
# add_compile_options(-Wall -ggdb)

project(cjml_server VERSION 1.0
                DESCRIPTION "超级玛丽服务器"
                LANGUAGES CXX)

add_subdirectory(cjml)

# 制作程序入口
add_executable(cjml_server main.cc)
target_link_libraries(cjml_server PRIVATE cjml event)
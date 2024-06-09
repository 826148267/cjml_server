cmake_minimum_required(VERSION 3.22)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
# 默认为发布模式
if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

# 将cmake文件夹添加到cmake的模块搜索路径
set(
    CMAKE_MODULE_PATH 
    ${CMAKE_CURRENT_LIST_DIR}/cmake 
    ${CMAKE_MODULE_PATH}
)

# 只采用新行为
cmake_policy(SET CMP0135 NEW)

project(cjml_server VERSION 1.0
                DESCRIPTION "超级玛丽服务器"
                LANGUAGES CXX)

add_subdirectory(cjml)

# 添加程序入口
add_executable(cjml_server main.cc)
# 链接cjml库
target_link_libraries(cjml_server PUBLIC cjml)
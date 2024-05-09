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

project(cjml_server VERSION 1.0
                DESCRIPTION "超级玛丽服务器"
                LANGUAGES CXX)

add_subdirectory(cjml)

# 制作程序入口
add_executable(cjml_server main.cc)
target_link_libraries(cjml_server PRIVATE cjml event)

# 引入Google test测试框架
set(DOWNLOAD_EXTRACT_TIMESTAMP true)

include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/03597a01ee50ed33e9dfd640b249b4be3799d395.zip
)
# For Windows: Prevent overriding the parent project's compiler/linker settings
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

enable_testing()

add_executable(
  hello_test
  tests/hello_test.cc
)
target_link_libraries(
  hello_test
  GTest::gtest_main
)

include(GoogleTest)
gtest_discover_tests(hello_test)
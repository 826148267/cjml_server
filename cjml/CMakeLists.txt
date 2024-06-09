# 添加头文件是为了在IDE去出现提示，不添加也能编译通过.file中读取的变量不会跨文件生效
file(GLOB_RECURSE srcs CONFIGURE_DEPENDS src/*.cc include/*.h include/*.cc include/*.hpp)
add_library(cjml STATIC ${srcs})

# 导入protobuf
find_package(Protobuf MODULE REQUIRED)
# 导入mysqlpp
find_package(mysqlpp MODULE REQUIRED)

# 链接相关库
target_link_libraries(cjml PRIVATE ${Protobuf_LIBRARIES} mysqlpp event)

# 此处设置include是为了让链接cjml库的目标、自身的其他文件夹 共享当前项目的include目录
target_include_directories(cjml PUBLIC include)

# 防止MS Windows找不到dll
include(${PROJECT_SOURCE_DIR}/cmake/output4MS.cmake)
outputDll2Dir("cjml")
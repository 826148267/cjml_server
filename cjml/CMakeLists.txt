# 添加头文件是为了在IDE去出现提示，不添加也能编译通过
file(GLOB_RECURSE srcs CONFIGURE_DEPENDS src/*.cc include/*.h proto/**.h proto/**.cc)
add_library(cjml STATIC ${srcs})

# 导入protobuf
find_package(Protobuf MODULE REQUIRED)
target_link_libraries(cjml PUBLIC ${Protobuf_LIBRARIES})

# 此处设置include是为了让链接cjml库的目标共享当前项目的include目录
target_include_directories(cjml PUBLIC include proto)

# 防止MS Windows找不到dll
include(${PROJECT_SOURCE_DIR}/cmake/output4MS.cmake)
outputDll2Dir("cjml")
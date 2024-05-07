# 添加头文件是为了在IDE去出现提示，不添加也能编译通过
file(GLOB_RECURSE srcs CONFIGURE_DEPENDS src/*.cc include/*.h)

add_library(cjml STATIC ${srcs})

include(${PROJECT_SOURCE_DIR}/cmake/output4MS.cmake)
# 防止MS Windows找不到dll
outputDll2Dir("cjml")

# 此处设置include是为了让链接cjml库的目标共享当前项目的include目录
target_include_directories(cjml PUBLIC include)

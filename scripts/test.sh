#!/bin/sh
cd /home/gzf/cjml_server || exit
rm -rf build_test

cd tests || exit
# 告诉cmake在上级目录
cmake -GNinja -S . -B ../build_test -DCMAKE_BUILD_TYPE=Release
cmake --build ../build_test --parallel 4
# 接受下列命令的控制台输出
cd ../build_test || exit
# 在后台执行./cjml_server，并将日志输出到cjml_server.log
echo "测试开始"
ctest --output-on-failure=true
echo "测试结束"
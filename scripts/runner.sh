#!/bin/sh
cd /home/gzf/cjml_server || exit
rm -rvf build

# 根据cjml/proto文件夹下的所有proto文件产生对应的头文件和源文件，并输出到cjml/proto目录下
# 遍历文件夹cjml/proto下的所有.proto文件，${proto_file}是文件名
for proto_file in cjml/proto/*.proto; do
    echo "Generating ${proto_file}'s code"
    protoc -I=cjml/proto --cpp_out=cjml/proto "${proto_file}"
done

# 告诉cmake在上级目录
cmake -GNinja -S . -B build -DCMAKE_BUILD_TYPE=Release
cmake --build build --parallel 4
# 杀掉进程
PORT=9898
server_pid=$(netstat -nlp | grep $PORT | awk '{print $7}' | awk -F"/" '{print $1}')
if [ -n "$server_pid" ]; then
    kill -9 "$server_pid" 
fi
# 接受下列命令的控制台输出
cd build || exit
# 在后台执行./cjml_server，并将日志输出到cjml_server.log
./cjml_server > cjml_server.log 2>&1 &
cd tests || exit
ctest
server_pid=$(netstat -nlp | grep $PORT | awk '{print $7}' | awk -F"/" '{print $1}')
if [ -n "$server_pid" ]; then
    kill -9 "$server_pid" 
fi
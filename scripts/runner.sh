#!/bin/sh
cd /home/gzf/cjml_server || exit
rm -rvf build
# 告诉cmake在上级目录
cmake -GNinja -S . -B build -DCMAKE_BUILD_TYPE=Release
cmake --build build --parallel 8
# 杀掉进程
PORT=9898
server_pid=$(netstat -nlp | grep $PORT | awk '{print $7}' | awk -F"/" '{print $1}')
if [ -n "$server_pid" ]; then
    kill -9 "$server_pid" 
fi
build/cjml_server
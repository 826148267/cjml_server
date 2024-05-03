#!/bin/sh
sh clean.sh
cmake .
make -j6
cd bin || exit
# 杀掉进程
PORT=9898
server_pid=$(netstat -nlp | grep $PORT | awk '{print $7}' | awk -F"/" '{print $1}')
if [ -n "$server_pid" ]; then
    kill -9 "$server_pid" 
fi
./cjml_server
#!/bin/sh
cd /home/gzf/cjml_server/build || exit
# /snap/bin/valgrind --tool=memcheck --log-file=log.txt --leak-check=yes -s ./cjml_server
# 如果需要更加详细的分析信息可以打开开关
/snap/bin/valgrind --tool=memcheck --log-file=log-details.txt --leak-check=full  --show-leak-kinds=all ./cjml_server
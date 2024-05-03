#!/bin/sh
cd /home/gzf/cjml_server/bin || exit
/snap/bin/valgrind --tool=memcheck --log-file=log.txt --leak-check=yes -s ./cjml_server
/snap/bin/valgrind --tool=memcheck --log-file=log-details.txt --leak-check=full  --show-leak-kinds=all ./cjml_server
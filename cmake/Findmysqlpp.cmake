FIND_PATH(MYSQLPP_INCLUDES mysql++/mysql++.h 
    PATHS /usr/local/include
    NO_DEFAULT_PATH
)

FIND_LIBRARY(MYSQLPP_ONLY_LIBRARIES 
    NAMES mysqlpp 
    PATHS /usr/local/lib
    NO_DEFAULT_PATH
)

# mysql++中以标准库的方式#include <mysql.h>使用，需要include文件夹来抵消mysql文件夹
include_directories(/usr/include/mysql)

if (MYSQLPP_INCLUDES AND MYSQLPP_ONLY_LIBRARIES AND MYSQL_FOUND)
    set (MYSQLPP_FOUND true)
endif(MYSQLPP_INCLUDES AND MYSQLPP_ONLY_LIBRARIES AND MYSQL_FOUND)

if (MYSQLPP_FOUND)
    set(MYSQLPP_INCLUDE_DIR ${MYSQL_INCLUDE_DIR} ${MYSQLPP_INCLUDES})
    set(MYSQLPP_LIBRARIES ${MYSQL_LIBRARIES} ${MYSQLPP_ONLY_LIBRARIES})
    if (NOT MYSQLPP_FIND_QUIETLY)
        message(STATUS "Found mysqlpp: ${MYSQLPP_LIBRARIES}")
    endif(NOT MYSQLPP_FIND_QUIETLY)
else (MYSQLPP_FOUND)
   if (MYSQLPP_FIND_REQUIRED)
      message(FATAL_ERROR "Could not find mysqlpp")
    endif(MYSQLPP_FIND_REQUIRED)
endif(MYSQLPP_FOUND) 
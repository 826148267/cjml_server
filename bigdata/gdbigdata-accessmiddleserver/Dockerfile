#基础镜像
FROM openjdk:17-oracle

MAINTAINER gzf<826148267@qq.com>

#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD gdbigdata-accessmiddleserver-1.0-SNAPSHOT.jar /gdbigdata/accessmiddleserver/gdbigdata-accessmiddleserver-1.0-SNAPSHOT.jar

#指定容器启动时要启动的命令
#ENTRYPOINT ["mkdir","/gdbigdata/accessrealserver/log"]
#ENTRYPOINT ["cd","/gdbigdata/accessrealserver"]

#工作目录
#WORKDIR /gdbigdata/accessrealserver

#容器卷 主要是怕运维人员忘记-v了，有了它会匿名挂载起来，而不会乱写到容器的存储层中
VOLUME ["/gdbigdata/accessmiddleserver"]

#就是我们平时写的 -p
EXPOSE 10002

#镜像运行时需要运行的命令
CMD ["java","-jar","/gdbigdata/accessmiddleserver/gdbigdata-accessmiddleserver-1.0-SNAPSHOT.jar","&"]

#基础镜像
FROM openjdk:17-oracle
#维护者，一般写姓名+邮箱
MAINTAINER gzf<826148267@qq.com>
#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD gdbigdata-tempserver-1.0-SNAPSHOT.jar /gdbigdata/tempserver/gdbigdata-tempserver-1.0-SNAPSHOT.jar
#指定容器启动时要启动的命令
#ENTRYPOINT ["mkdir","/gdbigdata/tempserver/log"]
#ENTRYPOINT ["cd","/gdbigdata/tempserver"]
#工作目录
WORKDIR /gdbigdata/tempserver
#容器卷 主要是怕运维人员忘记-v了，有了它会匿名挂载起来，而不会乱写到容器的存储层中
VOLUME ["/gdbigdata/tempserver"]
#就是我们平时写的 -p
EXPOSE 17399
#镜像运行时需要运行的命令
CMD ["java","-jar","/gdbigdata/tempserver/gdbigdata-tempserver-1.0-SNAPSHOT.jar",">","gdbd-ts-log.txt","&"]
#指定容器启动时要启动的命令，但CMD只有最后一行会生效
#CMD
#类似ADD， 将我们的文件拷贝到镜像中
#COPY
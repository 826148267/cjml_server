感谢您对本项目的关注～～  
请选择您的语言：
- [中文](README_zh.md)
- [English](README.md)

# 安全存储系统

## 简介

本项目是一个基于完整性审计技术和密文去重的安全存储系统，旨在提供一个安全、可靠的文件存储和管理解决方案。该系统采用了轻量级可去重的密文完整性审计方法，确保用户数据的安全性和完整性。

## 特性

- **安全存储**：所有文件均经过加密处理，确保数据在传输和存储过程中的安全性。
- **密文去重**：系统支持文件去重，节省存储空间，提高存储效率。
- **完整性审计**：通过密文完整性审计，确保文件在存储过程中的完整性，防止数据篡改。
- **隐私信息检索**：通过不经意存取，确保用户的访问行为也能保证机密性，其安全性高于通常讨论的语义安全。
- **用户友好界面**：提供直观易用的用户界面，方便用户进行文件上传、下载和管理。

## 隐私信息检索
基于以下文章中的不经意选择与更新协议（OSU protocol）进行设计。
> [Enabling_Efficient_Secure_and_Privacy-Preserving_Mobile_Cloud_Storage.pdf](https://github.com/user-attachments/files/18230817/Enabling_Efficient_Secure_and_Privacy-Preserving_Mobile_Cloud_Storage.pdf)

## 密文去重

密文去重是一种在加密数据上进行去重的技术，旨在减少存储空间的同时保持数据的安全性。其工作原理如下：

1. **加密处理**：在文件上传之前，系统会对文件进行加密，生成密文。
2. **哈希计算**：系统对密文进行哈希处理，生成唯一的哈希值。相同内容的文件在加密后会生成相同的密文和哈希值。
3. **去重存储**：在存储新文件之前，系统会检查该文件的哈希值是否已存在。如果存在，系统将不会再存储该文件的副本，而是仅保留原始文件的引用。这种方式显著减少了存储需求，同时确保了数据的安全性。

## 完整性审计

完整性审计是一种确保数据在存储和传输过程中未被篡改的技术。其主要步骤包括：

1. **数据加密**：在文件上传之前，系统会对文件进行加密处理，确保数据在传输过程中的安全性。
2. **哈希校验**：系统在文件上传时，会生成文件的哈希值，并将其存储在数据库中。哈希值用于后续的完整性验证。
3. **定期审计**：系统会定期对存储的文件进行完整性审计。通过重新计算文件的哈希值并与存储的哈希值进行比较，系统可以检测文件是否被篡改。
4. **警报机制**：如果发现文件的哈希值与存储的哈希值不匹配，系统会触发警报，通知管理员进行进一步的调查和处理。

## 系统整体架构图
<p align="center">
   <img width="416" alt="image" src="https://github.com/user-attachments/assets/21fd4a8d-c8ed-4d8b-827a-e12cc5ec65d1" />
</p>
客户端系统采用javafx进行构建。后端采用基于springboot框架的应用服务器。后端共有4个模块，分别为gdbigdate-ldcia-server-v2、gdbigdata-access-middle-server-v2和gdbigdata-access-real-server-v2、gdbigdata-user-auth

## 项目目录

```plaintext
cjml_server
├── bigdata
│   ├── gdbiddate-ldcia-server-v2
│   ├── gdbigdata-access-middle-server-v2
│   ├── gdbigdata-access-real-server-v2
│   ├── gdbigdata-audit-csp-server
│   ├── gdbigdata-audit-tpa-server
│   ├── gdbigdata-dupless-csp-server
│   ├── gdbigdata-dupless-ks-server
│   ├── gdbigdata-eureka-17000
│   ├── gdbigdata-gateway-17001
│   ├── gdbigdata-tempserver
│   └── gdbigdate-user-auth
└── desktop
```

## 各应用作用
项目主要包含以下应用：

1. **gdbiddate-ldcia-server-v2**: 主要处理数据审计和完整性验证。
2. **gdbigdata-access-middle-server-v2**: 作为中间服务器，连接客户端和实际数据存储服务器。
3. **gdbigdata-access-real-server-v2**: 处理实际数据存储和检索。
4. **gdbigdata-audit-csp-server**: 主要处理数据存储的完整性证明和审计。
5. **gdbigdata-audit-tpa-server**: 处理TPA（第三方审计）相关功能。
6. **gdbigdata-dupless-csp-server**: 处理数据去重和存储任务。
7. **gdbigdata-dupless-ks-server**: 处理密钥管理和安全存储。
8. **gdbigdata-eureka-17000**: 服务注册与发现模块。
9. **gdbigdata-gateway-17001**: API网关模块。
10. **gdbigdata-tempserver**: 临时服务器，用于测试和开发。
11. **gdbigdate-user-auth**: 用户认证模块。
12. **desktop**: 桌面客户端应用。

## 配置文件风格

每个模块都有自己的配置文件，通常位于`src/main/resources`目录下：

- `application.yml`: 全局配置文件。
- `application-dev.yml`: 开发环境配置文件。
- `application-prod.yml`: 生产环境配置文件。

根据需要修改相应的配置文件以适应本地或生产环境。

## 服务器端部署说明
| 需要部署的服务器列表 |
| :------: |
| gdbigdate-ldcia-server-v2 |
| gdbigdata-access-middle-server-v2 |
| gdbigdata-access-real-server-v2 |
| gdbigdata-user-auth |
| Mysql 8.0.27 |
| redis |

### 服务器端代码
[https://github.com/826148267/cjml_server/tree/master/bigdata](https://github.com/826148267/cjml_server/tree/master/bigdata)

### MySQL

```bash
docker run --name gdbd-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --mount type=bind,src=/home/docker/mysql/conf/my.cnf,dst=/etc/mysql/my.cnf --mount type=bind,src=/media/Yang/DATA/mysql/datadir,dst=/var/lib/mysql --restart=on-failure:3 -d mysql:8.0.27
```

### Redis

```bash
docker run -p 30060:30060 --name myredis -v/home/Yang/桌面/docker-redis/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf
```

### gdbigdata-user-auth

```bash
#基础镜像
FROM openjdk:17-oracle
#维护者，一般写姓名+邮箱
MAINTAINER gzf<zeavango@gmail.com>
#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD gdbigdate-user-auth-1.0-SNAPSHOT.jar /gdbigdata/userauth/gdbigdate-user-auth-1.0-SNAPSHOT.jar
#指定容器启动时要启动的命令
#ENTRYPOINT ["mkdir","/gdbigdata/userauth/log"]
#ENTRYPOINT ["cd","/gdbigdata/userauth"]
#工作目录
#WORKDIR /gdbigdata/accessrealserver
#容器卷 主要是怕运维人员忘记-v了，有了它会匿名挂载起来，而不会乱写到容器的存储层中
VOLUME ["/gdbigdata/userauth"]
#就是我们平时写的 -p
EXPOSE 10003
#镜像运行时需要运行的命令
CMD ["java","-jar","/gdbigdata/userauth/gdbigdate-user-auth-1.0-SNAPSHOT.jar","&"]
```

### gdbigdate-ldcia-server-v2

```bash
#基础镜像
FROM openjdk:17-oracle
#维护者，一般写姓名+邮箱
MAINTAINER gzf<zeavango@gmail.com>
#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD target/ldcia-server-v2.jar /gdbigdata/ldcia/ldcia-server-v2.jar
ADD src/main/resources/a.properties /gdbigdata/ldcia/a.properties
#指定容器启动时要启动的命令
#ENTRYPOINT ["mkdir","/gdbigdata/ldcia/log"]
#ENTRYPOINT ["cd","/gdbigdata/ldcia"]
#工作目录
#WORKDIR /gdbigdata/ldcia
#容器卷 主要是怕运维人员忘记-v了，有了它会匿名挂载起来，而不会乱写到容器的存储层中
VOLUME ["/gdbigdata/ldcia"]
#就是我们平时写的 -p
EXPOSE 10004
#镜像运行时需要运行的命令
CMD ["java","-jar","/gdbigdata/ldcia/ldcia-server-v2.jar","&"]
```

### gdbigdata-access-real-server-v2
```bash
#基础镜像
FROM openjdk:17-oracle
#维护者，一般写姓名+邮箱
MAINTAINER gzf<zeavango@gmail.com>
#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD target/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar /gdbigdata/accessrealserver/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar
#指定容器启动时要启动的命令
#ENTRYPOINT ["mkdir","/gdbigdata/accessrealserver/log"]
#ENTRYPOINT ["cd","/gdbigdata/accessrealserver"]
#工作目录
#WORKDIR /gdbigdata/accessrealserver
#容器卷 主要是怕运维人员忘记-v了，有了它会匿名挂载起来，而不会乱写到容器的存储层中
VOLUME ["/gdbigdata/accessrealserver"]
#就是我们平时写的 -p
EXPOSE 10001
#镜像运行时需要运行的命令
CMD ["java","--add-opens=java.base/java.lang=ALL-UNNAMED","-jar","/gdbigdata/accessrealserver/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar","&"]
```

### gdbigdata-access-middle-server-v2

```bash
#基础镜像
FROM openjdk:17-oracle
#维护者，一般写姓名+邮箱
MAINTAINER gzf<zeavango@gmail.com>
#构建时设置环境变量
#ENV
#将jar包复制到镜像中，第一个变量为
ADD target/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar /gdbigdata/accessmiddleserver/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar
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
CMD ["java","-jar","/gdbigdata/accessmiddleserver/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar","&"]
```

## 安装

1. 克隆项目到本地：
   ```bash
   git clone git@github.com/826148267/cjml_server.git
   ```

2. 进入项目目录：
   ```bash
   cd gdbd-desktop
   ```

3. 安装依赖：
   Java9 以上环境（模块化编程需要，最好是Java17）

4. 启动系统：
   编译运行后，之前点击运行软件即可

## 使用

- 用户可以通过注册账户来使用系统。
- 登录后，用户可以上传文件，系统会自动进行加密和去重处理。
- 用户可以随时下载自己的文件，系统会确保文件的完整性。

## 功能展示

### 隐私信息检索
<p align="center">
   <img width="415" alt="image" src="https://github.com/user-attachments/assets/0670e127-a61d-43db-b59f-1fa6eb8cb3a9" />
</p>
<p align="center">
   <img width="415" alt="image" src="https://github.com/user-attachments/assets/498a32fe-a71b-48ae-96df-4fe54dbe1849" />
</p>
<p align="center">
   <img width="416" alt="image" src="https://github.com/user-attachments/assets/efc3518a-1c16-4ff0-a904-83d207dabb6f" />
</p>
<p align="center">
   <img width="416" alt="image" src="https://github.com/user-attachments/assets/d19591b0-bc69-4106-9a35-e8c49ea0a28c" />
</p>

### 密文去重相关功能
<p align="center">
   <img width="412" alt="image" src="https://github.com/user-attachments/assets/95f4c0cc-b245-4e40-8a84-fe2e18520f67" />
</p>
<p align="center">
   <img width="414" alt="image" src="https://github.com/user-attachments/assets/b87a3421-6c02-4c25-a250-78520c50a6d8" />
</p>

### 完整性审计相关功能
<p align="center">
   <img width="416" alt="image" src="https://github.com/user-attachments/assets/d6ab0f67-2851-4791-9099-8868ed0c5186" />
</p>
<p align="center">
   <img width="416" alt="image" src="https://github.com/user-attachments/assets/1b9235b0-45fe-4272-8366-1db69dbdf242" />
</p>

## 原理
<p align="center">
  <img width="482" alt="image" src="https://github.com/user-attachments/assets/e76f2a4b-429b-493f-9f83-fdfcbec99cc1" />
</p>
<p align="center">
  <img width="482" alt="image" src="https://github.com/user-attachments/assets/0df527ed-2c87-4b7e-abac-e224f0548b31" />
</p>
<p align="center">
  <img width="482" alt="image" src="https://github.com/user-attachments/assets/ecc5bf5a-7044-4c6c-8537-77cfc67095b4" />
</p>
轻量级可去重的密文完整性审计方案，其特征在于包括三种角色：数据拥有者、审计者以及存储服务提供商。

该方法大致分为三个阶段：

1. **初始化阶段**：
   - 数据拥有者初始化后，将各自对应的数据发送至存储服务提供商和审计者。

2. **文件上传阶段**：
   - 用户将原始数据进行预处理后，将预处理后的数据发送至存储服务提供商。
   - 存储服务提供商做出响应，数据拥有者根据响应结果判断是否应计算标签。
   - 如需计算，则产生标签；否则跳过标签产生的步骤，直接产生标签转化辅助材料以及审计材料发送给存储服务提供商。

3. **审计阶段**：
   - 审计者发送对目标文件的挑战给存储服务提供商。
   - 存储服务提供商对挑战进行响应，审计者对响应结果进行验证。

构建方法包括以下步骤：

1. **初始化阶段**：
   - 用户密钥的初始化，用于审计的公私钥对初始化等。

2. **文件上传阶段**：
   - 产生去重密钥，对原文件进行加密。
   - 对加密后的文件进行切块并编码。
   - 对去重密钥的加密，上传加密、切块并编码后的文件和加密后的去重密钥。
   - 对块数据计算并上传完整性标签（如果需要）、标签转化辅助材料以及审计材料至存储服务提供商等。

3. **审计阶段**：
   - 审计者发送挑战以及验证对挑战的响应结果的正确性。
   - 存储服务提供者利用自身持有的数据对挑战计算响应结果。
  
## 公式与正确性
[轻量级可去重的密文完整性审计方法.docx](https://github.com/user-attachments/files/18230674/_v2.docx)

## 相关依赖
- Spring Boot
- Spring Data JPA
- Spring Cloud
- MySQL Connector
- Redis
- Swagger
- Fastjson
- JUnit
- Lombok

## 贡献

欢迎任何形式的贡献！请提交问题、建议或拉取请求。

## 许可证

本项目采用 MIT 许可证，详细信息请参见 LICENSE 文件。

## 联系

如有任何问题，请联系 [zeavango@gmail.com] 或访问 [https://github.com/826148267]


# 大数据项目：服务器端与客户端

## 项目简介

大数据项目是一套包括服务器端和客户端的系统，用于处理和管理大数据相关的任务。项目采用了Spring Boot框架，使用了Maven作为构建工具，并集成了多种流行的Java库和技术，如MySQL、Redis、Swagger等。

## 项目架构

项目主要包含以下模块：

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

## 快速开始

以下是如何在本地环境中启动项目的步骤：

### 前提条件

- Java 17
- Maven 3.8.5
- MySQL
- Redis

### 克隆项目

```bash
git clone https://github.com/826148267/cjml_server.git
cd cjml_server
```

### 配置数据库

在`application.yml`或`application-dev.yml`中配置MySQL和Redis连接信息。

### 构建和运行项目

#### 使用Maven Wrapper

```bash
./mvnw clean install
```

#### 运行各个模块

```bash
# 进入各个模块的目录并运行
cd bigdata/gdbiddate-ldcia-server-v2
mvn spring-boot:run

cd ../gdbigdata-access-middle-server-v2
mvn spring-boot:run

cd ../gdbigdata-access-real-server-v2
mvn spring-boot:run

# 依次类推，启动其他模块
```

### 运行桌面客户端

```bash
cd desktop
mvn javafx:run
```

## 配置文件

每个模块都有自己的配置文件，通常位于`src/main/resources`目录下：

- `application.yml`: 全局配置文件。
- `application-dev.yml`: 开发环境配置文件。
- `application-prod.yml`: 生产环境配置文件。

根据需要修改相应的配置文件以适应本地或生产环境。

## 项目结构

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

## 主要依赖

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

欢迎贡献代码！请提交Pull Request或Issue。

## 许可证

本项目采用MIT许可证，详情请参见[LICENSE](LICENSE)。

## 联系方式

如果您有任何问题或建议，请通过[826148267@qq.com](mailto:826148267@qq.com)联系我们。

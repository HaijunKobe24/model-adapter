# Model Adapter (模型适配器)

[![Java](https://img.shields.io/badge/Java-8-blue.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.15-green.svg)](https://spring.io/projects/spring-boot)
[![gRPC](https://img.shields.io/badge/gRPC-1.37.0-orange.svg)](https://grpc.io/)
[![License](https://img.shields.io/badge/License-Private-yellow.svg)]()

Model Adapter是一个企业级多模块Maven项目，提供数据库抽象和模型管理功能，专注于教材内容管理和课程数据适配。该项目采用微服务架构设计，支持gRPC和REST API，集成了教材发布系统(iPublish)、课程管理和内容同步等核心功能。

## 🚀 核心特性

- **多模块架构**：清晰的模块分离，支持独立部署和扩展
- **教材管理**：完整的教材、章节、单元数据模型和API
- **远程服务集成**：集成iPublish教材发布系统和课程管理服务
- **多协议支持**：同时支持gRPC和REST API
- **消息队列**：基于Kafka的异步消息处理
- **数据同步**：支持教材内容的实时同步和版本管理
- **Spring Boot生态**：完整的Spring Boot自动配置和Starter
- **gRPC拦截器**：内置日志记录和异常处理拦截器

## 🏗️ 项目架构

```
model-adapter/
├── model-adapter-api/           # 主API服务 (REST + gRPC)
├── model-adapter-base/          # 核心数据层 (JPA实体和Repository)
├── model-adapter-client/        # gRPC客户端和Proto定义
├── model-adapter-consumer/      # Kafka消息消费者
└── model-adapter-remote-spring-boot-starter/  # 远程服务集成Starter
```

### 核心模块说明

| 模块 | 职责 | 技术栈 |
|------|------|--------|
| **model-adapter-api** | REST API、gRPC服务、业务逻辑 | Spring Boot, Spring MVC, gRPC |
| **model-adapter-base** | 数据层、实体模型、Repository | Spring Data JPA, MySQL/H2 |
| **model-adapter-client** | gRPC客户端、Protocol Buffers、DTO转换 | gRPC, Protobuf, Lombok |
| **model-adapter-consumer** | 消息消费、异步处理 | Spring Kafka |
| **model-adapter-remote-starter** | 远程服务SDK、自动配置 | Spring Boot Starter, WebClient |

## 🛠️ 技术栈

- **核心框架**：Spring Boot 2.5.15, Spring Data JPA
- **API协议**：REST API, gRPC 1.37.0, Protocol Buffers 3.7.1
- **数据库**：MySQL 8.0+ (生产), H2 (开发/测试)
- **消息队列**：Apache Kafka
- **远程调用**：Spring WebClient, gRPC
- **构建工具**：Maven 3.6+
- **Java版本**：Java 8
- **代码生成**：Lombok, MapStruct 1.5.5
- **认证授权**：JWT (Nimbus JOSE JWT 4.15)

## 📦 快速开始

### 环境要求

- Java 8+
- Maven 3.6+
- MySQL 8.0+ (生产环境)
- Protocol Buffers 编译器 (protoc)

### 构建和运行

```bash
# 克隆项目
git clone https://git.unipus.cn/birdflock/model-adapter.git
cd model-adapter

# 编译项目
mvn clean compile

# 构建所有模块
mvn clean package

# 安装到本地仓库
mvn clean install

# 运行API服务
cd model-adapter-api
mvn spring-boot:run

# 运行API服务 (指定环境)
mvn spring-boot:run -Dspring.profiles.active=prod
```

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定模块测试
mvn test -pl model-adapter-base

# 运行特定测试类
mvn test -Dtest=IPublishBookServiceTest
```

## 🔌 API文档

### REST API

**服务地址**：`http://localhost:8081`  
**上下文路径**：`/api/adapter`  
**完整基础URL**：`http://localhost:8081/api/adapter`

> **注意**：所有REST API端点都需要包含上下文路径 `/api/adapter` 作为前缀。这是在 `application.yml` 中通过 `server.servlet.context-path` 配置的。

#### 主要接口

- **教材管理**
  - `POST /api/adapter/ipublish/copyBook` - 教材复制
    - 参数: `openId` (用户openId), `sourceBookId` (被复制的教材ID)
  - `POST /api/adapter/ipublish/book/publish` - 发布教材
    - 参数: `bookId` (教材ID)
  
- **节点管理**
  - `POST /api/adapter/ipublish/node/add` - 添加教材单元节点
    - 参数: `bookId` (教材ID), `unitName` (节点名称), `openId` (用户openId)

- **健康检查和监控**
  - `GET /api/adapter/actuator/health` - 应用健康状态
  - `GET /api/adapter/actuator/info` - 应用信息

#### API测试

项目提供了完整的cURL命令集合，位于 `model-adapter-api/doc/curl.txt`

```bash
# 教材复制示例
curl -X POST "http://localhost:8081/api/adapter/ipublish/copyBook" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "openId=user123&sourceBookId=book123"

# 添加教材单元节点示例
curl -X POST "http://localhost:8081/api/adapter/ipublish/node/add" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "bookId=book123&unitName=新单元&openId=user123"

# 发布教材示例
curl -X POST "http://localhost:8081/api/adapter/ipublish/book/publish" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "bookId=book123"

# 健康检查示例  
curl -X GET "http://localhost:8081/api/adapter/actuator/health"
```

### gRPC API

**服务端口**: `6565`  
**Reflection**: 已启用 (可用于测试)

#### 核心服务

**CourseService** - 课程管理服务
- `createUnit` - 创建单元
- `publishCourse` - 发布课程
- `updateUnitName` - 更新单元名称
- `deleteUnit` - 删除单元

Protocol Buffers定义文件位于 `model-adapter-client/src/main/proto/`

#### gRPC拦截器

项目内置了gRPC拦截器链：

1. **GrpcLogInterceptor** (`@Order(1)`) - 日志记录拦截器
   - 记录请求响应耗时
   - 记录入参和出参（支持JSON格式化）
   - 记录异常信息

2. **GrpcExceptionInterceptor** (`@Order(2)`) - 异常处理拦截器
   - 统一异常处理
   - 将异常转换为标准错误响应
   - 支持多种异常类型

```bash
# 使用grpcurl测试 (需要先安装grpcurl)
grpcurl -plaintext localhost:6565 list
grpcurl -plaintext localhost:6565 describe CourseService
```

## 📊 数据模型

### 核心实体

- **Book** - 教材基础信息，包含refId外部引用
- **BookUnit** - 教材单元，支持状态管理 (0=草稿, 2=已发布) 和排序
- **Course** - 课程信息
- **Content** - 教材内容节点

### 数据库设计

项目支持条件过滤：

```java
// 根据教材ID查询
List<BookUnit> units = repository.findByBookId("book123");
```

## 🚀 配置说明

### 应用配置

**主配置文件**：`model-adapter-api/src/main/resources/application.yml`

```yaml
server:
  port: 8081
  servlet:
    context-path: /api/adapter
    application-display-name: model-adapter

spring:
  application:
    name: ${server.servlet.application-display-name}
  profiles:
    include: common,mq

# gRPC配置
grpc:
  enabled: true
  port: 6565
  enable-reflection: true
```

### 远程服务配置

#### iPublish服务集成

```yaml
http:
  ipublish:
    enabled: true
    base-url: https://ipublish-test.unipus.cn/api
    timeout: 3
    token-header: authorization
    app-id: 1
    reader-type: 2
    uri:
      access-token-uri: /backend/auth/getAccessToken
      get-book-struct-uri: /backend/book/getBookStruct?bookId=%s
      copy-content-uri: /reader/customContent/copyPublishedCustomContentByBizIds
      get-published-content-uri: /reader/customContent/getPublishedCustomContentByBizIds
      get-editing-content-uri: /reader/customContent/getEditingCustomContentByBizIds
      publish-content-uri: /reader/customContent/publishCustomContent
      save-content-uri: /reader/customContent/saveCustomContent
```

#### 课程服务集成

```yaml
http:
  course:
    enabled: true
    base-url: http://testucontent.unipus.cn
    timeout: 3
    struct-sync-uri: /api/v1/ipublish/struct/sync
    token:
      secret: your-jwt-secret
      iss: your-issuer
      aud: edx.unipus.cn
      header-name: x-annotator-auth-token
```

### 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://10.103.6.172:3306/model_adapter_test?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useAffectedRows=true&serverTimezone=Asia/Shanghai&useSSL=false
    username: examuser
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
      validation-timeout: 5000
      max-lifetime: 1800000
      connection-test-query: SELECT 1
      auto-commit: true
  jpa:
    hibernate:
      ddl-auto: validate  # 生产环境使用validate
      naming:
        implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
    properties:
      hibernate:
        use-new-id-generator-mappings: false
        format_sql: true
        use_sql_comments: false
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
```

### Kafka配置

```yaml
kafka:
  servers: 10.103.6.146:9092,10.103.6.243:9092,10.103.6.71:9092
  producer:
    retries: 0
    batch-size: 4096
    buffer-memory: 40960
    linger: 1
  consumer:
    enabled: true
    session-timeout: 15000
    poll-timeout: 4000
    enable-auto-commit: true
    auto-commit-interval: 100
    auto-offset-reset: earliest
    ipublish-content:
      topic: test-ipublish-custom-content-Published
      group: model-adapter
      concurrency: 1
```

## 🔄 集成指南

### 使用Remote Starter

在其他项目中集成model-adapter功能：

```xml
<dependency>
    <groupId>cn.unipus</groupId>
    <artifactId>model-adapter-remote-spring-boot-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### gRPC客户端集成

```xml
<dependency>
    <groupId>cn.unipus</groupId>
    <artifactId>model-adapter-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 使用示例

```java
// 注入远程服务模板
@Autowired
private IPublishTemplate iPublishTemplate;

@Autowired
private CourseTemplate courseTemplate;

// 注入gRPC客户端
@Autowired
private CourseClient courseClient;

// 使用iPublish服务
BookStructDTO bookStruct = iPublishTemplate.getBookStruct("book123");

// 使用课程服务
StructSyncResponse response = courseTemplate.syncStruct(request);

// 使用gRPC客户端
CreateUnitRequestDTO request = new CreateUnitRequestDTO();
request.setBookId("book123");
request.setName("新单元");
CreateUnitResponseDTO response = courseClient.createUnit(request);
```

## 📈 监控和运维

### 健康检查

- `GET /api/adapter/actuator/health` - 应用健康状态
- `GET /api/adapter/actuator/info` - 应用信息

### 日志和监控

- **gRPC日志**: 自动记录所有gRPC调用的耗时和参数
- **异常监控**: 统一的异常处理和记录
- **应用日志**: 基于Logback的结构化日志输出

### 性能优化

- **连接池优化**: HikariCP数据库连接池配置
- **gRPC优化**: 支持连接池和反射调试
- **本地缓存**: 使用Guava Cache进行令牌缓存
- **动态查询**: 支持动态条件查询，减少数据库负载
- **批量操作**: 数据库批量插入和更新优化

## 🐳 容器化部署

### Docker构建

```bash
# 使用项目内置的构建脚本
./model-adapter-api/builds/build.sh <version> <environment>

# 手动构建
docker build -f model-adapter-api/builds/docker/Dockerfile -t model-adapter:latest .
```

### Jenkins CI/CD

项目配置了完整的Jenkins流水线：

```groovy
// 参考 model-adapter-api/builds/JenkinsTestfile
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'sh model-adapter-api/builds/build.sh ${BUILD_NUMBER} test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'sh model-adapter-api/builds/deploy/deploy.sh test_ma-api_v${BUILD_NUMBER} test model-adapter/model-adapter-api'
            }
        }
    }
}
```

## 🧪 测试指南

### 单元测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=IPublishBookServiceTest

# 运行带有特定标签的测试
mvn test -Dgroups=integration
```

### 集成测试

项目提供了完整的测试用例：

- `IPublishBookServiceTest` - 教材服务测试
- `IPublishTemplateTest` - 远程服务模板测试

### API测试

```bash
# 使用cURL测试REST API
curl -X POST "http://localhost:8081/api/adapter/ipublish/copyBook" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "openId=user123&sourceBookId=book123"

# 使用grpcurl测试gRPC API
grpcurl -plaintext -d '{"bookId":"book123","name":"新单元"}' \
  localhost:6565 cn.unipus.modelAdapter.api.proto.client.service.CourseService/createUnit
```

## 🔧 开发指南

### 代码规范

- **Java 8 兼容性**: 确保代码兼容Java 8
- **Lombok使用**: 减少样板代码，使用`@Data`、`@Slf4j`等注解
- **MapStruct映射**: 使用MapStruct进行对象映射
- **Spring Boot最佳实践**: 遵循Spring Boot官方建议
- **gRPC服务**: 使用`@GRpcService`注解定义服务
- **拦截器顺序**: 使用`@Order`注解控制拦截器执行顺序

### 新增功能流程

1. **定义数据模型**: 在`model-adapter-base`中定义JPA实体
2. **实现Repository**: 创建Spring Data JPA Repository
3. **定义服务接口**: 在`model-adapter-api`中定义Service接口
4. **实现业务逻辑**: 创建Service实现类
5. **创建API端点**: 实现REST Controller或gRPC Service
6. **更新Proto文件**: 如需要gRPC支持，更新Protocol Buffers定义
7. **编写测试用例**: 创建单元测试和集成测试
8. **更新文档**: 更新API文档和使用说明

### 依赖版本管理

项目使用严格的版本管理：

```xml
<properties>
    <spring-boot.version>2.5.15</spring-boot.version>
    <grpc.version>1.37.0</grpc.version>
    <protobuf.version>3.7.1</protobuf.version>
    <org.mapstruct.version>1.5.5.Final</org.mapstruct.version>
    <jwt.version>4.15</jwt.version>
</properties>
```

### 开发调试

```bash
# 使用gRPC反射
grpcurl -plaintext localhost:6565 list

# 查看应用健康状态
curl http://localhost:8081/api/adapter/actuator/health

# 查看配置信息
curl http://localhost:8081/api/adapter/actuator/configprops

# 启用SQL日志调试
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 📝 版本历史

### 1.0.0-SNAPSHOT (当前版本)
- ✅ 基础教材管理功能
- ✅ iPublish系统集成
- ✅ gRPC服务支持 (端口6565)
- ✅ Kafka消息处理
- ✅ gRPC拦截器链 (日志记录 + 异常处理)
- ✅ JWT认证集成
- ✅ Docker容器化部署
- ✅ Jenkins CI/CD流水线
- ✅ 数据库连接池优化 (HikariCP)

### 最新更新 (当前版本)
- 🆕 新增课程服务gRPC客户端SDK (`CourseClient`)
- 🆕 添加类型安全的DTO层和自动转换工具 (`ModelConverter`)
- 🆕 更新gRPC服务操作：支持单元创建、发布、更新和删除
- 🆕 集成Lombok依赖，简化DTO代码
- 🆕 优化消息消费者代码结构和可读性
- 🆕 统一接口调用方式，提高代码一致性

### 未来计划
- 🔄 Redis缓存集成
- 🔄 API限流功能
- 🔄 GraphQL API支持
- 🔄 Kubernetes部署配置
- 🔄 监控指标增强

## 🏢 许可证

本项目为私有项目，版权归 https://github.com/HaijunKobe24 所有。

## 📞 联系方式

- **开发团队**：HaijunKobe24
- **项目地址**：https://github.com/HaijunKobe24/model-adapter.git
- **文档地址**：详见项目内README.md文件
- **Nexus仓库**：http://nexus-hw.unipus.cn:8081/nexus/

---

> **注意**：本项目处于活跃开发阶段，API可能会发生变化。生产使用前请确保充分测试。
> 
> **开发提醒**：
> - 确保Protocol Buffers编译器已安装
> - 数据库连接配置需要根据环境调整
> - gRPC端口6565需要确保防火墙开放
> - 生产环境建议禁用gRPC反射功能
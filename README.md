# 高并发社交平台

基于微服务架构的高并发社交平台，采用前后端分离设计。

## 技术栈

### 前端
- Vue 3
- Ant Design Vue
- Vite
- TypeScript
- Pinia 状态管理
- Vue Router

### 后端微服务
- Spring Boot 3.x
- Spring Cloud Alibaba
- Spring Security + JWT
- MyBatis-Plus
- MySQL 8.0 (分库分表 Sharding-JDBC)
- Redis 7.x
- Kafka
- WebSocket
- Elasticsearch

### 运维部署
- Docker
- Kubernetes
- Nginx
- Jenkins
- Prometheus + Grafana

## 项目结构
```
social-platform/
├── frontend/                # Vue 3 前端项目
├── admin/                   # React Admin 管理后台
├── services/               # 微服务模块
│   ├── gateway/           # API 网关服务
│   ├── auth/              # 认证授权服务
│   ├── user/              # 用户服务
│   ├── message/           # 消息服务
│   ├── notification/      # 通知服务
│   └── search/            # 搜索服务
├── common/                # 公共模块
└── deploy/                # 部署配置
    ├── docker/           
    └── k8s/              
```

## 系统架构

### 微服务划分
- Gateway Service: API 网关，负责路由转发、限流、认证
- Auth Service: 统一认证中心，JWT token 管理
- User Service: 用户管理，个人信息维护
- Message Service: 消息发布、点赞、评论
- Notification Service: WebSocket 实时通知
- Search Service: 消息搜索服务

### 数据库设计
- 用户数据库: 分库分表设计
- 消息数据库: 按时间范围分表
- Redis: 缓存热点数据、Session 管理
- Elasticsearch: 消息全文检索

### 高可用设计
- 服务注册发现: Nacos
- 负载均衡: Nginx + Spring Cloud LoadBalancer
- 限流熔断: Sentinel
- 分布式事务: Seata
- 消息队列: Kafka

## 开发环境要求
- JDK 17+
- Node.js 18+
- Maven 3.8+
- Docker
- Kubernetes
- MySQL 8.0
- Redis 7.0

## 快速开始

详细的环境搭建和开发指南请参考各个服务目录下的 README.md 文件。

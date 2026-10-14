# Paitrick Blog

一个面向云原生实践的前后端分离博客 MVP：单体、无状态、配置外置。它刻意不引入 Redis、消息队列或微服务，以便把 Docker、Kubernetes、TKE/TCR/COS 和 CI/CD 实践建立在清晰的业务载体上。

## 架构与技术栈

- `blog-server`：Java 21、Spring Boot 3、Spring MVC/Validation/Security、JWT、MyBatis、Flyway、MySQL 8、Actuator。
- `blog-web`：Vue 3、TypeScript、Vite、Vue Router、Pinia、Axios、Marked。
- 请求由 Nginx 转发到无状态 API；文章正文只保存 Markdown，图片只保存 URL。
- `StorageService` 是业务与对象存储的边界。目前是 Mock 实现；后续可替换为 COS 实现，无需修改文章业务。

## 目录

```text
blog-server/src/main/java/com/paitrick/blog/
  common/       # 响应、异常、安全与配置
  auth/ article/ category/ tag/ storage/  # 按业务模块分层
blog-server/src/main/resources/db/migration/V1__init.sql
blog-web/src/   # Vue 公共页面与 /admin 管理页
docker-compose.yml
```

## 本地运行

准备 MySQL 8 数据库，然后设置环境变量（可复制 `.env.example` 作为参考）：

```powershell
$env:DB_HOST='localhost'; $env:DB_PORT='3306'; $env:DB_NAME='blog'
$env:DB_USERNAME='blog'; $env:DB_PASSWORD='your-password'
$env:JWT_SECRET='use-a-long-random-secret-at-least-32-characters'
$env:ADMIN_USERNAME='admin'; $env:ADMIN_PASSWORD='your-admin-password'
cd blog-server; mvn spring-boot:run
# 新终端
cd blog-web; npm install; npm run dev
```

前端开发地址为 Vite 输出的地址；健康检查为 `http://localhost:8080/actuator/health`。Flyway 会自动执行 `V1__init.sql`。

## Docker

```powershell
Copy-Item .env.example .env
# 编辑 .env，必须替换所有 change-me 值
# 生成足够长的 JWT_SECRET 后：
docker compose up -d --build
```

浏览 `http://localhost:8088`，管理端从 `/admin/login` 进入。Compose 包含 MySQL 数据卷、API 健康检查和 Web 反向代理。

## 数据库设计

迁移创建 `admin_user`、`article`、`category`、`tag` 和 `article_tag`。`slug` 为唯一索引；文章另有 status、published_at、category_id 索引；关联表有双向查询索引。为降低线上迁移、历史修复和删除策略的耦合，表之间未建立物理外键；应用服务负责关联写入，所有 join 列均有索引。

管理员第一阶段由 `ADMIN_USERNAME` / `ADMIN_PASSWORD` 环境变量认证，故不会提交初始明文密码。`admin_user` 已被创建，后续可增加密码哈希与管理员管理迁移。

## API 摘要

- 公开：`GET /api/articles`、`GET /api/articles/{slug}`、`GET /api/categories`、`GET /api/tags`
- 登录：`POST /api/auth/login`，请求体 `{ "username", "password" }`
- 管理（Bearer JWT）：`/api/admin/articles`（CRUD、`POST /{id}/publish`）、`/api/admin/categories`（CRUD）、`/api/admin/tags`（CRUD）

响应统一为 `{ "code": 0, "message": "success", "data": ... }`。

## 下一阶段建议

接入 COS 的 `StorageService`、增加管理员密码哈希持久化、补充集成测试和 CI，然后再增加镜像仓库与 Kubernetes manifests。

# Big Event

Big Event 是一个展示新闻大事件的项目，提供文章的分类、查看、发布、修改和删除等功能。该项目使用 Spring Boot 开发，结合 MySQL 和 Redis 实现数据存储与缓存。

## 功能

- **文章分类**：支持按分类展示文章。
- **文章查看**：用户可以查看具体的文章内容。
- **文章发布**：管理员可以发布新的文章。
- **文章修改**：管理员可以修改已有的文章。
- **文章删除**：管理员可以删除文章。

## 环境要求

- **Java**：需要安装 Java 17 或更高版本。
- **MySQL**：需要安装 MySQL 8 或更高版本。
- **Redis**：需要安装 Redis 环境，用于缓存。
- **Maven**：用于构建项目。

## 数据库配置

1. 在 MySQL 中创建数据库 `big-event`，并执行 `sql` 文件夹中的 SQL 脚本来初始化数据库结构。

2. 修改 `src/main/resources/application.yml` 文件中的数据库密码，确保密码与 MySQL 配置一致。

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/big-event?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: <your-database-password>
```

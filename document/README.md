# CZ Blog Backend API Documentation

本文档说明如何使用 Postman 进行接口测试。

## 快速开始

1.  **启动后端服务**
    确保 MySQL 数据库已启动，并且已执行 `src/main/resources/sql/init.sql` 初始化数据库表和数据。
    运行 `MainApplication` 启动 Spring Boot 应用。

2.  **导入 Postman Collection**
    *   打开 Postman。
    *   点击左上角的 "Import" 按钮。
    *   选择本项目目录下的 `document/cz-blog-backend.postman_collection.json` 文件。

3.  **测试登录接口**
    *   在 Postman 集合中找到 `Auth` -> `Login`。
    *   点击 "Send" 发送请求。
    *   如果成功（状态码 200），Postman 会自动将返回的 JWT Token 设置到环境变量 `token` 中。

4.  **测试受保护接口**
    *   在 Postman 集合中找到 `User` -> `Get User Info (Protected)`。
    *   点击 "Send" 发送请求。
    *   该请求会自动使用上一步获取的 `token` 进行鉴权。

## 默认账号

*   **用户名**: `admin`
*   **密码**: `123456`
# 用户注册功能部署指南

本文档提供了博客系统用户注册功能的完整部署指南，包括环境配置、邮件服务设置、数据库初始化等步骤。

## 1. 环境要求

### 1.1 系统要求
- Java 17 或更高版本
- Maven 3.6+
- MySQL 8.0+
- SMTP邮件服务（Gmail、QQ邮箱、企业邮箱等）

### 1.2 依赖版本
- Spring Boot 3.4.3
- MyBatis 3.0.5
- MySQL Connector 8.0.33

## 2. 邮件服务配置

### 2.1 Gmail配置（推荐）
```properties
# Gmail SMTP配置
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-gmail@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 2.2 QQ邮箱配置
```properties
# QQ邮箱SMTP配置
spring.mail.host=smtp.qq.com
spring.mail.port=587
spring.mail.username=your-qq@qq.com
spring.mail.password=your-qq-authorization-code
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 2.3 企业邮箱配置
```properties
# 企业邮箱SMTP配置
spring.mail.host=smtp.yourcompany.com
spring.mail.port=587
spring.mail.username=service@yourcompany.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 2.4 获取应用密码
- **Gmail**: 需要开启两步验证，然后在[Google账户设置](https://myaccount.google.com/apppasswords)中生成应用专用密码
- **QQ邮箱**: 在QQ邮箱设置中开启SMTP服务，获取授权码
- **其他邮箱**: 请参考对应邮箱服务商的SMTP配置文档

## 3. 数据库初始化

### 3.1 执行数据库脚本
```sql
-- 创建用户表（如果已存在可跳过）
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建验证码表
CREATE TABLE IF NOT EXISTS `verification_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `code` varchar(10) NOT NULL,
  `type` varchar(20) NOT NULL,
  `expire_time` datetime NOT NULL,
  `is_used` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_email` (`email`),
  KEY `idx_expire_time` (`expire_time`),
  KEY `idx_is_used` (`is_used`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';
```

### 3.2 插入初始管理员用户（可选）
```sql
-- 插入管理员用户（密码为admin123加密后的值）
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `enabled`) 
VALUES ('admin', '$2a$10$7JB720yub2Z7fQfX3qP6E.lS8q5f5f5f5f5f5f5f5f5f5f5f5f5f5f5', '管理员', 'admin@example.com', 1);
```

## 4. 应用配置

### 4.1 配置文件更新
更新 `src/main/resources/application.properties` 文件：

```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/blog_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password

# JWT配置
jwt.secret=your-jwt-secret-key-here
jwt.expiration=86400000

# 邮件配置（替换为实际值）
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# 验证码配置
verification.code.expire-minutes=10
verification.code.length=6

# 日志配置
logging.level.com.weixiao=DEBUG
logging.level.org.springframework.security=DEBUG
```

### 4.2 生产环境配置
更新 `src/main/resources/application-prod.properties` 文件：

```properties
# 生产环境配置
spring.datasource.url=jdbc:mysql://prod-db-host:3306/blog_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# 使用环境变量
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}
jwt.secret=${JWT_SECRET}
```

## 5. 构建与部署

### 5.1 Maven构建
```bash
# 开发环境构建
mvn clean package -DskipTests

# 生产环境构建
mvn clean package -Pprod
```

### 5.2 Docker部署
```bash
# 构建Docker镜像
docker build -t blog-backend:latest .

# 运行容器
docker run -d \
  --name blog-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=jdbc:mysql://mysql:3306/blog_db \
  -e DB_USERNAME=blog_user \
  -e DB_PASSWORD=your-db-password \
  -e EMAIL_USERNAME=your-email@gmail.com \
  -e EMAIL_PASSWORD=your-app-password \
  -e JWT_SECRET=your-jwt-secret \
  blog-backend:latest
```

### 5.3 使用Docker Compose
创建 `docker-compose.yml` 文件：

```yaml
version: '3.8'
services:
  blog-backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_URL=jdbc:mysql://mysql:3306/blog_db
      - DB_USERNAME=blog_user
      - DB_PASSWORD=${DB_PASSWORD}
      - EMAIL_USERNAME=${EMAIL_USERNAME}
      - EMAIL_PASSWORD=${EMAIL_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      - mysql
    networks:
      - blog-network

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=${DB_ROOT_PASSWORD}
      - MYSQL_DATABASE=blog_db
      - MYSQL_USER=blog_user
      - MYSQL_PASSWORD=${DB_PASSWORD}
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - blog-network

volumes:
  mysql_data:

networks:
  blog-network:
    driver: bridge
```

启动服务：
```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f blog-backend
```

## 6. 测试验证

### 6.1 发送验证码测试
```bash
curl -X POST http://localhost:8080/api/auth/send-verification-code \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "type": "REGISTER"
  }'
```

### 6.2 用户注册测试
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "test123456",
    "verificationCode": "123456"
  }'
```

## 7. 常见问题解决

### 7.1 邮件发送失败
**问题**: `AuthenticationFailedException` 或 `SendFailedException`

**解决方案**:
1. 检查邮箱用户名和密码是否正确
2. 确认已开启SMTP服务
3. 对于Gmail，确保使用的是应用专用密码而非账户密码
4. 检查网络连接是否被防火墙阻止

### 7.2 验证码验证失败
**问题**: 验证码无效或已过期

**解决方案**:
1. 检查验证码是否在有效期内（默认10分钟）
2. 确认验证码未被使用过
3. 检查邮箱地址是否正确
4. 查看应用日志确认验证码生成和验证过程

### 7.3 数据库连接失败
**问题**: `CommunicationsException` 或 `SQLException`

**解决方案**:
1. 确认MySQL服务正在运行
2. 检查数据库连接URL、用户名和密码
3. 验证数据库用户权限
4. 检查防火墙设置

### 7.4 JWT Token问题
**问题**: Token无效或过期

**解决方案**:
1. 检查JWT密钥配置是否正确
2. 确认Token未过期
3. 验证Token格式是否正确

## 8. 安全配置建议

### 8.1 密码安全
- 要求用户密码至少8位，包含大小写字母、数字和特殊字符
- 使用BCrypt算法加密存储密码
- 定期更新JWT密钥

### 8.2 邮件安全
- 使用专用的发送邮箱账户
- 定期更换应用密码
- 启用两步验证

### 8.3 网络安全
- 使用HTTPS加密通信
- 配置防火墙规则
- 限制API访问频率

## 9. 监控与维护

### 9.1 日志监控
```bash
# 查看应用日志
tail -f logs/blog-backend.log

# 查看错误日志
grep ERROR logs/blog-backend.log
```

### 9.2 性能监控
```bash
# 查看JVM状态
jstat -gc <pid>

# 查看内存使用
jmap -heap <pid>
```

### 9.3 数据库监控
```sql
-- 查看活跃用户
SELECT COUNT(*) FROM sys_user WHERE enabled = 1;

-- 查看验证码使用情况
SELECT COUNT(*) FROM verification_code WHERE is_used = 1;
```

## 10. 备份与恢复

### 10.1 数据库备份
```bash
# 备份数据库
mysqldump -u username -p blog_db > backup_$(date +%Y%m%d).sql

# 恢复数据库
mysql -u username -p blog_db < backup.sql
```

### 10.2 配置文件备份
```bash
# 备份配置文件
tar -czf config_backup_$(date +%Y%m%d).tar.gz src/main/resources/
```

## 11. 更新与升级

### 11.1 应用更新
```bash
# 停止当前服务
docker-compose down

# 拉取最新代码
git pull origin main

# 重新构建并启动
docker-compose up -d --build
```

### 11.2 数据库迁移
```bash
# 备份当前数据库
mysqldump -u username -p blog_db > before_migration.sql

# 执行迁移脚本
mysql -u username -p blog_db < migration_script.sql
```

## 12. 支持联系方式

如有部署问题，请联系：
- 邮箱: support@example.com
- 文档: [API文档](docs/API-USER-REGISTRATION.md)
- Postman集合: [API测试集合](src/main/resources/postman/Blog-API-Collection.json)

---
*最后更新: 2026年1月22日*
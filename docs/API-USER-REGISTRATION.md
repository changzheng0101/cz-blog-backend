# 用户注册API文档

本文档详细描述了博客系统的用户注册功能，包括邮箱验证码验证流程。

## 1. 接口概览

| 接口 | 方法 | 路径 | 描述 |
|------|------|------|------|
| 发送验证码 | POST | /api/auth/send-verification-code | 向指定邮箱发送验证码 |
| 用户注册 | POST | /api/auth/register | 使用邮箱验证码完成注册 |

## 2. 发送验证码接口

### 2.1 基本信息
- **URL**: `/api/auth/send-verification-code`
- **Method**: POST
- **Content-Type**: application/json

### 2.2 请求参数

```json
{
  "email": "user@example.com",
  "type": "REGISTER"
}
```

| 参数名 | 类型 | 是否必填 | 描述 |
|--------|------|----------|------|
| email | string | 是 | 用户邮箱地址，必须格式正确 |
| type | string | 是 | 验证码类型，注册时使用"REGISTER" |

### 2.3 响应结果

**成功响应**:
```json
{
  "code": "200",
  "msg": "success",
  "data": null
}
```

**错误响应**:
```json
{
  "code": "400",
  "msg": "邮箱已被注册",
  "data": null
}
```

### 2.4 使用示例

```bash
curl -X POST http://localhost:8080/api/auth/send-verification-code \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "type": "REGISTER"
  }'
```

## 3. 用户注册接口

### 3.1 基本信息
- **URL**: `/api/auth/register`
- **Method**: POST
- **Content-Type**: application/json

### 3.2 请求参数

```json
{
  "username": "testuser",
  "email": "user@example.com",
  "password": "password123",
  "verificationCode": "123456"
}
```

| 参数名 | 类型 | 是否必填 | 描述 |
|--------|------|----------|------|
| username | string | 是 | 用户名，3-20个字符 |
| email | string | 是 | 邮箱地址，必须格式正确 |
| password | string | 是 | 密码，6-20个字符 |
| verificationCode | string | 是 | 邮箱收到的验证码 |

### 3.3 响应结果

**成功响应**:
```json
{
  "code": "200",
  "msg": "success",
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTcwNTg2NzQ0MiwiZXhwIjoxNzA1ODcwNDQyfQ.1234567890abcdef"
}
```

**错误响应示例**:

邮箱已被注册:
```json
{
  "code": "400",
  "msg": "邮箱已被注册",
  "data": null
}
```

验证码无效:
```json
{
  "code": "400",
  "msg": "验证码无效或已过期",
  "data": null
}
```

用户名已存在:
```json
{
  "code": "400",
  "msg": "用户名已存在",
  "data": null
}
```

### 3.4 使用示例

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "user@example.com",
    "password": "password123",
    "verificationCode": "123456"
  }'
```

## 4. 注册流程说明

### 4.1 完整注册流程

1. **发送验证码**: 客户端调用发送验证码接口，向用户邮箱发送6位数字验证码
2. **用户输入**: 用户在注册页面输入用户名、邮箱、密码和收到的验证码
3. **提交注册**: 客户端调用注册接口，提交所有信息
4. **验证处理**: 服务端验证验证码有效性、用户名和邮箱唯一性
5. **创建用户**: 验证通过后创建用户并返回JWT Token

### 4.2 验证码规则

- **有效期**: 10分钟
- **使用次数**: 每个验证码只能使用一次
- **格式**: 6位纯数字
- **发送限制**: 同一邮箱1分钟内只能发送一次验证码

## 5. 配置说明

### 5.1 邮件配置

在 `application.properties` 中配置邮件服务器:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**注意**: 
- 将 `YOUR_EMAIL` 替换为实际的发送邮箱
- 将 `YOUR_APP_PASSWORD` 替换为邮箱的应用专用密码（不是登录密码）

### 5.2 验证码配置

```properties
verification.code.expire-minutes=10
verification.code.length=6
```

## 6. 错误码说明

| 错误码 | 描述 |
|--------|------|
| 200 | 成功 |
| 400 | 参数错误或业务错误 |
| 500 | 系统内部错误 |

## 7. 安全注意事项

1. **密码安全**: 密码在传输过程中应使用HTTPS加密
2. **验证码安全**: 验证码有效期短，且只能使用一次
3. **邮箱验证**: 确保邮箱格式正确，且未被注册
4. **频率限制**: 建议在前端实现验证码发送频率限制

## 8. 测试建议

1. **正常流程测试**: 测试完整的注册流程
2. **边界条件测试**: 测试各种错误情况
3. **并发测试**: 测试同一邮箱同时注册的情况
4. **验证码过期测试**: 测试验证码过期后的行为

## 9. 常见问题

### 9.1 邮件发送失败

**问题**: 邮件无法发送，日志显示认证失败

**解决方案**: 
1. 检查邮箱配置是否正确
2. 确认使用的是应用专用密码而非登录密码
3. 检查邮箱是否开启了SMTP服务

### 9.2 验证码收不到

**问题**: 用户收不到验证码邮件

**解决方案**:
1. 检查邮件是否被归类为垃圾邮件
2. 确认邮箱地址输入正确
3. 检查邮件服务器配置

### 9.3 验证码无效

**问题**: 用户输入验证码提示无效

**解决方案**:
1. 确认验证码在10分钟有效期内
2. 确认验证码未被使用过
3. 确认验证码输入正确（注意大小写和空格）
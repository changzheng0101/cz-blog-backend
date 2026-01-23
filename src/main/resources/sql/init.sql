use czBlog;

-- 用户表
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '状态 1:启用 0:禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码 (如 ROLE_ADMIN)',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 权限表 (菜单/按钮)
CREATE TABLE `sys_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint DEFAULT '0' COMMENT '父ID',
  `permission_code` varchar(100) DEFAULT NULL COMMENT '权限标识 (如 user:list)',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission_type` tinyint DEFAULT '1' COMMENT '类型 1:菜单 2:按钮',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径',
  `component` varchar(200) DEFAULT NULL COMMENT '前端组件',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 用户角色关联表
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色权限关联表
CREATE TABLE `sys_role_permission` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 初始数据
INSERT INTO `sys_user` (`username`, `password`, `nickname`) VALUES
('admin', '$2a$10$GmkQuZdXqnjwtojR/eTNFu7PyjQtnOJ.E1H4yvm4FnbZHInryiY5y', '超级管理员'); -- 密码为 123456

INSERT INTO `sys_role` (`role_code`, `role_name`, `description`) VALUES
('ROLE_ADMIN', '管理员', '拥有所有权限'),
('ROLE_USER', '普通用户', '拥有基本权限');

INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);


-- 文章表
CREATE TABLE article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    cover VARCHAR(500) COMMENT '封面图片地址',
    content LONGTEXT COMMENT '文章内容',
    title VARCHAR(255) NOT NULL COMMENT '文章标题',
    is_top TINYINT(1) DEFAULT 0 COMMENT '是否置顶 0-不置顶 1-置顶',
    status VARCHAR(32)  COMMENT '状态：PUBLISH-发表，DRAFT-草稿，DELETE-删除',
    labels VARCHAR(500) COMMENT '标签，多个标签用逗号分隔',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    category_id BIGINT COMMENT '分类ID',
    abstract_content TEXT COMMENT '摘要',
     created_by BIGINT  COMMENT '创建者用户ID',
    updated_by BIGINT  COMMENT '修改者用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    read_count INT DEFAULT 0 COMMENT '阅读次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章表';

-- 添加索引
CREATE INDEX idx_author_id ON article(author_id);
CREATE INDEX idx_category_id ON article(category_id);
CREATE INDEX idx_status ON article(status);
CREATE INDEX idx_is_top ON article(is_top);
CREATE INDEX idx_create_time ON article(create_time);
CREATE INDEX idx_created_by ON article(created_by);
CREATE INDEX idx_updated_by ON article(updated_by);

-- 插入测试文章数据
INSERT INTO article (cover, content, title, is_top, status, labels, author_id, category_id, abstract_content, read_count) VALUES
('https://example.com/images/spring-boot.jpg', '# Spring Boot 入门指南\n\nSpring Boot 是一个用于创建独立的、生产级别的 Spring 应用的框架。\n\n## 主要特性\n\n- 自动配置\n- 起步依赖\n- 命令行界面\n- Actuator\n\n## 快速开始\n\n```java\n@SpringBootApplication\npublic class Application {\n    public static void main(String[] args) {\n        SpringApplication.run(Application.class, args);\n    }\n}\n```\n\n## 总结\n\nSpring Boot 大大简化了 Spring 应用的开发和部署。', 'Spring Boot 入门指南', 1, 'PUBLISH', 'Spring Boot,Java,后端', 1, 1, 'Spring Boot 是一个用于创建独立的、生产级别的 Spring 应用的框架，本文将介绍其主要特性和快速开始方法。', 1250),

('https://example.com/images/microservices.jpg', '# 微服务架构设计\n\n微服务架构是一种将单一应用程序划分为一组小的服务的方法。\n\n## 微服务优势\n\n- 独立部署\n- 技术多样性\n- 故障隔离\n- 可扩展性\n\n## 设计原则\n\n### 单一职责\n每个服务应该只负责一个业务功能。\n\n### 自治性\n服务应该能够独立开发、部署和扩展。\n\n### 去中心化\n避免集中式的服务管理。\n\n## 实施策略\n\n1. 从单体应用开始\n2. 识别边界上下文\n3. 逐步拆分\n4. 建立通信机制\n\n## 总结\n\n微服务架构能够提高系统的可维护性和可扩展性，但也带来了复杂性。', '微服务架构设计', 0, 'PUBLISH', '微服务,架构设计,分布式', 1, 2, '微服务架构是一种将单一应用程序划分为一组小的服务的方法，本文将介绍其优势、设计原则和实施策略。', 890),

('https://example.com/images/docker.jpg', '# Docker 容器化实践\n\nDocker 是一个开源的应用容器引擎，让开发者可以打包应用及其依赖。\n\n## Docker 核心概念\n\n- 镜像（Image）\n- 容器（Container）\n- 仓库（Repository）\n\n## 基本命令\n\n```bash\n# 拉取镜像\ndocker pull nginx\n\n# 运行容器\ndocker run -d -p 80:80 nginx\n\n# 查看容器\ndocker ps\n\n# 停止容器\ndocker stop container_id\n```\n\n## Dockerfile 示例\n\n```dockerfile\nFROM openjdk:17\nCOPY target/app.jar app.jar\nENTRYPOINT ["java", "-jar", "app.jar"]\n```\n\n## Docker Compose\n\n```yaml\nversion: ''3.8''\nservices:\n  web:\n    build: .\n    ports:\n      - "8080:8080"\n  db:\n    image: mysql:8.0\n    environment:\n      MYSQL_ROOT_PASSWORD: root\n```\n\n## 总结\n\nDocker 简化了应用的部署和管理，是现代 DevOps 实践的重要组成部分。', 'Docker 容器化实践', 0, 'PUBLISH', 'Docker,容器化,DevOps', 1, 3, 'Docker 是一个开源的应用容器引擎，本文将介绍其核心概念、基本命令和 Dockerfile 示例。', 1560),

('https://example.com/images/spring-security.jpg', '# Spring Security 安全框架详解\n\nSpring Security 是一个功能强大且高度可定制的身份验证和访问控制框架。\n\n## 核心概念\n\n- 认证（Authentication）\n- 授权（Authorization）\n- 安全上下文（Security Context）\n\n## 基本配置\n\n```java\n@Configuration\n@EnableWebSecurity\npublic class SecurityConfig {\n    \n    @Bean\n    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {\n        http\n            .authorizeHttpRequests(auth -> auth\n                .requestMatchers("/public/**").permitAll()\n                .anyRequest().authenticated()\n            )\n            .formLogin(withDefaults());\n        return http.build();\n    }\n}\n```\n\n## JWT 集成\n\n```java\n@Component\npublic class JwtAuthenticationFilter extends OncePerRequestFilter {\n    \n    @Override\n    protected void doFilterInternal(HttpServletRequest request, \n                                  HttpServletResponse response, \n                                  FilterChain filterChain) throws ServletException, IOException {\n        // JWT 验证逻辑\n        filterChain.doFilter(request, response);\n    }\n}\n```\n\n## 总结\n\nSpring Security 提供了全面的安全解决方案，适用于各种规模的应用程序。', 'Spring Security 安全框架详解', 1, 'PUBLISH', 'Spring Security,认证,授权', 1, 1, 'Spring Security 是一个功能强大且高度可定制的身份验证和访问控制框架，本文将介绍其核心概念和配置方法。', 2100),

('https://example.com/images/mybatis.jpg', '# MyBatis 持久层框架最佳实践\n\nMyBatis 是一个优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。\n\n## 核心组件\n\n- SqlSessionFactory\n- SqlSession\n- Mapper 接口\n- XML 映射文件\n\n## 基本用法\n\n### 1. 配置\n\n```xml\n<configuration>\n    <environments default="development">\n        <environment id="development">\n            <transactionManager type="JDBC"/>\n            <dataSource type="POOLED">\n                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>\n                <property name="url" value="jdbc:mysql://localhost:3306/test"/>\n                <property name="username" value="root"/>\n                <property name="password" value="password"/>\n            </dataSource>\n        </environment>\n    </environments>\n</configuration>\n```\n\n### 2. Mapper 接口\n\n```java\n@Mapper\npublic interface UserMapper {\n    @Select("SELECT * FROM users WHERE id = #{id}")\n    User findById(Long id);\n    \n    @Insert("INSERT INTO users(name, email) VALUES(#{name}, #{email})")\n    @Options(useGeneratedKeys = true, keyProperty = "id")\n    void insert(User user);\n}\n```\n\n## 高级特性\n\n- 动态 SQL\n- 缓存机制\n- 插件扩展\n\n## 总结\n\nMyBatis 提供了灵活的 SQL 映射能力，适合需要精细控制 SQL 的场景。', 'MyBatis 持久层框架最佳实践', 0, 'PUBLISH', 'MyBatis,持久层,ORM', 1, 1, 'MyBatis 是一个优秀的持久层框架，本文将介绍其核心组件、基本用法和高级特性。', 1780),

('https://example.com/images/restful-api.jpg', '# RESTful API 设计指南\n\nRESTful API 是一种基于 REST 架构风格的 Web API 设计方法。\n\n## REST 原则\n\n- 资源导向\n- 统一接口\n- 无状态\n- 可缓存\n- 分层系统\n\n## 设计规范\n\n### 资源命名\n\n使用名词复数形式：\n- `/users` 而不是 `/user`\n- `/articles` 而不是 `/article`\n\n### HTTP 方法\n\n- GET: 获取资源\n- POST: 创建资源\n- PUT: 更新整个资源\n- PATCH: 更新部分资源\n- DELETE: 删除资源\n\n### 状态码\n\n- 200 OK\n- 201 Created\n- 400 Bad Request\n- 401 Unauthorized\n- 404 Not Found\n- 500 Internal Server Error\n\n## 版本控制\n\n```\n/api/v1/users\n/api/v2/users\n```\n\n## 总结\n\n良好的 API 设计能够提高开发效率和用户体验。', 'RESTful API 设计指南', 0, 'DRAFT', 'REST,API设计,Web服务', 1, 2, 'RESTful API 是一种基于 REST 架构风格的 Web API 设计方法，本文将介绍其设计原则和规范。', 0),

('https://example.com/images/vue-react.jpg', '# Vue vs React：前端框架对比\n\nVue 和 React 是目前最流行的两个前端框架，各有特点。\n\n## Vue.js\n\n### 优点\n\n- 学习曲线平缓\n- 模板语法直观\n- 双向数据绑定\n- 完整的解决方案\n\n### 缺点\n\n- 生态系统相对较小\n- 大型项目经验较少\n\n## React\n\n### 优点\n\n- 虚拟 DOM 性能优秀\n- 组件化思想成熟\n- 生态系统丰富\n- 社区活跃\n\n### 缺点\n\n- 学习曲线陡峭\n- 需要额外的状态管理方案\n\n## 选择建议\n\n- 小型项目：Vue\n- 大型项目：React\n- 团队熟悉度：选择团队更熟悉的框架\n\n## 总结\n\n两个框架都很优秀，选择应根据项目需求和团队情况决定。', 'Vue vs React：前端框架对比', 0, 'DRAFT', 'Vue,React,前端框架', 1, 3, 'Vue 和 React 是目前最流行的两个前端框架，本文将对它们进行对比分析，帮助开发者做出选择。', 0),

('https://example.com/images/microservices.jpg', '# 微服务拆分策略\n\n微服务拆分是微服务架构实施的关键步骤，需要合理规划。\n\n## 拆分原则\n\n### 业务边界\n\n基于业务领域进行拆分，每个微服务对应一个业务能力。\n\n### 数据一致性\n\n考虑数据一致性的要求，避免过度拆分导致分布式事务问题。\n\n### 团队结构\n\n遵循康威定律，组织结构应该与系统架构相匹配。\n\n## 拆分策略\n\n### 绞杀者模式\n\n逐步替换单体应用中的模块，而不是一次性重写。\n\n### 分支抽象模式\n\n通过抽象层隔离变化，逐步迁移功能。\n\n### 装饰者模式\n\n在现有功能基础上添加新功能，逐步重构。\n\n## 实施步骤\n\n1. 识别业务边界\n2. 评估拆分可行性\n3. 制定拆分计划\n4. 逐步实施拆分\n5. 验证拆分效果\n\n## 总结\n\n微服务拆分是一个持续的过程，需要根据业务发展和团队能力进行调整。', '微服务拆分策略', 0, 'DELETE', '微服务,架构设计,拆分策略', 1, 2, '微服务拆分是微服务架构实施的关键步骤，本文将介绍拆分原则、策略和实施步骤。', 320),

('https://example.com/images/devops.jpg', '# DevOps 实践指南\n\nDevOps 是一种重视软件开发人员（Dev）和IT运维技术人员（Ops）之间沟通合作的文化、运动或惯例。\n\n## 核心原则\n\n- 协作\n- 自动化\n- 持续集成\n- 持续交付\n- 监控\n\n## 工具链\n\n### 版本控制\n- Git\n- GitHub/GitLab\n\n### 构建工具\n- Maven/Gradle\n- npm/yarn\n\n### CI/CD\n- Jenkins\n- GitLab CI\n- GitHub Actions\n\n### 容器化\n- Docker\n- Kubernetes\n\n### 监控\n- Prometheus\n- Grafana\n- ELK Stack\n\n## 实施步骤\n\n1. 建立协作文化\n2. 自动化构建和测试\n3. 持续集成和交付\n4. 基础设施即代码\n5. 监控和日志\n\n## 总结\n\nDevOps 能够显著提高软件交付速度和质量，是现代软件开发的重要实践。', 'DevOps 实践指南', 1, 'PUBLISH', 'DevOps,CI/CD,自动化', 1, 3, 'DevOps 是一种重视软件开发和IT运维之间沟通合作的文化，本文将介绍其核心原则、工具链和实施步骤。', 950);


-- ----------------------------
-- Table structure for category
-- ----------------------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(200) DEFAULT NULL COMMENT '分类描述',
  `created_by` bigint COMMENT '创建者用户ID',
  `updated_by` bigint COMMENT '修改者用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_created_by` (`created_by`),
  KEY `idx_updated_by` (`updated_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` (`name`, `description`, `created_by`, `updated_by`) VALUES ('Java', 'Java编程语言相关文章', 1, 1);
INSERT INTO `category` (`name`, `description`, `created_by`, `updated_by`) VALUES ('Spring Boot', 'Spring Boot框架相关技术', 1, 1);
INSERT INTO `category` (`name`, `description`, `created_by`, `updated_by`) VALUES ('Frontend', '前端开发技术', 1, 1);

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
CREATE TABLE IF NOT EXISTS `verification_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `email` varchar(100) NOT NULL COMMENT '邮箱地址',
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `type` varchar(20) NOT NULL COMMENT '验证码类型 (REGISTER, LOGIN, RESET_PASSWORD)',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `is_used` tinyint(1) DEFAULT '0' COMMENT '是否已使用 1:是 0:否',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_email` (`email`),
  KEY `idx_expire_time` (`expire_time`),
  KEY `idx_is_used` (`is_used`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';
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
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
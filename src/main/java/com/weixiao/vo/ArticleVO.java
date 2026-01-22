package com.weixiao.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章视图对象
 * 
 * @author weixiao
 * @version 1.0
 * @since 2026-01-21
 */
@Data
public class ArticleVO {
    /**
     * 文章ID
     */
    private Long id;
    
    /**
     * 封面图片URL
     */
    private String cover;
    
    /**
     * 文章内容（Markdown格式）
     */
    private String content;
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 是否置顶（0-不置顶，1-置顶）
     */
    private Integer isTop;
    
    /**
     * 文章状态（DRAFT-草稿，PUBLISH-发布，DELETE-删除）
     */
    private String status;
    
    /**
     * 标签（逗号分隔）
     */
    private String labels;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 摘要内容
     */
    private String abstractContent;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建者ID
     */
    private Long createdBy;
    
    /**
     * 修改者ID
     */
    private Long updatedBy;
}
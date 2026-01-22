package com.weixiao.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 文章数据传输对象
 * 
 * @author weixiao
 * @version 1.0
 * @since 2026-01-21
 */
@Data
public class ArticleDTO {
    /**
     * 封面图片URL
     */
    private String cover;
    
    /**
     * 文章内容（Markdown格式）
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;
    
    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    private String title;
    
    /**
     * 是否置顶（0-不置顶，1-置顶）
     */
    @NotNull(message = "是否置顶不能为空")
    private Integer isTop;
    
    /**
     * 文章状态（DRAFT-草稿，PUBLISH-发布，DELETE-删除）
     */
    @NotBlank(message = "文章状态不能为空")
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
}
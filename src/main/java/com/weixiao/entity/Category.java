package com.weixiao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author weixiao
 * @description 文章分类实体类
 */
@Data
public class Category {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 创建人ID
     */
    private Long createdBy;

    /**
     * 修改人ID
     */
    private Long updatedBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
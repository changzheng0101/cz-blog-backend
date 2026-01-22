package com.weixiao.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author weixiao
 * @description 文章分类VO
 */
@Data
public class CategoryVO {
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
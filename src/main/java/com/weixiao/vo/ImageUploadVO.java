package com.weixiao.vo;

import lombok.Data;

/**
 * 图像上传视图对象
 */
@Data
public class ImageUploadVO {
    
    /**
     * 图像URL
     */
    private String imageUrl;
    
    /**
     * 图像名称
     */
    private String imageName;
    
    /**
     * 图像大小（字节）
     */
    private Long imageSize;
    
    /**
     * 图像描述
     */
    private String description;
}
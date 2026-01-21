package com.weixiao.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

/**
 * 图像上传数据传输对象
 */
@Data
public class ImageUploadDTO {
    
    /**
     * 上传的图像文件
     */
    @NotNull(message = "图像文件不能为空")
    private MultipartFile file;
    
    /**
     * 图像描述
     */
    private String description;
}
package com.weixiao.controller;

import com.weixiao.common.result.DataResponse;
import com.weixiao.dto.ImageUploadDTO;
import com.weixiao.service.ImageService;
import com.weixiao.common.utils.JwtUtils;
import com.weixiao.vo.ImageUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 图像控制器
 */
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * 上传图像
     * 
     * @param imageUploadDTO 图像上传DTO
     * @param authorization 认证头
     * @return 上传结果
     */
    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public DataResponse<ImageUploadVO> uploadImage(
            @Validated @ModelAttribute ImageUploadDTO imageUploadDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        
        try {
            // 从Authorization头中提取token
            String token = authorization.replace("Bearer ", "");
            
            // 从token中获取用户名
            String username = jwtUtils.extractUsername(token);
            
            // 上传图像
            ImageUploadVO result = imageService.uploadImage(imageUploadDTO, username);
            
            return DataResponse.success(result);
        } catch (IOException e) {
            return DataResponse.error("图像上传失败: " + e.getMessage());
        } catch (Exception e) {
            return DataResponse.error("系统错误: " + e.getMessage());
        }
    }
    
    /**
     * 读取图像
     * 
     * @param imagePath 图像路径
     * @return 图像文件
     */
    @GetMapping("/{*imagePath}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imagePath") String imagePath) {
        try {
            // 获取完整的文件路径
            String fullPath = imageService.getImageFilePath(imagePath);
            
            // 读取文件
            Path path = Paths.get(fullPath);
            byte[] imageBytes = Files.readAllBytes(path);
            
            // 根据文件扩展名确定媒体类型
            String contentType = getContentType(imagePath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 根据文件扩展名获取媒体类型
     * 
     * @param fileName 文件名
     * @return 媒体类型
     */
    private String getContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.toLowerCase().endsWith(".bmp")) {
            return "image/bmp";
        } else if (fileName.toLowerCase().endsWith(".webp")) {
            return "image/webp";
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
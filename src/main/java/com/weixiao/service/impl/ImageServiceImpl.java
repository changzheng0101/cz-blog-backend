package com.weixiao.service.impl;

import com.weixiao.config.FileUploadConfig;
import com.weixiao.dto.ImageUploadDTO;
import com.weixiao.service.ImageService;
import com.weixiao.common.utils.FileUtils;
import com.weixiao.vo.ImageUploadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 图像服务实现类
 */
@Service
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private FileUploadConfig fileUploadConfig;
    
    @Value("${server.servlet.context-path:}")
    private String contextPath;
    
    @Override
    public ImageUploadVO uploadImage(ImageUploadDTO imageUploadDTO, String username) throws IOException {
        MultipartFile file = imageUploadDTO.getFile();
        
        // 获取每月文件夹路径
        String monthlyDirectory = FileUtils.getMonthlyDirectory(fileUploadConfig.getDirectory());
        
        // 确保目录存在
        FileUtils.ensureDirectoryExists(monthlyDirectory);
        
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = FileUtils.generateUniqueFileName(originalFilename);
        
        // 构建完整文件路径
        Path filePath = Paths.get(monthlyDirectory, uniqueFileName);
        
        // 保存文件
        Files.write(filePath, file.getBytes());
        
        // 获取相对路径
        String relativePath = FileUtils.getRelativePath(fileUploadConfig.getDirectory(), filePath.toString());
        
        // 构建返回对象
        ImageUploadVO imageUploadVO = new ImageUploadVO();
        imageUploadVO.setImageName(uniqueFileName);
        imageUploadVO.setImageSize(file.getSize());
        imageUploadVO.setDescription(imageUploadDTO.getDescription());
        
        // 构建图像URL
        String imageUrl = contextPath + "/api/v1/images/" + relativePath.replace("\\", "/");
        imageUploadVO.setImageUrl(imageUrl);
        
        return imageUploadVO;
    }
    
    @Override
    public String getImageFilePath(String imagePath) {
        return Paths.get(fileUploadConfig.getDirectory(), imagePath).toString();
    }
}
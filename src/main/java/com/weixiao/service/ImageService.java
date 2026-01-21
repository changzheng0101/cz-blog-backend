package com.weixiao.service;

import com.weixiao.dto.ImageUploadDTO;
import com.weixiao.vo.ImageUploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 图像服务接口
 */
public interface ImageService {
    
    /**
     * 上传图像
     * @param imageUploadDTO 图像上传数据传输对象
     * @param username 用户名
     * @return 图像上传视图对象
     * @throws IOException 如果文件操作失败
     */
    ImageUploadVO uploadImage(ImageUploadDTO imageUploadDTO, String username) throws IOException;
    
    /**
     * 获取图像文件路径
     * @param imagePath 图像相对路径
     * @return 图像文件路径
     */
    String getImageFilePath(String imagePath);
}
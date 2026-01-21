package com.weixiao.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件工具类
 */
public class FileUtils {
    
    /**
     * 获取每月文件夹路径
     * @param baseDirectory 基础目录
     * @return 每月文件夹路径
     */
    public static String getMonthlyDirectory(String baseDirectory) {
        LocalDate now = LocalDate.now();
        String monthFolder = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        return Paths.get(baseDirectory, monthFolder).toString();
    }
    
    /**
     * 确保目录存在，如果不存在则创建
     * @param directoryPath 目录路径
     * @throws IOException 如果创建目录失败
     */
    public static void ensureDirectoryExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }
    
    /**
     * 生成唯一文件名
     * @param originalFilename 原始文件名
     * @return 唯一文件名
     */
    public static String generateUniqueFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }
    
    /**
     * 获取文件的相对路径
     * @param baseDirectory 基础目录
     * @param filePath 文件完整路径
     * @return 相对于基础目录的路径
     */
    public static String getRelativePath(String baseDirectory, String filePath) {
        Path basePath = Paths.get(baseDirectory);
        Path filePathObj = Paths.get(filePath);
        return basePath.relativize(filePathObj).toString().replace("\\", "/");
    }
}
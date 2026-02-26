package com.personhealth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件工具类
 */
@Slf4j
@Component
public class FileUtil {

    @Value("${file.upload-path:/data/uploads}")
    private String uploadPath;

    @Value("${file.max-size:10485760}")
    private long maxSize;

    @Value("${file.allowed-types:image/jpeg,image/png,image/jpg}")
    private String allowedTypes;

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file, String category) {
        // 验证文件
        validateFile(file);

        try {
            // 生成文件路径
            String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileName = generateFileName(file.getOriginalFilename());
            String relativePath = category + "/" + datePath + "/" + fileName;
            String fullPath = uploadPath + "/" + relativePath;

            // 创建目录
            Path path = Paths.get(fullPath);
            Files.createDirectories(path.getParent());

            // 保存文件
            file.transferTo(path.toFile());

            log.info("文件上传成功，路径：{}", relativePath);

            return "/" + relativePath;
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败");
        }
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 检查文件大小
        if (file.getSize() > maxSize) {
            throw new RuntimeException("文件大小不能超过 " + (maxSize / 1024 / 1024) + "MB");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new RuntimeException("仅支持上传图片文件（JPEG、PNG）");
        }
    }

    /**
     * 生成文件名
     */
    private String generateFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex);
        }
        return "";
    }

    /**
     * 检查是否为身份证照片
     */
    public static boolean isIdCardPhoto(MultipartFile file) {
        String contentType = file.getContentType();
        return ALLOWED_CONTENT_TYPES.contains(contentType);
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Files.delete(file.toPath());
                log.info("文件删除成功，路径：{}", filePath);
            }
        } catch (IOException e) {
            log.error("文件删除失败，路径：{}", filePath, e);
        }
    }
}

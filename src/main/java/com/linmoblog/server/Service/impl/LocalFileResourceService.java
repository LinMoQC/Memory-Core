package com.linmoblog.server.Service.impl;

import cn.hutool.core.io.FileUtil;
import com.linmoblog.server.Controller.ImageController;
import com.linmoblog.server.Service.FileResourceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * 使用本地存储，在配置文件中要添加 local.enable = true
 *
 * @author wkq97@qq.com
 * @since 2024-04-07
 */
@Service
@Slf4j
@ConditionalOnProperty(value = "local.enable", havingValue = "true")
public class LocalFileResourceService implements FileResourceService {

    @Value("${local.uploadDir}")
    private String uploadDir;


    @Override
    public void delete(List<String> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        for (String fileUrl : files) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"));
            File file = new File(uploadDir +File.separator+ fileName);
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    log.info("文件删除成功：" + fileUrl);
                } else {
                    log.error("文件删除失败：" + fileUrl);
                }
            } else {
                log.error("文件不存在或者不是一个文件：" + fileUrl);
            }
        }
    }

    @Override
    public String upload(MultipartFile file) {
        // 创建目录（如果不存在）
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Path path = null;
        if (uploadDir != null) {
            path = Paths.get(uploadDir).resolve(getFileName(Objects.requireNonNull(file.getOriginalFilename())));
        }

        // 保存文件
        try {
            if (path != null) {
                Files.copy(file.getInputStream(), path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 创建一个可以访问此文件的URL
        URI uri = MvcUriComponentsBuilder.fromMethodName(ImageController.class, "downloadFile", path.getFileName().toString())
                .buildAndExpand(path.getFileName().toString()).toUri();

        // 返回带有新创建资源的URL的响应
        return uri.toString();
    }
}

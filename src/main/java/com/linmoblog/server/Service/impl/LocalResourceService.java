package com.linmoblog.server.Service.impl;

import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.ImageService;
import com.linmoblog.server.Service.ResourceService;
import com.linmoblog.server.enums.ResultCode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 使用本地存储，在配置文件中要添加 local.enable = true
 *
 * @author wkq97@qq.com
 * @since 2024-04-07
 */
@Service
@Slf4j
@ConditionalOnProperty(value = "local.enable", havingValue = "true")
public class LocalResourceService implements ResourceService {


    @Value("${local.uploadUrl}")
    private String uploadUrl;

    @Value("${local.downloadUrl}")
    private String downloadUrl;

    @Override
    public void delete(List<String> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (String filePath : files) {
            File file = new File(filePath.replace(downloadUrl, uploadUrl));
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    log.info("文件删除成功：" + filePath);
                } else {
                    log.error("文件删除失败：" + filePath);
                }
            } else {
                log.error("文件不存在或者不是一个文件：" + filePath);
            }
        }
    }

    @Override
    public String upload(MultipartFile file) {
        String fileName = getFileName(file.getOriginalFilename());
        String absolutePath = uploadUrl + fileName;
        if (FileUtil.exist(absolutePath)) {
            throw new RuntimeException("文件已存在！");
        }
        try {
            File newFile = FileUtil.touch(absolutePath);
            file.transferTo(newFile);
            return downloadUrl + fileName;
        } catch (IOException e) {
            log.error("文件上传失败：", e);
            FileUtil.del(absolutePath);
            throw new RuntimeException("文件上传失败！");
        }
    }
}

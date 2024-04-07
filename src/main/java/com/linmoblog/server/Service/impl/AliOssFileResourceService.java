package com.linmoblog.server.Service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.linmoblog.server.Service.FileResourceService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 使用阿里云作为存储，在配置文件中要添加  ali.enable = true 开启此配置
 *
 * @author wkq97@qq.com
 * @since 2024-04-07
 */
@Service
@Slf4j
@ConditionalOnProperty(value = "ali.enable",havingValue = "true")
public class AliOssFileResourceService implements FileResourceService {
    @Value("${ali.accessKey}")
    private String accessKey;

    @Value("${ali.secretKey}")
    private String secretKey;

    @Value("${ali.bucket}")
    private String bucket;

    @Value("${ali.endpoint}")
    private String endpoint;
    @Value("${ali.uploadPath}")
    private String uploadPath;
    private OSS oss;

    @PostConstruct
    public void init() {

        // 创建OSSClient实例。
        oss = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    }

    @Override
    public String upload(MultipartFile file) {
        if (file == null) {
            throw new RuntimeException("文件不能为空");
        }
        String absolutePath = uploadPath + getFileName(Objects.requireNonNull(file.getOriginalFilename())); //absolutePath 也是 ObjectName : 不包含 bucket 名称在内的完整路径

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, absolutePath, inputStream);
            oss.putObject(putObjectRequest);
            return "https://" + bucket + "." + endpoint + "/" + absolutePath;
        } catch (IOException e) {
            log.error("文件上传失败：", e);
            throw new RuntimeException("文件上传失败！");
        }
    }

    @Override
    public void delete(List<String> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (String filePath : files) {
            String objectName = filePath.substring(filePath.indexOf(endpoint) + endpoint.length());
            try {
                oss.deleteObject(bucket, objectName);
                log.info("文件从 OSS 删除成功：" + filePath);

            } catch (OSSException | com.aliyun.oss.ClientException e) {
                log.error("文件从 OSS 删除失败：" + filePath);
            }

        }
    }
}

package com.linmoblog.server.Service;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 资源接口，目前的实现有本地、阿里云两个，其它的实现可自行添加
 * @author wkq
 * @since 2024-04-07
 */
public interface ResourceService {
    /**
     * 上传文件,返回可访问的 url
     */
    String upload(MultipartFile file);

    /**
     * 根据 url 列表 删除文件
     */
    void delete(List<String> fileNames);
    default String getFileName(String originalFileName){
        // 截取文件后缀名
        assert originalFileName != null;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + fileExtension;
    }
}

package com.linmoblog.server.Controller;

import cn.hutool.core.io.FileUtil;
import com.linmoblog.server.Entity.Image;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.ImageService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/protect")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Value("${files.upload.path}")
    private String uploadPath;

    @Value("${files.domains.siteUrl}")
    private String domain;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String saveFileName = UUID.randomUUID().toString() + '.' + FileUtil.extName(fileName);
            String filePath = Paths.get(uploadPath, saveFileName).toString();
            Path absolutePath = Paths.get(uploadPath).toAbsolutePath();
            String absoluteFilePath = Paths.get(String.valueOf(absolutePath), saveFileName).toString();
            file.transferTo(new File(absoluteFilePath));

            String imageUrl = domain + "/upload/" + saveFileName;

            return imageService.upload(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<String>(500, "上传失败: " + e.getMessage(), null);
        }
    }


    @DeleteMapping("/delImg")
    public Result<Null> deleteFiles(@RequestBody List<String> fileNames) {
        try {
            List<String> failedDeletions = new ArrayList<>();
            for (String fileName : fileNames) {
                String f = extractFileName(fileName);
                Path absolutePath = Paths.get(uploadPath).toAbsolutePath();
                String filePath = Paths.get(String.valueOf(absolutePath), f).toString();
                File file = new File(filePath);

                String imageUrl = domain + "/upload/" + fileName;
                imageService.deleteFile(fileName);
                if (file.exists()) {
                    if (!file.delete()) {
                        failedDeletions.add(f);
                    }
                } else {
                    System.out.println(filePath + " 文件不存在");
                    failedDeletions.add(f);
                }
            }

            if (failedDeletions.isEmpty()) {
                return new Result<>(200, "所有文件删除成功", null);
            } else {
                return new Result<>(500, "以下文件删除失败: " + failedDeletions, null);
            }
        } catch (Exception e) {
            return new Result<>(500, "文件删除失败：" + e.getMessage(), null);
        }
    }


    @GetMapping("/images")
    public Result<List<Image>> getImages() throws Exception {
        return imageService.getImages();
    }


    public static String extractFileName(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            return url.substring(lastSlashIndex + 1);
        }
        return url;
    }
}

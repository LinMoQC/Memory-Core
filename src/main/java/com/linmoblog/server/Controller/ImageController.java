package com.linmoblog.server.Controller;

import cn.hutool.core.io.FileUtil;
import com.linmoblog.server.Entity.Image;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.ImageService;
import com.linmoblog.server.aspect.ApiOperationLog;
import com.linmoblog.server.enums.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/protect")
@Tag(name = "图片接口")
@Slf4j
public class ImageController {
    @Resource
    private ImageService imageService;

    @ApiOperationLog(description = "图片上传")
    @Operation(summary ="图片上传")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        try {
            return imageService.upload(file);
        } catch (Exception e) {
            log.error("上传失败：{}", e.toString());
            return new Result<String>(ResultCode.ERROR_UPLOAD, null);
        }
    }

    @ApiOperationLog(description = "图片删除")
    @Operation(summary ="图片删除")
    @DeleteMapping("/delImg")
    public Result<Null> deleteFiles(@RequestBody List<String> fileNames) {
        try {
            List<String> failedDeletions = new ArrayList<>();
            for (String fileName : fileNames) {
//                String f = extractFileName(fileName);
//                Path absolutePath = Paths.get(uploadPath).toAbsolutePath();
//                String filePath = Paths.get(String.valueOf(absolutePath), f).toString();
//                File file = new File(filePath);
//
//                String imageUrl = domain + "/upload/" + fileName;
                imageService.deleteFile(fileName);
//                if (file.exists()) {
//                    if (!file.delete()) {
//                        failedDeletions.add(f);
//                    }
//                } else {
//                    System.out.println(filePath + " 文件不存在");
//                    failedDeletions.add(f);
//                }
            }

            if (failedDeletions.isEmpty()) {
                return new Result<>(ResultCode.SUCCESS_FILE_DEL, null);
            } else {
                log.error("以下文件未删除：{}", String.join(", ", failedDeletions));
                return new Result<>(ResultCode.ERROR_FILE_DEL, null);
            }
        } catch (Exception e) {
            log.error("文件删除失败：{}", e.toString());
            return new Result<>(ResultCode.ERROR_FILE_DEL, null);
        }
    }

    @ApiOperationLog(description = "获取所有图片")
    @Operation(summary ="获取所有图片")
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

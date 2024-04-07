package com.linmoblog.server.Controller;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(value = "/api/protect")
@Tag(name = "图片接口")
@Slf4j
public class ImageController {
    @Resource
    private ImageService imageService;

    @Value("${local.uploadDir}")
    private String uploadDir;

    @ApiOperationLog(description = "图片上传")
    @Operation(summary = "图片上传")
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        String fileUrl = imageService.upload(file);
        return Result.success(fileUrl);
    }

    @ApiOperationLog(description = "图片删除")
    @Operation(summary = "图片删除")
    @DeleteMapping("/delImg")
    public Result<Void> deleteFiles(@RequestBody List<String> fileNames) {
        imageService.deleteFile(fileNames);
        return Result.success();
    }

    @ApiOperationLog(description = "获取所有图片")
    @Operation(summary = "获取所有图片")
    @GetMapping("/images")
    public Result<List<Image>> getImages() throws Exception {
        List<Image> imageList = imageService.getImages();
        return Result.success(imageList);
    }


    // 提供一个GET请求来获取已上传的文件（假设你已经有处理下载的逻辑）
    @GetMapping("/download/{filename}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String filename) {
        // 获取文件路径
        Path filePath = Paths.get(uploadDir, filename);
        FileSystemResource file = new FileSystemResource(filePath.toFile());

        if (file.exists()) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"").body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

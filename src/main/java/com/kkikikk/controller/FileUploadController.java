package com.kkikikk.controller;

import com.kkikikk.pojo.Result;
import com.kkikikk.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class FileUploadController {

    private final AliOssUtil aliOssUtil;

    @Autowired
    public FileUploadController(AliOssUtil aliOssUtil) {
        this.aliOssUtil = aliOssUtil;
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("文件上传: {}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("文件名为空");
            }
            // 截取文件名后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构造新文件名称
            String objectFileName = UUID.randomUUID() + extension;

            // 文件的请求路径
            return Result.success(aliOssUtil.upload(file.getBytes(), objectFileName));
        } catch (IOException e) {
            log.error("文件上传失败: {}", e.getMessage());
        }

        return Result.error("文件上传失败");
    }

}

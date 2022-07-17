package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class uploadFileController {

    private final s3Util util;
    @PostMapping
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile){
        String filename = s3Util.generateFileName(multipartFile);

        return util.uploadFile(multipartFile);
    }
}

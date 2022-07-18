package com.example.demo;

import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class uploadFileController {

    private final s3Service util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile){

        return util.uploadFile(multipartFile);
    }
//    @PostMapping("/{bucketName}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public String createBucket(@PathVariable String bucketName){
//        return util.createBucket(bucketName);
//    }
//
//    @GetMapping()
//    public ArrayList<Bucket> getAllBucket(){
//        return util.getListBucket();
//    }

}

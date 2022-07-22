package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class uploadFileController {

    private final s3Service util;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {

        return util.uploadFile(multipartFile);
    }

    @GetMapping("/getobject")
    public String getObjectByName(@RequestParam("FILE_NAME") String fileName) {
        return util.findByName(fileName);
    }

    @PostMapping("/byurl")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadByUrl(@RequestParam("fileName") String fileName){
        return util.uploadByUrl(fileName);
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

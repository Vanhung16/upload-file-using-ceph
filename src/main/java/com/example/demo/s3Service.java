package com.example.demo;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class s3Service {

    private final AmazonS3 s3;
    @Value("${amazonProperties.bucketName}")
    private String BucketName;
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    private String generateFileName(MultipartFile multipartFile){
        return new Date().getTime() + multipartFile.getOriginalFilename().replace(" ","_");
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public String uploadFile(MultipartFile multipartFile) {
        var fileName = generateFileName(multipartFile);
        String fileUrl = null;

        try {
            File file = convertMultipartToFile(multipartFile);
            var request = new PutObjectRequest(BucketName, fileName,file);
            s3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = endpointUrl + "/" + BucketName + "/" + fileName;
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    private String generateUrl(String fileName, HttpMethod httpMethod){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);// generate URL will be valid for 1 day or 24 hours
//        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(BucketName,fileName,httpMethod);
        return s3.generatePresignedUrl(BucketName,fileName,calendar.getTime(),httpMethod).toString();
    }

    public String findByName(String fileName){
        if(!s3.doesObjectExist(BucketName,fileName)) return "File does not exist";
        return generateUrl(fileName,HttpMethod.GET);
    }

    public String uploadByUrl(String fileName){
//        String fileName = new Date().getTime() + extension;
        return generateUrl(fileName, HttpMethod.PUT);
    }



}

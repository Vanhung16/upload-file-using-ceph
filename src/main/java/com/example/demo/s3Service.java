package com.example.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
        String fileName = generateFileName(multipartFile);
        String fileUrl = null;
        try {
            PutObjectRequest request = new PutObjectRequest(BucketName, fileName, convertMultipartToFile(multipartFile));
            s3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = endpointUrl + "/" + BucketName + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUrl;
    }
}

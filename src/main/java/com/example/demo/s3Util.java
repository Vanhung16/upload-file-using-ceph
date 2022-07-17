package com.example.demo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class s3Util {
    private final String BUCKET_NAME = "sandbox";
    private final String accessKey = "sandbox";
    private final String secretKey = "s3cr3t";
    BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSCredentialsProvider() {
                @Override
                public AWSCredentials getCredentials() {
                    return credentials;
                }

                @Override
                public void refresh() {
                }
            })
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://127.0.0.1:8000", "default"))
            .build();

    public static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    public static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl;
        String fileName = generateFileName(multipartFile);
        try {
            PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, fileName, convertMultipartToFile(multipartFile));
//            s3.putObject(BUCKET_NAME,generateFileName(multipartFile),convertMultipartToFile(multipartFile));
            s3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = "http://127.0.0.1:8000/" + BUCKET_NAME + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    public String createBucket(String bucketName) {
        String fileUrl = null;
        Bucket bucket;
        if (s3.doesBucketExistV2(bucketName)) {
            fileUrl = "error";
            String status = HttpStatus.RESET_CONTENT.toString();
            return status;
        } else {
            bucket = s3.createBucket(bucketName);
            fileUrl = "http://localhost:5000/objects.html?bucket=" + bucket.getName();
        }
        return fileUrl;
    }

    public ArrayList<Bucket> getListBucket() {
        return (ArrayList<Bucket>) s3.listBuckets();
    }
}

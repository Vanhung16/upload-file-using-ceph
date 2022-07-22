package com.example.demo;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class s3Configuration {
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    //    BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
    //bean
    @Bean
    public AmazonS3 credentialsS3() {
        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, "default"))
                .build();
    }

//    public static String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
//    }
//
//    public static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
//        File file = new File(multipartFile.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(multipartFile.getBytes());
//        fos.close();
//        return file;
//    }
//    public String uploadFile(MultipartFile multipartFile) {
//        credentialsS3();
//        String fileUrl;
//        String fileName = generateFileName(multipartFile);
//
//        PutObjectRequest request = null;
//        try {
//            request = new PutObjectRequest(BUCKET_NAME, fileName, convertMultipartToFile(multipartFile));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////            s3.putObject(BUCKET_NAME,generateFileName(multipartFile),convertMultipartToFile(multipartFile));
//        s3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
//        fileUrl = "http://127.0.0.1:8000/" + BUCKET_NAME + "/" + fileName;
//        return fileUrl;
//    }

//    @Bean
//    public String createBucket(String bucketName) {
//        String fileUrl = null;
//        Bucket bucket;
//        if (s3.doesBucketExistV2(bucketName)) {
//            fileUrl = "error";
//            String status = HttpStatus.RESET_CONTENT.toString();
//            return status;
//        } else {
//            bucket = s3.createBucket(bucketName);
//            fileUrl = "http://localhost:5000/objects.html?bucket=" + bucket.getName();
//        }
//        return fileUrl;
//    }
//    @Bean
//    public ArrayList<Bucket> getListBucket() {
//        return (ArrayList<Bucket>) s3.listBuckets();
//    }
}

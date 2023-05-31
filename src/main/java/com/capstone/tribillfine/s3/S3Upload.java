package com.capstone.tribillfine.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        String fileUrl = "https://tribillbucket.s3.ap-northeast-2.amazonaws.com/";
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
//
//        return amazonS3.getUrl(bucket, s3FileName).toString();
//        ObjectMetadata objMeta = new ObjectMetadata();
//        objMeta.setContentLength(multipartFile.getSize());
//        objMeta.setContentType(multipartFile.getContentType());
//
//        PutObjectRequest request = new PutObjectRequest(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
//        request.setCannedAcl(CannedAccessControlList.PublicRead);
//
//        amazonS3.putObject(request);

        return amazonS3.getUrl(bucket, s3FileName).toString();
//        return multipartFile.getOriginalFilename();

    }
//    public String upload(MultipartFile file) throws IOException {
//        if(file == null || file.isEmpty()){
//            return null;
//        }
//        String fileName = file.getOriginalFilename();
//        String fileUrl = "https://tribillbucket.s3.ap-northeast-2.amazonaws.com/" + fileName;
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(file.getContentType());
//        metadata.setContentLength(file.getSize());
//        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
//        return fileUrl;
//    }
}
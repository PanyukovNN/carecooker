package com.zylex.carecooker.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
public class S3Services {

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String uidFile = UUID.randomUUID().toString();
        String uniqueFileName = uidFile + "." + file.getOriginalFilename();

        s3client.putObject(new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream(), new ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + uniqueFileName;
    }
}

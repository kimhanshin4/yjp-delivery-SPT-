package com.yjp.delivery.service.provider;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.yjp.delivery.common.validator.S3Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Provider {

    private final AmazonS3 amazonS3;
    private final String SEPARATOR = "/";
    @Value("${cloud.aws.s3.bucket.name}")
    public String bucket;

    public String saveFile(MultipartFile multipartFile, String folderName) throws IOException {
        String originalFilename =
            folderName + SEPARATOR + UUID.randomUUID() + multipartFile.getOriginalFilename();
        createFolder(folderName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    private void createFolder(String folderName) {
        if (!amazonS3.doesObjectExist(bucket, folderName)) {
            amazonS3.putObject(
                bucket,
                folderName + SEPARATOR,
                new ByteArrayInputStream(new byte[0]),
                new ObjectMetadata());
        }
    }

    public void deleteImage(String originalFilename) {
        S3Validator.validate(amazonS3, bucket, originalFilename);
        amazonS3.deleteObject(bucket, originalFilename);
    }
}

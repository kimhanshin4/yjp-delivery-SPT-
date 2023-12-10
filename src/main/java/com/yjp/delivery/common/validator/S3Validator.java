package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_FILE;

import com.amazonaws.services.s3.AmazonS3;
import com.yjp.delivery.common.exception.GlobalException;

public class S3Validator {

    public static void validate(AmazonS3 amazonS3, String bucket, String filename) {
        if (isNullFilename(filename) || !isExistFile(amazonS3, bucket, filename)) {
            throw new GlobalException(NOT_FOUND_FILE);
        }
    }

    private static boolean isNullFilename(String filename) {
        return filename == null;
    }

    private static boolean isExistFile(AmazonS3 amazonS3, String bucket, String filename) {
        return amazonS3.doesObjectExist(bucket, filename);
    }
}

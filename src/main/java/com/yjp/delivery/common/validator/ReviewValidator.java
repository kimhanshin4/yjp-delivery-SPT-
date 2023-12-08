package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_REVIEW;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.ReviewEntity;

public class ReviewValidator {

    public static void validate(ReviewEntity reviewEntity) {
        if (checkIsNull(reviewEntity)) {
            throw new GlobalException(NOT_FOUND_REVIEW);
        }
    }

    private static boolean checkIsNull(ReviewEntity reviewEntity) {
        return reviewEntity == null;
    }
}





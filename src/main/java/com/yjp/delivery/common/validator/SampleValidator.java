package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_SAMPLE;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.SampleEntity;

public class SampleValidator {

    public static void validate(SampleEntity sampleEntity) {
        if (checkIsNull(sampleEntity)) {
            throw new GlobalException(NOT_FOUND_SAMPLE);
        }
    }

    private static boolean checkIsNull(SampleEntity sampleEntity) {
        return sampleEntity == null;
    }
}

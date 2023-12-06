package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_USER;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.UserEntity;

public class UserValidator {

    public static void validate(UserEntity user) {
        if (checkIsNull(user)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    private static boolean checkIsNull(UserEntity user) {
        return user == null;
    }
}

package com.yjp.delivery.common.validator;


import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_SHOP;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.ShopEntity;

public class ShopValidator {

    public static void validate(ShopEntity shopEntity) {
        if (checkIsNull(shopEntity)) {
            throw new GlobalException(NOT_FOUND_SHOP);
        }
    }

    private static boolean checkIsNull(ShopEntity shopEntity) {
        return shopEntity == null;
    }
}

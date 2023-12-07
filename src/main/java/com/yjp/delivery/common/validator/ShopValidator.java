package com.yjp.delivery.common.validator;


import static com.yjp.delivery.common.meta.ResultCode.ALREADY_LIKED_SHOP;
import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_SHOP;
import static com.yjp.delivery.common.meta.ResultCode.NOT_YET_LIKED_SHOP;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;

public class ShopValidator {

    public static void validate(ShopEntity shopEntity) {
        if (checkIsNull(shopEntity)) {
            throw new GlobalException(NOT_FOUND_SHOP);
        }
    }

    public static void checkAlreadyLiked(ShopLikeEntity shopLikeEntity) {
        if (isExistShopLike(shopLikeEntity)) {
            throw new GlobalException(ALREADY_LIKED_SHOP);
        }
    }

    public static void checkNotYetLiked(ShopLikeEntity shopLikeEntity) {
        if (!isExistShopLike(shopLikeEntity)) {
            throw new GlobalException(NOT_YET_LIKED_SHOP);
        }
    }

    private static boolean checkIsNull(ShopEntity shopEntity) {
        return shopEntity == null;
    }

    private static boolean isExistShopLike(ShopLikeEntity shopLikeEntity) {
        return shopLikeEntity != null;
    }
}

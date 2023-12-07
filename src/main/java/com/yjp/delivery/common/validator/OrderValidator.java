package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_SAMPLE;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.OrderEntity;

public class OrderValidator {

    public static void validate(OrderEntity orderEntity) {
        if (checkIsNull(orderEntity)) {
            throw new GlobalException(NOT_FOUND_SAMPLE);
        }
    }

    private static boolean checkIsNull(OrderEntity orderEntity) {
        return orderEntity == null;
    }
}

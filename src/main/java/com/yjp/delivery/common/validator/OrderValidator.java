package com.yjp.delivery.common.validator;

import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_ORDER;
import static com.yjp.delivery.common.meta.ResultCode.NOT_PERMISSION_ORDER;
import static com.yjp.delivery.common.meta.Role.ROLE_ADMIN;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.common.meta.Role;
import com.yjp.delivery.store.entity.OrderEntity;

public class OrderValidator {

    public static void validate(OrderEntity orderEntity, Long userId, Role role) {
        if (checkIsNull(orderEntity)) {
            throw new GlobalException(NOT_FOUND_ORDER);
        }

        if (isAdmin(role)) {
            return;
        }

        if (!isMine(orderEntity, userId)) {
            throw new GlobalException(NOT_PERMISSION_ORDER);
        }
    }

    private static boolean checkIsNull(OrderEntity orderEntity) {
        return orderEntity == null;
    }

    private static boolean isAdmin(Role role) {
        return ROLE_ADMIN.equals(role);
    }

    private static boolean isMine(OrderEntity orderEntity, Long userId) {
        return userId.equals(orderEntity.getUserEntity().getUserId());
    }
}

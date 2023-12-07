package com.yjp.delivery.common.validator;


import static com.yjp.delivery.common.meta.ResultCode.NOT_FOUND_MENU;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.store.entity.MenuEntity;

public class MenuValidator {

    public static void validate(MenuEntity menuEntity) {
        if (checkIsNull(menuEntity)) {
            throw new GlobalException(NOT_FOUND_MENU);
        }
    }

    private static boolean checkIsNull(MenuEntity menuEntity) {
        return menuEntity == null;
    }
}

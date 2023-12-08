package com.yjp.delivery.common.meta;

import com.yjp.delivery.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role implements CommonEnum {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "유저"),
    ROLE_GUEST("ROLE_GUEST", "게스트");

    @Getter
    private final String code;
    @Getter
    private final String value;

    @Override
    public String getName() {
        return name();
    }
}

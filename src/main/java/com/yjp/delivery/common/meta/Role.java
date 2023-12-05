package com.yjp.delivery.common.meta;

import com.yjp.delivery.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role implements CommonEnum {
    ADMIN("관리자"),
    USER("유저"),
    GUEST("게스트");

    @Getter private final String value;

    @Override
    public String getName() {
        return name();
    }
}

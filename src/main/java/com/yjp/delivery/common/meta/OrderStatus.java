package com.yjp.delivery.common.meta;

import com.yjp.delivery.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus implements CommonEnum {
    COOKING("COOKING", "진행중"),
    DONE("DONE", "완료");

    @Getter
    private final String code;
    @Getter
    private final String value;

    @Override
    public String getName() {
        return name();
    }
}

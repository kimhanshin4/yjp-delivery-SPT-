package com.yjp.delivery.common.meta;

import com.yjp.delivery.common.template.CommonEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Social implements CommonEnum {
    KAKAO("KAKAO", "카카오"),
    GOOGLE("GOOGLE", "구글"),
    NAVER("NAVER", "네이버");

    @Getter
    private final String code;
    @Getter
    private final String value;

    @Override
    public String getName() {
        return name();
    }
}

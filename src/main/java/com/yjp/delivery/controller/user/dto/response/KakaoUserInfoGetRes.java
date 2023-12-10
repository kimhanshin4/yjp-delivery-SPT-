package com.yjp.delivery.controller.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoGetRes {

    private Long id;
    private String nickname;
    private String email;
}

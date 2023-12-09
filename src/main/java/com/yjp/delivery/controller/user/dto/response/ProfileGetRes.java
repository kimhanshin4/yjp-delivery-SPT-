package com.yjp.delivery.controller.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileGetRes {

    private String email;
    private String username;
    private String introduction;
    private String profileImageUrl;
}

package com.yjp.delivery.controller.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileEditReq {

    private Long userId;
    private String username;
    private String introduction;
    private String profileImageUrl;
}

package com.yjp.delivery.controller.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileEditReq {

    private Long userId;
    private String username;
    private String introduction;
}

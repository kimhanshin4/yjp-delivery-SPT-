package com.yjp.delivery.controller.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileGetReq {

    private Long userId;
}

package com.yjp.delivery.controller.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLikeReq {

    private Long userId;
    private Long shopId;
    private Boolean isLike;
}

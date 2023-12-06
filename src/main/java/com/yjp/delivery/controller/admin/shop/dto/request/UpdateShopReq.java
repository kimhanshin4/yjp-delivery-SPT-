package com.yjp.delivery.controller.admin.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShopReq {

    private Long shopId;
    private String shopName;
    private String description;
    private String location;
    private String callNumber;
}

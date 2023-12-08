package com.yjp.delivery.controller.shop.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopGetRes {

    private Long shopId;
    private String shopName;
    private String description;
    private String location;
    private String callNumber;
    private int like;
    private List<MenuGetRes> menuGetReses;
}

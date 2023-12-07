package com.yjp.delivery.controller.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuGetRes {

    private Long menuId;
    private String imageUrl;
    private String menuName;
    private int price;
}

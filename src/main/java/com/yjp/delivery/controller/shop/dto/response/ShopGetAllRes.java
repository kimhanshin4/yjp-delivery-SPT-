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
public class ShopGetAllRes {

    private List<ShopGetRes> shopGetReses;
    private int total;
}

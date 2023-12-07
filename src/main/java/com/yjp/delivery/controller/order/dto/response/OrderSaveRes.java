package com.yjp.delivery.controller.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRes {

    private String menuName;
    private int amount;
    private int price;
}

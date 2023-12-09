package com.yjp.delivery.controller.order.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetShopRes {

    private List<OrderGetResWrapper> orderGetResWrappers;
    private int total;
}

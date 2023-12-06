package com.yjp.delivery.controller.order.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveReqList {

    private List<OrderSaveReq> orderSaveReqs;
    private String username;
}

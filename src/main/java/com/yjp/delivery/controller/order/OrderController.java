package com.yjp.delivery.controller.order;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public RestResponse<OrderSaveResList> saveOrders(
        @RequestBody OrderSaveReqList orderSaveReqList) {
        return RestResponse.success(orderService.saveOrders(orderSaveReqList));
    }
}

package com.yjp.delivery.controller.order;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.order.dto.request.OrderDeleteReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetAllReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderDeleteRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetAllRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetResWrapper;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public RestResponse<OrderGetAllRes> getAllOrder(@RequestBody OrderGetAllReq orderGetAllReq) {
        if (orderGetAllReq.getShopId() == null) {
            return RestResponse.success(
                orderService.getAllOrderByUserId(orderGetAllReq.getUserId()));
        } else {
            return RestResponse.success(
                orderService.getAllOrderByShopId(orderGetAllReq.getShopId()));
        }
    }

    @GetMapping("/{orderId}")
    public RestResponse<OrderGetResWrapper> getOrder(@PathVariable Long orderId) {
        return RestResponse.success(orderService.getOrder(orderId));
    }

    @DeleteMapping
    public RestResponse<OrderDeleteRes> deleteOrder(@RequestBody OrderDeleteReq orderDeleteReq) {
        return RestResponse.success(orderService.deleteOrder(orderDeleteReq));
    }
}

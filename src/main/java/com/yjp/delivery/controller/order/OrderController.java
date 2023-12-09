package com.yjp.delivery.controller.order;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.order.dto.request.OrderDeleteReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetShopReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetUserReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderDeleteRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetResWrapper;
import com.yjp.delivery.controller.order.dto.response.OrderGetShopRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetUserRes;
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

    @GetMapping("/shops")
    public RestResponse<OrderGetShopRes> getAllOrderByShop(
        @RequestBody OrderGetShopReq orderGetShopReq) {
        return RestResponse.success(orderService.getAllOrderByShop(orderGetShopReq));
    }

    @GetMapping("/users")
    public RestResponse<OrderGetUserRes> getAllOrderByUser(
        @RequestBody OrderGetUserReq orderGetUserReq) {
        return RestResponse.success(orderService.getAllOrderByUser(orderGetUserReq));
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

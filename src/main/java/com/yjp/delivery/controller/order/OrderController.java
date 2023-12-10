package com.yjp.delivery.controller.order;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.order.dto.request.OrderDeleteReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetShopReq;
import com.yjp.delivery.controller.order.dto.request.OrderGetUserReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderDeleteRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetResWrapper;
import com.yjp.delivery.controller.order.dto.response.OrderGetShopRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetUserRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.security.UserDetailsImpl;
import com.yjp.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public RestResponse<OrderSaveResList> saveOrders(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderSaveReqList orderSaveReqList) {
        orderSaveReqList.setUsername(userDetails.getUsername());
        return RestResponse.success(orderService.saveOrders(orderSaveReqList));
    }

    @GetMapping("/shops")
    public RestResponse<OrderGetShopRes> getAllOrderByShop(
        @RequestBody OrderGetShopReq orderGetShopReq) {
        return RestResponse.success(orderService.getAllOrderByShop(orderGetShopReq));
    }

    @GetMapping("/users")
    public RestResponse<OrderGetUserRes> getAllOrderByUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderGetUserReq orderGetUserReq) {
        orderGetUserReq.setUserId(userDetails.getUser().getUserId());
        return RestResponse.success(orderService.getAllOrderByUser(orderGetUserReq));
    }

    @GetMapping
    public RestResponse<OrderGetResWrapper> getOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderGetReq orderGetUserReq) {
        orderGetUserReq.setUserId(userDetails.getUser().getUserId());
        orderGetUserReq.setRole(userDetails.getUser().getRole());
        return RestResponse.success(orderService.getOrder(orderGetUserReq));
    }

    @DeleteMapping
    public RestResponse<OrderDeleteRes> deleteOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody OrderDeleteReq orderDeleteReq) {
        orderDeleteReq.setUserId(userDetails.getUser().getUserId());
        orderDeleteReq.setRole(userDetails.getUser().getRole());
        return RestResponse.success(orderService.deleteOrder(orderDeleteReq));
    }
}

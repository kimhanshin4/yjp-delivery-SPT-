package com.yjp.delivery.service;

import static com.yjp.delivery.common.meta.OrderStatus.COOKING;

import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.provider.OrderDetailProvider;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.OrderRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDetailProvider orderDetailProvider;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderSaveResList saveOrders(OrderSaveReqList orderSaveReqList) {
        UserEntity userEntity = getUserByUsername(orderSaveReqList.getUsername());
        OrderEntity orderEntity = saveOrder(userEntity);
        List<OrderSaveRes> orderSaveReses = orderDetailProvider.saveOrderDetails(
            orderSaveReqList.getOrderSaveReqs(), orderEntity);

        return OrderSaveResList.builder()
            .orderId(orderEntity.getOrderId())
            .username(orderEntity.getUserEntity().getUsername())
            .orderSaveReses(orderSaveReses)
            .totalPrice(getTotalPrice(orderSaveReses))
            .build();
    }

    private UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserValidator.validate(userEntity);
        return userEntity;
    }

    private OrderEntity saveOrder(UserEntity userEntity) {
        return orderRepository.save(OrderEntity.builder()
            .status(COOKING)
            .userEntity(userEntity)
            .build());
    }

    private int getTotalPrice(List<OrderSaveRes> orderSaveReses) {
        return orderSaveReses.stream().mapToInt(OrderSaveRes::getPrice).sum();
    }
}

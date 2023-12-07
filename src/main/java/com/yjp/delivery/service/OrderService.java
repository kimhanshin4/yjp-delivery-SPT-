package com.yjp.delivery.service;

import static com.yjp.delivery.common.meta.OrderStatus.COOKING;

import com.yjp.delivery.common.validator.OrderValidator;
import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.order.dto.request.OrderDeleteReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderDeleteRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetAllRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetResWrapper;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.provider.OrderDetailProvider;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.OrderDetailEntity;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.OrderRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
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

    @Transactional(readOnly = true)
    public OrderGetAllRes getAllOrderByShopId(Long shopId) {
        List<OrderGetResWrapper> orderGetResWrappers = OrderServiceMapper.INSTANCE.toOrderGetResWrapperList(
            orderRepository.findByShopId(shopId));

        return OrderGetAllRes.builder()
            .orderGetResWrappers(orderGetResWrappers)
            .total(orderGetResWrappers.size())
            .build();
    }

    @Transactional(readOnly = true)
    public OrderGetAllRes getAllOrderByUserId(Long userId) {
        List<OrderGetResWrapper> orderGetResWrappers = OrderServiceMapper.INSTANCE.toOrderGetResWrapperList(
            orderRepository.findByUserEntityUserId(userId));

        return OrderGetAllRes.builder()
            .orderGetResWrappers(orderGetResWrappers)
            .total(orderGetResWrappers.size())
            .build();
    }

    @Transactional(readOnly = true)
    public OrderGetResWrapper getOrder(Long orderId) {
        return OrderServiceMapper.INSTANCE.toOrderGetResWrapper(
            orderRepository.findByOrderId(orderId));
    }

    public OrderDeleteRes deleteOrder(OrderDeleteReq orderDeleteReq) {
        OrderEntity orderEntity = orderRepository.findByOrderIdAndUserEntityUsername(
            orderDeleteReq.getOrderId(), orderDeleteReq.getUsername());
        OrderValidator.validate(orderEntity);
        orderRepository.delete(orderEntity);
        return new OrderDeleteRes();
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

    @Mapper
    public interface OrderServiceMapper {

        OrderServiceMapper INSTANCE = Mappers.getMapper(OrderServiceMapper.class);

        @Mapping(source = "userEntity", target = "username")
        default String toUsername(UserEntity userEntity) {
            return userEntity.getUsername();
        }

        @Mapping(source = "menuEntity", target = "menuName")
        default String toMenuName(MenuEntity menuEntity) {
            return menuEntity.getMenuName();
        }

        @Mapping(source = "orderDetailEntity", target = "price")
        default int toPrice(OrderDetailEntity orderDetailEntity) {
            return orderDetailEntity.getAmount() * orderDetailEntity.getMenuEntity().getPrice();
        }

        @Mapping(source = "orderDetailEntities", target = "totalPrice")
        default int toTotalPrice(List<OrderDetailEntity> orderDetailEntities) {
            return orderDetailEntities.stream()
                .mapToInt(order -> order.getAmount() * order.getMenuEntity().getPrice()).sum();
        }

        @Mapping(source = "orderDetailEntities", target = "shopName")
        default String toShopName(List<OrderDetailEntity> orderDetailEntities) {
            return orderDetailEntities.get(0).getMenuEntity().getShopEntity().getShopName();
        }

        @Mapping(source = "menuEntity", target = "menuName")
        @Mapping(source = "orderDetailEntity", target = "price")
        OrderGetRes toOrderGetRes(OrderDetailEntity orderDetailEntity);

        List<OrderGetRes> toOrderGetReses(List<OrderDetailEntity> orderDetailEntities);

        @Mapping(source = "orderDetailEntities", target = "orderGetReses")
        @Mapping(source = "userEntity", target = "username")
        @Mapping(source = "orderDetailEntities", target = "totalPrice")
        @Mapping(source = "orderDetailEntities", target = "shopName")
        OrderGetResWrapper toOrderGetResWrapper(OrderEntity orderEntity);

        List<OrderGetResWrapper> toOrderGetResWrapperList(List<OrderEntity> orderEntities);
    }
}

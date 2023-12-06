package com.yjp.delivery.service.provider;

import com.yjp.delivery.controller.order.dto.request.OrderSaveReq;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.OrderDetailEntity;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import com.yjp.delivery.store.repository.OrderDetailRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderDetailProvider {

    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public List<OrderSaveRes> saveOrderDetails(
        List<OrderSaveReq> orderSaveReqs, OrderEntity orderEntity) {
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
        orderSaveReqs.forEach(order -> {
            orderDetailEntities.add(OrderDetailEntity.builder()
                .amount(order.getAmount())
                .menuEntity(menuRepository.findByMenuId(order.getMenuId()))
                .orderEntity(orderEntity)
                .build());
        });

        return OrderDetailServiceMapper.INSTANCE.toOrderSaveResList(
            orderDetailRepository.saveAll(orderDetailEntities));
    }

    @Mapper
    public interface OrderDetailServiceMapper {

        OrderDetailServiceMapper INSTANCE = Mappers.getMapper(OrderDetailServiceMapper.class);

        @Mapping(source = "menuEntity", target = "menuName")
        default String toMenuName(MenuEntity menuEntity) {
            return menuEntity.getMenuName();
        }

        @Mapping(source = "orderDetailEntity", target = "price")
        default int toPrice(OrderDetailEntity orderDetailEntity) {
            return orderDetailEntity.getAmount() * orderDetailEntity.getMenuEntity().getPrice();
        }

        @Mapping(source = "menuEntity", target = "menuName")
        @Mapping(source = "orderDetailEntity", target = "price")
        OrderSaveRes toOrderSaveRes(OrderDetailEntity orderDetailEntity);

        List<OrderSaveRes> toOrderSaveResList(List<OrderDetailEntity> orderDetailEntities);
    }
}

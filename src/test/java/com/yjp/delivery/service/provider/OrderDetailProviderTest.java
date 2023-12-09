package com.yjp.delivery.service.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.order.dto.request.OrderSaveReq;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.OrderDetailEntity;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import com.yjp.delivery.store.repository.OrderDetailRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderDetailProviderTest {

    @InjectMocks
    private OrderDetailProvider orderDetailProvider;

    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 상세 저장")
    void 메뉴_상세_저장() {
        // given
        Long menuId = 1L;
        String menuName = "menu";
        int price = 100;
        int amount = 1;
        Long orderId = 1L;
        Long orderDetailId = 1L;
        OrderSaveReq orderSaveReq = OrderSaveReq.builder()
            .menuId(menuId)
            .amount(amount)
            .build();
        List<OrderSaveReq> orderSaveReqs = List.of(orderSaveReq);
        MenuEntity menu = MenuEntity.builder()
            .menuId(menuId).menuName(menuName).price(price).build();
        OrderEntity order = OrderEntity.builder().orderId(orderId).build();
        OrderDetailEntity orderDetailEntity = OrderDetailEntity.builder()
            .orderDetailId(orderDetailId)
            .amount(amount)
            .orderEntity(order)
            .menuEntity(menu)
            .build();
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
        orderDetailEntities.add(orderDetailEntity);
        when(menuRepository.findByMenuId(any())).thenReturn(menu);
        when(orderDetailRepository.saveAll(any())).thenReturn(orderDetailEntities);

        // when
        List<OrderSaveRes> orderSaveReses = orderDetailProvider.saveOrderDetails(
            orderSaveReqs, order);

        // then
        verify(menuRepository).findByMenuId(menuId);
        verify(orderDetailRepository).saveAll(any());
        assertThat(orderSaveReses.get(0).getMenuName()).isEqualTo(menuName);
        assertThat(orderSaveReses.get(0).getAmount()).isEqualTo(amount);
        assertThat(orderSaveReses.get(0).getPrice()).isEqualTo(price);
    }
}

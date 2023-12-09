package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.common.meta.OrderStatus;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.provider.OrderDetailProvider;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.OrderRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDetailProvider orderDetailProvider;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("주문 정보 저장 테스트")
    void 주문_정보_저장() {
        // given
        Long menuId = 1L;
        String menuName = "menu";
        int amount = 1;
        OrderStatus orderStatus = OrderStatus.COOKING;
        String username = "ysys";
        OrderSaveReq orderSaveReq = OrderSaveReq.builder()
            .menuId(menuId)
            .amount(amount)
            .build();
        OrderSaveReqList orderSaveReqList = OrderSaveReqList.builder()
            .orderSaveReqs(List.of(orderSaveReq))
            .username(username)
            .build();
        UserEntity user = UserEntity.builder().username(username).build();
        OrderEntity order = OrderEntity.builder()
            .status(orderStatus)
            .userEntity(user)
            .build();
        OrderSaveRes orderSaveRes = OrderSaveRes.builder()
            .menuName(menuName)
            .build();
        List<OrderSaveRes> orderSaveReses = List.of(orderSaveRes);
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderDetailProvider.saveOrderDetails(any(), any())).thenReturn(orderSaveReses);

        // when
        OrderSaveResList orderSaveResList = orderService.saveOrders(orderSaveReqList);

        // then
        assertThat(orderSaveResList.getUsername()).isEqualTo(username);
        assertThat(orderSaveResList.getOrderSaveReses().get(0).getMenuName()).isEqualTo(menuName);
        verify(userRepository).findByUsername(any());
        verify(orderRepository).save(any());
        verify(orderDetailProvider).saveOrderDetails(any(), any());
    }
}

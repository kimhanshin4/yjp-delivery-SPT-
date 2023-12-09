package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.OrderDetailEntity;
import com.yjp.delivery.store.entity.OrderEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.UserEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    private UserEntity user;
    private ShopEntity shop;
    private OrderEntity saveOrder;
    private MenuEntity menu;
    private OrderDetailEntity orderDetail;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        String username = "ysys";
        Long shopId = 1L;
        Long orderId = 1L;
        Long menuId = 1L;
        user = userRepository.save(UserEntity.builder().userId(userId).username(username).build());
        shop = shopRepository.save(ShopEntity.builder().shopId(shopId).build());
        saveOrder = orderRepository.save(OrderEntity.builder()
            .orderId(orderId)
            .userEntity(user)
            .build());
        menu = menuRepository.save(MenuEntity.builder().menuId(menuId).shopEntity(shop).build());
        orderDetail = orderDetailRepository.save(OrderDetailEntity.builder()
            .menuEntity(menu)
            .orderEntity(saveOrder)
            .build());
    }

    @Test
    @DisplayName("userId로 주문 정보 조회 테스트")
    void userId_주문_정보_조회() {
        // given
        Long userId = user.getUserId();

        // when
        List<OrderEntity> orders = orderRepository.findByUserEntityUserId(userId);

        // then
        assertThat(orders.get(0)).isEqualTo(saveOrder);
    }

    @Test
    @DisplayName("shopId로 주문 정보 조회 테스트")
    void shopId_주문_정보_조회() {
        // given
        Long shopId = shop.getShopId();

        // when
        List<OrderEntity> orders = orderRepository.findByShopId(shopId);

        // then
        assertThat(orders.get(0)).isEqualTo(saveOrder);
    }

    @Test
    @DisplayName("orderId로 주문 정보 조회 테스트")
    void orderId_주문_정보_조회() {
        // given
        Long orderId = saveOrder.getOrderId();

        // when
        OrderEntity order = orderRepository.findByOrderId(orderId);

        // then
        assertThat(order).isEqualTo(saveOrder);
    }
}

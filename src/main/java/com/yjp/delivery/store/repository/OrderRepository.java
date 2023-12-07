package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserEntityUserId(Long userId);

    @Query(value = "select o "
        + "from OrderEntity o "
        + "join OrderDetailEntity od on o.orderId=od.orderEntity.orderId "
        + "join MenuEntity m on m.menuId=od.menuEntity.menuId "
        + "join ShopEntity s on s.shopId=m.shopEntity.shopId "
        + "where s.shopId= :shopId")
    List<OrderEntity> findByShopId(Long shopId);
}

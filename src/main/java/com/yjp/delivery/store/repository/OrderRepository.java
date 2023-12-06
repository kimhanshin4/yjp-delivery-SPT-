package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}

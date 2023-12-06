package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}

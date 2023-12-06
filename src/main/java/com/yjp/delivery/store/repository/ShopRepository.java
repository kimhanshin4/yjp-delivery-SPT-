package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

    ShopEntity findByShopId(Long shopId);
}

package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;
import com.yjp.delivery.store.entity.ShopLikeEntityId;
import com.yjp.delivery.store.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopLikeRepository extends JpaRepository<ShopLikeEntity, ShopLikeEntityId> {

    ShopLikeEntity findByShopIdAndUserId(ShopEntity shopEntity, UserEntity userEntity);
}

package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.shop.dto.request.ShopLikeReq;
import com.yjp.delivery.controller.shop.dto.response.ShopLikeRes;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ShopLikeRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopLikeService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopLikeRepository shopLikeRepository;

    public ShopLikeRes likeShop(ShopLikeReq shopLikeReq) {
        ShopEntity shopEntity = getShopEntity(shopLikeReq.getShopId());
        UserEntity userEntity = getUserEntity(shopLikeReq.getUserId());

        ShopLikeEntity shopLikeEntity = getShopLike(shopEntity, userEntity);
        ShopValidator.checkAlreadyLiked(shopLikeEntity);

        shopLikeRepository.save(ShopLikeEntity.builder()
            .shopId(shopEntity)
            .userId(userEntity)
            .build());
        return new ShopLikeRes();
    }

    public ShopLikeRes unLikeShop(ShopLikeReq shopLikeReq) {
        ShopEntity shopEntity = getShopEntity(shopLikeReq.getShopId());
        UserEntity userEntity = getUserEntity(shopLikeReq.getUserId());

        ShopLikeEntity shopLikeEntity = getShopLike(shopEntity, userEntity);
        ShopValidator.checkNotYetLiked(shopLikeEntity);

        shopLikeRepository.delete(shopLikeEntity);
        return new ShopLikeRes();
    }

    private ShopEntity getShopEntity(Long shopId) {
        ShopEntity shopEntity = shopRepository.findByShopId(shopId);
        ShopValidator.validate(shopEntity);
        return shopEntity;
    }

    private UserEntity getUserEntity(Long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserValidator.validate(userEntity);
        return userEntity;
    }

    private ShopLikeEntity getShopLike(ShopEntity shopEntity, UserEntity userEntity) {
        return shopLikeRepository.findByShopIdAndUserId(shopEntity, userEntity);
    }
}

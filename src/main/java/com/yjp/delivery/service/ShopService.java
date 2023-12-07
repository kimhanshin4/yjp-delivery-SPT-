package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.shop.dto.request.ShopLikeReq;
import com.yjp.delivery.controller.shop.dto.response.MenuGetRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetRes;
import com.yjp.delivery.controller.shop.dto.response.ShopLikeRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ShopLikeRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopLikeRepository shopLikeRepository;

    @Transactional(readOnly = true)
    public ShopGetAllRes getAllShops() {
        List<ShopGetRes> shopGetReses =
            ShopServiceMapper.INSTANCE.toShopGetResList(shopRepository.findAll());
        return ShopGetAllRes.builder()
            .shopGetReses(shopGetReses)
            .total(shopGetReses.size())
            .build();
    }

    @Transactional(readOnly = true)
    public ShopGetRes getShop(Long shopId) {
        ShopEntity shopEntity = shopRepository.findByShopId(shopId);
        ShopValidator.validate(shopEntity);
        return ShopServiceMapper.INSTANCE.toShopGetRes(shopEntity);
    }

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

    @Mapper
    public interface ShopServiceMapper {

        ShopServiceMapper INSTANCE = Mappers.getMapper(ShopServiceMapper.class);

        MenuGetRes toMenuGetRes(MenuEntity menuEntity);

        List<MenuGetRes> toMenuGetReses(List<MenuEntity> menuEntities);

        @Mapping(source = "menuEntities", target = "menuGetReses")
        ShopGetRes toShopGetRes(ShopEntity shopEntity);

        List<ShopGetRes> toShopGetResList(List<ShopEntity> shopEntities);
    }
}

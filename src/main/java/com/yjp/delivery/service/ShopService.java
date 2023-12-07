package com.yjp.delivery.service;

import com.yjp.delivery.controller.shop.dto.response.MenuGetRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.ShopRepository;
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

    @Transactional(readOnly = true)
    public ShopGetAllRes getAllShops() {
        List<ShopGetRes> shopGetReses =
            ShopServiceMapper.INSTANCE.toShopGetResList(shopRepository.findAll());
        return ShopGetAllRes.builder()
            .shopGetReses(shopGetReses)
            .total(shopGetReses.size())
            .build();
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

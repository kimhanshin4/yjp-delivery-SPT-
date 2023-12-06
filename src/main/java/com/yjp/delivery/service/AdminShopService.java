package com.yjp.delivery.service;

import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminShopService {

  private final ShopRepository shopRepository;

  public AddShopRes addShop(AddShopReq addShopReq) {
    return AdminShopServiceMapper.INSTANCE.toAddShopRes(
        shopRepository.save(ShopEntity.builder()
            .shopName(addShopReq.getShopName())
            .description(addShopReq.getDescription())
            .location(addShopReq.getLocation())
            .callNumber(addShopReq.getCallNumber())
            .build()));
  }

  @Mapper
  public interface AdminShopServiceMapper {

    AdminShopServiceMapper INSTANCE = Mappers.getMapper(AdminShopServiceMapper.class);

    AddShopRes toAddShopRes(ShopEntity shopEntity);
  }
}

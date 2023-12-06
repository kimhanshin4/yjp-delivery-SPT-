package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.request.UpdateShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.controller.admin.shop.dto.response.UpdateShopRes;
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

  public UpdateShopRes updateShop(UpdateShopReq updateShopReq) {
    ShopEntity shopEntity = shopRepository.findByShopName(updateShopReq.getShopName());
    ShopValidator.validate(shopEntity);
    shopEntity = ShopEntity.builder()
        .shopName(updateShopReq.getNewShopName())
        .description(updateShopReq.getDescription())
        .location(updateShopReq.getLocation())
        .callNumber(updateShopReq.getCallNumber())
        .build();
    return AdminShopServiceMapper.INSTANCE.toUpdateShopRes(shopEntity);
  }

  @Mapper
  public interface AdminShopServiceMapper {

    AdminShopServiceMapper INSTANCE = Mappers.getMapper(AdminShopServiceMapper.class);

    AddShopRes toAddShopRes(ShopEntity shopEntity);

    UpdateShopRes toUpdateShopRes(ShopEntity shopEntity);
  }
}

package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    public AddMenuRes addMenu(AddMenuReq addMenuReq) {
        ShopEntity shopEntity = shopRepository.findByShopId(addMenuReq.getShopId());
        ShopValidator.validate(shopEntity);
        return AdminMenuServiceMapper.INSTANCE.toAddMenuRes(
            menuRepository.save(MenuEntity.builder()
                .imageUrl(addMenuReq.getImageUrl())
                .menuName(addMenuReq.getMenuName())
                .price(addMenuReq.getPrice())
                .shopEntity(shopEntity)
                .build()));
    }

    @Mapper
    public interface AdminMenuServiceMapper {

        AdminMenuServiceMapper INSTANCE = Mappers.getMapper(AdminMenuServiceMapper.class);

        AddMenuRes toAddMenuRes(MenuEntity menuEntity);
    }
}

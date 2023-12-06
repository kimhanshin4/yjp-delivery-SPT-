package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.MenuValidator;
import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.DeleteMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.UpdateMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.DeleteMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.UpdateMenuRes;
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

    public UpdateMenuRes updateMenu(UpdateMenuReq updateMenuReq) {
        ShopEntity shopEntity = shopRepository.findByShopId(updateMenuReq.getShopId());
        ShopValidator.validate(shopEntity);
        MenuEntity menuEntity = menuRepository.findByMenuId(updateMenuReq.getMenuId());
        MenuValidator.validate(menuEntity);
        return AdminMenuServiceMapper.INSTANCE.toUpdateMenuRes(
            menuRepository.save(MenuEntity.builder()
                .menuId(updateMenuReq.getMenuId())
                .imageUrl(updateMenuReq.getImageUrl())
                .menuName(updateMenuReq.getMenuName())
                .price(updateMenuReq.getPrice())
                .shopEntity(shopEntity)
                .build()));
    }

    public DeleteMenuRes deleteMenu(DeleteMenuReq deleteMenuReq) {
        ShopEntity shopEntity = shopRepository.findByShopId(deleteMenuReq.getShopId());
        ShopValidator.validate(shopEntity);
        MenuEntity menuEntity = menuRepository.findByMenuId(deleteMenuReq.getMenuId());
        MenuValidator.validate(menuEntity);
        menuRepository.delete(menuEntity);
        return AdminMenuServiceMapper.INSTANCE.toDeleteMenuRes(
            MenuEntity.builder()
                .menuId(deleteMenuReq.getMenuId())
                .shopEntity(shopEntity)
                .build());
    }

    @Mapper
    public interface AdminMenuServiceMapper {

        AdminMenuServiceMapper INSTANCE = Mappers.getMapper(AdminMenuServiceMapper.class);

        AddMenuRes toAddMenuRes(MenuEntity menuEntity);

        UpdateMenuRes toUpdateMenuRes(MenuEntity menuEntity);

        DeleteMenuRes toDeleteMenuRes(MenuEntity menuEntity);
    }
}

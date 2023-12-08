package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.MenuValidator;
import com.yjp.delivery.controller.menu.dto.response.MenuGetRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuGetRes getMenu(Long menuId) {
        MenuEntity menuEntity = menuRepository.findByMenuId(menuId);
        MenuValidator.validate(menuEntity);
        return MenuServiceMapper.INSTANCE.toMenuGetRes(menuEntity);
    }

    @Mapper
    public interface MenuServiceMapper {

        MenuService.MenuServiceMapper INSTANCE = Mappers.getMapper(
            MenuService.MenuServiceMapper.class);

        MenuGetRes toMenuGetRes(MenuEntity menuEntity);

    }
}

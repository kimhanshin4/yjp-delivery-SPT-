package com.yjp.delivery.controller.menu;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.menu.dto.response.MenuGetRes;
import com.yjp.delivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;


    @GetMapping("/{menuId}")
    public RestResponse<MenuGetRes> getMenu(@PathVariable(name = "menuId") Long menuId) {
        return RestResponse.success(menuService.getMenu(menuId));
    }

}

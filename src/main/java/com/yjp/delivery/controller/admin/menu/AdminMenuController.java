package com.yjp.delivery.controller.admin.menu;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.service.AdminMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/menu")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping
    public RestResponse<AddMenuRes> addMenu(@RequestBody AddMenuReq addMenuReq) {
        return RestResponse.success(adminMenuService.addMenu(addMenuReq));
    }
}

package com.yjp.delivery.controller.admin.menu;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.DeleteMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.UpdateMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.DeleteMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.UpdateMenuRes;
import com.yjp.delivery.service.AdminMenuService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/admin/menu")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping
    public RestResponse<AddMenuRes> addMenu(@RequestPart MultipartFile multipartFile,
        @RequestPart AddMenuReq addMenuReq) throws IOException {
        return RestResponse.success(adminMenuService.addMenu(multipartFile, addMenuReq));
    }

    @PatchMapping
    public RestResponse<UpdateMenuRes> updateMenu(@RequestPart MultipartFile multipartFile,
        @RequestPart UpdateMenuReq updateMenuReq) throws IOException {
        return RestResponse.success(adminMenuService.updateMenu(multipartFile, updateMenuReq));
    }

    @DeleteMapping
    public RestResponse<DeleteMenuRes> deleteMenu(@RequestBody DeleteMenuReq deleteMenuReq) {
        return RestResponse.success(adminMenuService.deleteMenu(deleteMenuReq));
    }

}

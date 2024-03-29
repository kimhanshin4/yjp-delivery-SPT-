package com.yjp.delivery.controller.admin.shop;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.request.DeleteShopReq;
import com.yjp.delivery.controller.admin.shop.dto.request.UpdateShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.controller.admin.shop.dto.response.DeleteShopRes;
import com.yjp.delivery.controller.admin.shop.dto.response.UpdateShopRes;
import com.yjp.delivery.service.AdminShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminShopService adminShopService;

    @PostMapping
    public RestResponse<AddShopRes> addShop(@RequestBody AddShopReq addShopReq) {
        return RestResponse.success(adminShopService.addShop(addShopReq));
    }

    @PatchMapping
    public RestResponse<UpdateShopRes> updateShop(@RequestBody UpdateShopReq updateShopReq) {
        return RestResponse.success(adminShopService.updateShop(updateShopReq));
    }

    @DeleteMapping
    public RestResponse<DeleteShopRes> deleteShop(@RequestBody DeleteShopReq deleteShopReq) {
        return RestResponse.success(adminShopService.deleteShop(deleteShopReq));
    }
}

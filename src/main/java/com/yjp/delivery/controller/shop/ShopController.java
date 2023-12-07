package com.yjp.delivery.controller.shop;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetRes;
import com.yjp.delivery.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/shop")
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public RestResponse<ShopGetAllRes> getAllShops() {
        return RestResponse.success(shopService.getAllShops());
    }

    @GetMapping("/{shopId}")
    public RestResponse<ShopGetRes> getShop(@PathVariable Long shopId) {
        return RestResponse.success(shopService.getShop(shopId));
    }
}

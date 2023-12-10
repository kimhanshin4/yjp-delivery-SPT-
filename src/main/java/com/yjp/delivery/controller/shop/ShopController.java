package com.yjp.delivery.controller.shop;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.shop.dto.request.ShopLikeReq;
import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetRes;
import com.yjp.delivery.controller.shop.dto.response.ShopLikeRes;
import com.yjp.delivery.security.UserDetailsImpl;
import com.yjp.delivery.service.ShopLikeService;
import com.yjp.delivery.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/shops")
public class ShopController {

    private final ShopService shopService;
    private final ShopLikeService shopLikeService;

    @GetMapping
    public RestResponse<ShopGetAllRes> getAllShops() {
        return RestResponse.success(shopService.getAllShops());
    }

    @GetMapping("/{shopId}")
    public RestResponse<ShopGetRes> getShop(@PathVariable Long shopId) {
        return RestResponse.success(shopService.getShop(shopId));
    }

    @PostMapping("/like")
    public RestResponse<ShopLikeRes> likeShop(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ShopLikeReq shopLikeReq) {
        shopLikeReq.setUserId(userDetails.getUser().getUserId());
        if (shopLikeReq.getIsLike()) {
            return RestResponse.success(shopLikeService.likeShop(shopLikeReq));
        } else {
            return RestResponse.success(shopLikeService.unLikeShop(shopLikeReq));
        }
    }
}

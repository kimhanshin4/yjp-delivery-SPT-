package com.yjp.delivery.controller.admin.shop;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.service.AdminShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/shop")
@RequiredArgsConstructor
public class AdminShopController {

  private final AdminShopService adminShopService;

  @PostMapping
  public RestResponse<AddShopRes> addShop(@RequestBody AddShopReq addShopReq) {
    return RestResponse.success(adminShopService.addShop(addShopReq));
  }
}

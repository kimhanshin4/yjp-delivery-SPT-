package com.yjp.delivery.controller.admin.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShopRes {

  private String shopName;
  private String description;
  private String location;
  private String callNumber;
}

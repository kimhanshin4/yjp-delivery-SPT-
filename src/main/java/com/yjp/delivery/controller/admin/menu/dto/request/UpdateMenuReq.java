package com.yjp.delivery.controller.admin.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuReq {

    private Long menuId;
    private String menuName;
    private int price;
    private Long shopId;
}

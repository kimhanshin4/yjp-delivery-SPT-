package com.yjp.delivery.controller.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuGetRes {

    private String menuName;
    private String imageUrl;
    private int price;


}

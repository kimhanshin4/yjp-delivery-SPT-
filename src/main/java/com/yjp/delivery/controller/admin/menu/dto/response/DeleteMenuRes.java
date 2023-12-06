package com.yjp.delivery.controller.admin.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMenuRes {

    private Long menuId;
    private Long shorId;
}

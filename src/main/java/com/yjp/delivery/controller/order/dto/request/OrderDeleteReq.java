package com.yjp.delivery.controller.order.dto.request;

import com.yjp.delivery.common.meta.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeleteReq {

    private Long orderId;
    private Long userId;
    private Role role;
}

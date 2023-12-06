package com.yjp.delivery.store.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopLikeEntityId implements Serializable {

    private Long userId;
    private Long shopId;
}

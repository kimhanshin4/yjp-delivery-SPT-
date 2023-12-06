package com.yjp.delivery.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ShopLikeEntityId.class)
@Table(name = "shop_likes")
public class ShopLikeEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "shopId")
    private ShopEntity shopId;
}

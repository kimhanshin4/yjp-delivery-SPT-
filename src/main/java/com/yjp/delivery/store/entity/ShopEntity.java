package com.yjp.delivery.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
public class ShopEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
    private String shopName;
    private String description;
    private String location;
    private String callNumber;

    @OneToMany(mappedBy = "shopEntity", cascade = CascadeType.ALL)
    private List<MenuEntity> menuEntities;

    @OneToMany(mappedBy = "shopId", cascade = CascadeType.ALL)
    private List<ShopLikeEntity> shopLikeEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "shopEntity", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviewEntities;
}

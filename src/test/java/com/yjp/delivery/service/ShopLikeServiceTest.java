package com.yjp.delivery.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.shop.dto.request.ShopLikeReq;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ShopLikeRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShopLikeServiceTest {

    @InjectMocks
    private ShopLikeService shopLikeService;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ShopLikeRepository shopLikeRepository;

    @Test
    @DisplayName("가게 좋아요 테스트")
    void 가게_좋아요() {
        // given
        Long userId = 1L;
        Long shopId = 1L;
        Boolean isLike = true;
        ShopLikeReq shopLikeReq = ShopLikeReq.builder()
            .userId(userId)
            .shopId(shopId)
            .isLike(isLike)
            .build();
        UserEntity user = UserEntity.builder().userId(userId).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        when(shopRepository.findByShopId(any())).thenReturn(shop);
        when(userRepository.findByUserId(any())).thenReturn(user);
        when(shopLikeRepository.findByShopIdAndUserId(any(), any())).thenReturn(null);

        // when
        shopLikeService.likeShop(shopLikeReq);

        // then
        verify(shopRepository).findByShopId(any());
        verify(userRepository).findByUserId(any());
        verify(shopLikeRepository).findByShopIdAndUserId(any(), any());
        verify(shopLikeRepository).save(any());
    }

    @Test
    @DisplayName("가게 좋아요 취소 테스트")
    void 가게_좋아요_취소() {
        // given
        Long userId = 1L;
        Long shopId = 1L;
        Boolean isLike = false;
        ShopLikeReq shopLikeReq = ShopLikeReq.builder()
            .userId(userId)
            .shopId(shopId)
            .isLike(isLike)
            .build();
        UserEntity user = UserEntity.builder().userId(userId).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        ShopLikeEntity shopLike = ShopLikeEntity.builder()
            .shopId(shop)
            .userId(user)
            .build();
        when(shopRepository.findByShopId(any())).thenReturn(shop);
        when(userRepository.findByUserId(any())).thenReturn(user);
        when(shopLikeRepository.findByShopIdAndUserId(any(), any())).thenReturn(shopLike);

        // when
        shopLikeService.unLikeShop(shopLikeReq);

        // then
        verify(shopRepository).findByShopId(any());
        verify(userRepository).findByUserId(any());
        verify(shopLikeRepository).findByShopIdAndUserId(any(), any());
        verify(shopLikeRepository).delete(any());
    }
}

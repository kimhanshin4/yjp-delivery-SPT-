package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.request.DeleteShopReq;
import com.yjp.delivery.controller.admin.shop.dto.request.UpdateShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.controller.admin.shop.dto.response.UpdateShopRes;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AdminShopServiceTest {

    @InjectMocks
    private AdminShopService adminShopService;

    @Mock
    private ShopRepository shopRepository;

    @Test
    @DisplayName("가게 추가 테스트")
    void 가게_추가() {
        // given
        String shopName = "shop";
        AddShopReq addShopReq = AddShopReq.builder().shopName(shopName).build();
        ShopEntity shop = ShopEntity.builder().shopName(shopName).build();
        when(shopRepository.save(any())).thenReturn(shop);

        // when
        AddShopRes addShopRes = adminShopService.addShop(addShopReq);

        // then
        assertThat(addShopRes.getShopName()).isEqualTo(shopName);
        verify(shopRepository).save(any());
    }

    @Test
    @DisplayName("가게 수정 테스트")
    void 가게_수정() {
        // given
        Long shopId = 1L;
        String shopName = "shop";
        String updatedShopName = "updatedShop";
        UpdateShopReq updateShopReq = UpdateShopReq.builder()
            .shopId(shopId)
            .shopName(updatedShopName)
            .build();
        ShopEntity shop = ShopEntity.builder()
            .shopId(shopId)
            .shopName(shopName)
            .build();
        ShopEntity updatedShop = ShopEntity.builder()
            .shopId(shopId)
            .shopName(updatedShopName)
            .build();
        when(shopRepository.findByShopId(any())).thenReturn(shop);
        when(shopRepository.save(any())).thenReturn(updatedShop);

        // when
        UpdateShopRes updateShopRes = adminShopService.updateShop(updateShopReq);

        // then
        assertThat(updateShopRes.getShopName()).isEqualTo(updatedShopName);
        verify(shopRepository).findByShopId(any());
        verify(shopRepository).save(any());
    }

    @Test
    @DisplayName("가게 삭제 테스트")
    void 가제_삭제() {
        // given
        Long shopId = 1L;
        DeleteShopReq deleteShopReq = DeleteShopReq.builder().shopId(shopId).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        when(shopRepository.findByShopId(any())).thenReturn(shop);

        // when
        adminShopService.deleteShop(deleteShopReq);

        // then
        verify(shopRepository).findByShopId(any());
        verify(shopRepository).delete(any());
    }
}

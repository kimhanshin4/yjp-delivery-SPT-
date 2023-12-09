package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.ShopRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Test
    @DisplayName("가게 정보 전체 조회 테스트")
    void 가게_정보_전체_조회() {
        // given
        Long shopId = 1L;
        String shopName = "shop";
        ShopEntity shopEntity = ShopEntity.builder()
            .shopId(shopId)
            .shopName(shopName)
            .shopLikeEntities(new ArrayList<>())
            .build();
        List<ShopEntity> shopEntities = List.of(shopEntity);
        when(shopRepository.findAll()).thenReturn(shopEntities);

        // when
        ShopGetAllRes shopGetAllRes = shopService.getAllShops();

        // then
        verify(shopRepository).findAll();
        assertThat(shopGetAllRes.getTotal()).isEqualTo(1);
        assertThat(shopGetAllRes.getShopGetReses().get(0).getShopId()).isEqualTo(shopId);
    }
}

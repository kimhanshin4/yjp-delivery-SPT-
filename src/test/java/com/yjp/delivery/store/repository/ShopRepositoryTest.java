package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.ShopEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;
    private ShopEntity saveShop;

    @BeforeEach
    void setUp() {
        Long shopId = 1L;
        String shopName = "shop";
        saveShop = shopRepository.save(ShopEntity.builder()
            .shopId(shopId)
            .shopName(shopName)
            .build());
    }

    @Test
    @DisplayName("shopId로 가게 정보 조회 테스트")
    void shopId_가게_조회() {
        // given
        Long shopId = 1L;

        // when
        ShopEntity shop = shopRepository.findByShopId(shopId);

        // then
        assertThat(shop).isEqualTo(saveShop);
    }
}

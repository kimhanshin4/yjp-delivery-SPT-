package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.ShopLikeEntity;
import com.yjp.delivery.store.entity.UserEntity;
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
class ShopLikeRepositoryTest {

    @Autowired
    private ShopLikeRepository shopLikeRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserRepository userRepository;
    private UserEntity user;
    private ShopEntity shop;
    private ShopLikeEntity saveShopLike;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        Long shopId = 1L;
        user = userRepository.save(UserEntity.builder().userId(userId).build());
        shop = shopRepository.save(ShopEntity.builder().shopId(shopId).build());
        saveShopLike = shopLikeRepository.save(ShopLikeEntity.builder()
            .userId(user)
            .shopId(shop)
            .build());
    }

    @Test
    @DisplayName("user, shop으로 가게 좋아요 정보 조회 테스트")
    void user_shop_가게_좋아요_조회() {
        // given
        Long userId = 1L;
        Long shopId = 1L;
        UserEntity user = UserEntity.builder().userId(userId).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();

        // when
        ShopLikeEntity shopLike = shopLikeRepository.findByShopIdAndUserId(shop, user);

        // then
        assertThat(shopLike).isEqualTo(saveShopLike);
    }
}

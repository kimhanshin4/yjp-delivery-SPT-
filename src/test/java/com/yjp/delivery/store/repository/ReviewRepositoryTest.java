package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.ReviewEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.UserEntity;
import java.util.List;
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
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    private UserEntity user;
    private ShopEntity shop;
    private ReviewEntity saveReview;

    @BeforeEach
    void setUp() {
        Long reviewId = 1L;
        Long shopId = 1L;
        String username = "ysys";
        user = userRepository.save(UserEntity.builder().username(username).build());
        shop = shopRepository.save(ShopEntity.builder().shopId(shopId).build());
        saveReview = reviewRepository.save(ReviewEntity.builder()
            .reviewId(reviewId)
            .userEntity(user)
            .shopEntity(shop)
            .build());
    }

    @Test
    @DisplayName("reviewId로 리뷰 조회 테스트")
    void reviewId_리뷰_조회() {
        // given
        Long reviewId = saveReview.getReviewId();

        // when
        ReviewEntity review = reviewRepository.findByReviewId(reviewId);

        // then
        assertThat(review).isEqualTo(saveReview);
    }

    @Test
    @DisplayName("reviewId, username으로 리뷰 조회 테스트")
    void reviewId_username_리뷰_조회() {
        // given
        Long reviewId = saveReview.getReviewId();
        String username = "ysys";

        // when
        ReviewEntity review = reviewRepository.findByReviewIdAndUserEntityUsername(
            reviewId, username);

        // then
        assertThat(review).isEqualTo(saveReview);
    }

    @Test
    @DisplayName("shopId로 리뷰 조회 테스트")
    void shopId_리뷰_조회() {
        // given
        Long shopId = shop.getShopId();

        // when
        List<ReviewEntity> reviews = reviewRepository.findByShopEntityShopId(shopId);

        // then
        assertThat(reviews.get(0)).isEqualTo(saveReview);
    }
}

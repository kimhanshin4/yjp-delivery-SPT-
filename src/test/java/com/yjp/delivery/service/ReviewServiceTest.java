package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.review.dto.request.ReviewDeleteReq;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqShop;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqUser;
import com.yjp.delivery.controller.review.dto.request.ReviewSaveReq;
import com.yjp.delivery.controller.review.dto.request.ReviewUpdateReq;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResShop;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResUser;
import com.yjp.delivery.controller.review.dto.response.ReviewSaveRes;
import com.yjp.delivery.controller.review.dto.response.ReviewUpdateRes;
import com.yjp.delivery.service.provider.S3Provider;
import com.yjp.delivery.store.entity.ReviewEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ReviewRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private S3Provider s3Provider;

    @Test
    @DisplayName("리뷰 저장 테스트")
    void 리뷰_저장() throws IOException {
        // given
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        MultipartFile multipartFile = null;
        String imageUrl = "url";
        ReviewSaveReq req = ReviewSaveReq.builder()
            .shopId(shopId)
            .username(username)
            .content(content)
            .build();
        UserEntity user = UserEntity.builder().username(username).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        ReviewEntity review = ReviewEntity.builder()
            .shopEntity(shop)
            .userEntity(user)
            .content(content)
            .imageUrl(imageUrl)
            .build();
        when(s3Provider.saveFile(any(), any())).thenReturn(imageUrl);
        when(shopRepository.findByShopId(any())).thenReturn(shop);
        when(userRepository.findByUsername(any())).thenReturn(user);
        when(reviewRepository.save(any())).thenReturn(review);

        // when
        ReviewSaveRes reviewSaveRes = reviewService.saveReview(req, multipartFile);

        // then
        assertThat(reviewSaveRes.getUsername()).isEqualTo(username);
        assertThat(reviewSaveRes.getContent()).isEqualTo(content);
        verify(s3Provider).saveFile(any(), any());
        verify(shopRepository).findByShopId(any());
        verify(userRepository).findByUsername(any());
        verify(reviewRepository).save(any());
    }

    @Test
    @DisplayName("shopId로 리뷰 조회 테스트")
    void shopId_리뷰_조회() {
        // given
        Long shopId = 1L;
        String username = "ysys";
        ReviewGetReqShop reviewGetReqShop = ReviewGetReqShop.builder().shopId(shopId).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        UserEntity user = UserEntity.builder().username(username).build();
        ReviewEntity review = ReviewEntity.builder().shopEntity(shop).userEntity(user).build();
        List<ReviewEntity> reviews = List.of(review);
        when(reviewRepository.findByShopEntityShopId(any())).thenReturn(reviews);

        // when
        List<ReviewGetResShop> reviewGetResShops = reviewService.findShopReview(reviewGetReqShop);

        // then
        assertThat(reviewGetResShops.get(0).getUsername()).isEqualTo(username);
        assertThat(reviewGetResShops.get(0).getShopId()).isEqualTo(shopId);
        verify(reviewRepository).findByShopEntityShopId(any());
    }

    @Test
    @DisplayName("username으로 리뷰 조회 테스트")
    void username_리뷰_조회() {
        // given
        Long shopId = 1L;
        String username = "ysys";
        ReviewGetReqUser reviewGetReqUser = ReviewGetReqUser.builder().username(username).build();
        ShopEntity shop = ShopEntity.builder().shopId(shopId).build();
        UserEntity user = UserEntity.builder().username(username).build();
        ReviewEntity review = ReviewEntity.builder().shopEntity(shop).userEntity(user).build();
        List<ReviewEntity> reviews = List.of(review);
        when(reviewRepository.findByUserEntityUsername(any())).thenReturn(reviews);

        // when
        List<ReviewGetResUser> reviewGetResUsers = reviewService.findUserReview(reviewGetReqUser);

        // then
        assertThat(reviewGetResUsers.get(0).getUsername()).isEqualTo(username);
        assertThat(reviewGetResUsers.get(0).getShopId()).isEqualTo(shopId);
        verify(reviewRepository).findByUserEntityUsername(any());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void 리뷰_수정() throws IOException {
        // given
        Long reviewId = 1L;
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        String updatedContent = "updatedContent";
        MultipartFile multipartFile = null;
        ReviewUpdateReq reviewUpdateReq = ReviewUpdateReq.builder()
            .reviewId(reviewId)
            .shopId(shopId)
            .username(username)
            .content(content)
            .build();
        UserEntity user = UserEntity.builder()
            .username(username)
            .build();
        ShopEntity shop = ShopEntity.builder()
            .shopId(shopId)
            .build();
        ReviewEntity review = ReviewEntity.builder()
            .reviewId(reviewId)
            .userEntity(user)
            .shopEntity(shop)
            .content(content)
            .build();
        ReviewEntity updatedReview = ReviewEntity.builder()
            .reviewId(reviewId)
            .userEntity(user)
            .shopEntity(shop)
            .content(updatedContent)
            .build();
        when(reviewRepository.findByReviewIdAndUserEntityUsername(any(), any())).thenReturn(review);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(shopRepository.findByShopId(shopId)).thenReturn(shop);
        when(reviewRepository.save(any())).thenReturn(updatedReview);

        // when
        ReviewUpdateRes reviewUpdateRes = reviewService.updateReview(
            reviewUpdateReq, multipartFile);

        // then
        assertThat(reviewUpdateRes.getContent()).isEqualTo(updatedContent);
        verify(reviewRepository).findByReviewIdAndUserEntityUsername(any(), any());
        verify(userRepository).findByUsername(any());
        verify(shopRepository).findByShopId(any());
        verify(reviewRepository).save(any());
        verify(s3Provider).deleteImage(any());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void 리뷰_삭제() {
        // given
        Long reviewId = 1L;
        String username = "ysys";
        ReviewDeleteReq reviewDeleteReq = ReviewDeleteReq.builder()
            .reviewId(reviewId)
            .username(username)
            .build();
        ReviewEntity review = ReviewEntity.builder()
            .reviewId(reviewId)
            .build();
        when(reviewRepository.findByReviewIdAndUserEntityUsername(any(), any())).thenReturn(review);

        // when
        reviewService.deleteReview(reviewDeleteReq);

        // then
        verify(reviewRepository).findByReviewIdAndUserEntityUsername(any(), any());
        verify(reviewRepository).delete(any());
    }
}

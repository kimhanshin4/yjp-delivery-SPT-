package com.yjp.delivery.service;


import com.yjp.delivery.common.response.ReviewGetRes;
import com.yjp.delivery.common.validator.ReviewValidator;
import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.review.dto.request.ReviewSaveReq;
import com.yjp.delivery.controller.review.dto.request.ReviewUpdateReq;
import com.yjp.delivery.controller.review.dto.response.ReviewSaveRes;
import com.yjp.delivery.controller.review.dto.response.ReviewUpdateRes;
import com.yjp.delivery.store.entity.ReviewEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ReviewRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ReviewSaveRes saveReview(ReviewSaveReq req) {
        ShopEntity shopEntity = findShop(req.getShopId());
        UserEntity userEntity = findUser(req.getUsername());
        return ReviewServiceMapper.INSTANCE.toReviewSaveRes(
            reviewRepository.save(ReviewEntity.builder()
                .shopEntity(shopEntity)
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .userEntity(userEntity)
                .build()));
    }


    public ReviewUpdateRes updateReview(ReviewUpdateReq req) {
        ReviewEntity reviewEntity = reviewRepository.findByReviewId(req.getReviewId());
        ReviewValidator.validate(reviewEntity);
        ShopEntity shopEntity = shopRepository.findByShopId(req.getShopId());
        ShopValidator.validate(shopEntity);
        UserEntity userEntity = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(userEntity);
        return ReviewServiceMapper.INSTANCE.toReviewUpdateRes(
            reviewRepository.save(ReviewEntity.builder()
                .reviewId(req.getReviewId())
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .build()));
    }

    private UserEntity findUser(String name) {
        UserEntity userEntity = userRepository.findByUsername(name);
        UserValidator.validate(userEntity);
        return userEntity;
    }

    private ShopEntity findShop(Long id) {
        ShopEntity shopEntity = shopRepository.findByShopId(id);
        ShopValidator.validate(shopEntity);
        return shopEntity;
    }

    @Mapper
    public interface ReviewServiceMapper {

        ReviewService.ReviewServiceMapper INSTANCE = Mappers.getMapper(
            ReviewService.ReviewServiceMapper.class);

        ReviewGetRes toReviewGetRes(ReviewEntity reviewEntity);

        ReviewSaveRes toReviewSaveRes(ReviewEntity reviewEntity);

        ReviewUpdateRes toReviewUpdateRes(ReviewEntity reviewEntity);

    }


}

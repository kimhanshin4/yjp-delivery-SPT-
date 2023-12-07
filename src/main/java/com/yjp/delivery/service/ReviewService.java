package com.yjp.delivery.service;


import com.yjp.delivery.common.validator.ReviewValidator;
import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.common.validator.UserValidator;
import com.yjp.delivery.controller.review.dto.request.ReviewDeleteReq;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqShop;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqUser;
import com.yjp.delivery.controller.review.dto.request.ReviewSaveReq;
import com.yjp.delivery.controller.review.dto.request.ReviewUpdateReq;
import com.yjp.delivery.controller.review.dto.response.ReviewDeleteRes;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResShop;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResUser;
import com.yjp.delivery.controller.review.dto.response.ReviewSaveRes;
import com.yjp.delivery.controller.review.dto.response.ReviewUpdateRes;
import com.yjp.delivery.store.entity.ReviewEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.entity.UserEntity;
import com.yjp.delivery.store.repository.ReviewRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import com.yjp.delivery.store.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ReviewGetResShop> findShopReview(ReviewGetReqShop req) {
        return ReviewServiceMapper.INSTANCE.toReviewGetResShopList(
            reviewRepository.findByShopEntityShopId(req.getShopId()));
    }

    public List<ReviewGetResUser> findUserReview(ReviewGetReqUser req) {
        return ReviewServiceMapper.INSTANCE.toReviewGetResUserList(
            reviewRepository.findByUserEntityUsername(req.getUsername()));
    }


    @Transactional
    public ReviewUpdateRes updateReview(ReviewUpdateReq req) {
        ReviewEntity reviewEntity = reviewRepository.findByReviewIdAndUserEntityUsername(
            req.getReviewId(), req.getUsername());
        ReviewValidator.validate(reviewEntity);
        UserEntity userEntity = findUser(req.getUsername());
        ShopEntity shopEntity = findShop(req.getShopId());
        return ReviewServiceMapper.INSTANCE.toReviewUpdateRes(
            reviewRepository.save(ReviewEntity.builder()
                .reviewId(req.getReviewId())
                .shopEntity(shopEntity)
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .userEntity(userEntity)
                .build()));
    }


    public ReviewDeleteRes deleteReview(ReviewDeleteReq req) {
        ReviewEntity reviewEntity = reviewRepository.findByReviewIdAndUserEntityUsername(
            req.getReviewId(), req.getUsername());
        ReviewValidator.validate(reviewEntity);
        reviewRepository.delete(reviewEntity);
        return new ReviewDeleteRes();
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

    private ReviewEntity findReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findByReviewId(id);
        ReviewValidator.validate(reviewEntity);
        return reviewEntity;
    }

    @Mapper
    public interface ReviewServiceMapper {

        ReviewService.ReviewServiceMapper INSTANCE = Mappers.getMapper(
            ReviewService.ReviewServiceMapper.class);

        List<ReviewGetResShop> toReviewGetResShopList(List<ReviewEntity> reviewEntityList);

        @Mapping(source = "userEntity", target = "username")
        ReviewGetResShop toReviewGetResShop(ReviewEntity reviewEntity);

        @Mapping(source = "userEntity", target = "username")
        default String toUserName(UserEntity userEntity) {
            return userEntity.getUsername();
        }

        List<ReviewGetResUser> toReviewGetResUserList(List<ReviewEntity> reviewEntityList);

        @Mapping(source = "shopEntity", target = "shopId")
        ReviewGetResUser toReviewGetResUser(ReviewEntity reviewEntity);

        @Mapping(source = "shopEntity", target = "shopId")
        default Long toShopId(ShopEntity shopEntity) {
            return shopEntity.getShopId();
        }

        ReviewSaveRes toReviewSaveRes(ReviewEntity reviewEntity);

        ReviewUpdateRes toReviewUpdateRes(ReviewEntity reviewEntity);

        ReviewDeleteRes toReviewDeleteRes(ReviewEntity reviewEntity);

    }


}

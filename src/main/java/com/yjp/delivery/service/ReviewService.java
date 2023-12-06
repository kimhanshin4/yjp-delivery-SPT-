package com.yjp.delivery.service;


import com.yjp.delivery.common.response.ReviewGetRes;
import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.controller.sample.dto.request.sample.ReviewSaveReq;
import com.yjp.delivery.controller.sample.dto.response.ReviewSaveRes;
import com.yjp.delivery.store.entity.ReviewEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.ReviewRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    public ReviewSaveRes saveReview(ReviewSaveReq req) {
        ShopEntity shopEntity = shopRepository.findByShopId(req.getShopId());
        ShopValidator.validate(shopEntity);
        return ReviewServiceMapper.INSTANCE.toReviewSaveRes(
            reviewRepository.save(ReviewEntity.builder()
                .shopEntity(shopEntity)
                .content(req.getContent())
                .imageUrl(req.getImageUrl())
                .build()));
    }


    @Mapper
    public interface ReviewServiceMapper {

        ReviewService.ReviewServiceMapper INSTANCE = Mappers.getMapper(
            ReviewService.ReviewServiceMapper.class);

        ReviewGetRes toReviewGetRes(ReviewEntity reviewEntity);

        ReviewSaveRes toReviewSaveRes(ReviewEntity reviewEntity);
    }
}

package com.yjp.delivery.controller.review;

import com.yjp.delivery.common.response.RestResponse;
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
import com.yjp.delivery.service.ReviewService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public RestResponse<ReviewSaveRes> saveReview(@RequestPart ReviewSaveReq req,
        @RequestPart MultipartFile multipartFile)
        throws IOException {
        return RestResponse.success(reviewService.saveReview(req, multipartFile));
    }

    @PatchMapping
    public RestResponse<ReviewUpdateRes> updateReview(@RequestPart ReviewUpdateReq reviewUpdateReq,
        @RequestPart MultipartFile multipartFile)
        throws IOException {
        return RestResponse.success(reviewService.updateReview(reviewUpdateReq, multipartFile));
    }

    @DeleteMapping
    public RestResponse<ReviewDeleteRes> deleteReview(@RequestBody ReviewDeleteReq req) {
        return RestResponse.success(reviewService.deleteReview(req));
    }

    @GetMapping
    public RestResponse<List<ReviewGetResShop>> getReviews(@RequestBody ReviewGetReqShop req) {
        return RestResponse.success(reviewService.findShopReview(req));
    }

    @GetMapping("/users")
    public RestResponse<List<ReviewGetResUser>> getReviews(@RequestBody ReviewGetReqUser req) {
        return RestResponse.success(reviewService.findUserReview(req));
    }

}

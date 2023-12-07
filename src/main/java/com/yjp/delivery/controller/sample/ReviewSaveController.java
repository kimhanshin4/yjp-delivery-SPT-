package com.yjp.delivery.controller.sample;

import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.sample.dto.request.sample.ReviewSaveReq;
import com.yjp.delivery.controller.sample.dto.response.ReviewSaveRes;
import com.yjp.delivery.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewSaveController {

  private final ReviewService reviewService;

  @PostMapping("")
  public RestResponse<ReviewSaveRes> saveReview(@RequestBody ReviewSaveReq req) {
    return RestResponse.success(reviewService.saveReview(req));
  }
}

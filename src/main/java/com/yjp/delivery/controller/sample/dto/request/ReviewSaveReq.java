package com.yjp.delivery.controller.sample.dto.request.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveReq {

  private Long shopId;
  private String content;
  private String imageUrl;
}

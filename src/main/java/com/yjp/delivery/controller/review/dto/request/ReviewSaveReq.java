package com.yjp.delivery.controller.review.dto.request;

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
    private String username;
    private String content;

}

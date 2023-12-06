package com.yjp.delivery.controller.sample.dto.request.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleSaveReq {

    private String name;
    private String text;
}

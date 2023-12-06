package com.yjp.delivery.controller.sample.dto.response.sample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SampleSaveRes {

    private String name;
    private String text;
}

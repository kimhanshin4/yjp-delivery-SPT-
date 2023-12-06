package com.yjp.delivery.controller.sample;

import static com.yjp.delivery.common.meta.ResultCode.SYSTEM_ERROR;

import com.yjp.delivery.common.exception.GlobalException;
import com.yjp.delivery.common.response.RestResponse;
import com.yjp.delivery.controller.sample.dto.request.sample.SampleSaveReq;
import com.yjp.delivery.controller.sample.dto.response.sample.SampleGetRes;
import com.yjp.delivery.controller.sample.dto.response.sample.SampleSaveRes;
import com.yjp.delivery.sevice.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PostMapping
    public RestResponse<SampleSaveRes> saveSample(@RequestBody SampleSaveReq sampleSaveReq) {
        return RestResponse.success(sampleService.saveSample(sampleSaveReq));
    }

    @GetMapping("/{sampleId}")
    public RestResponse<SampleGetRes> getSample(@PathVariable Long sampleId) {
        return RestResponse.success(sampleService.getSample(sampleId));
    }

    @GetMapping("/error")
    public RestResponse<SampleGetRes> getError() {
        throw new GlobalException(SYSTEM_ERROR);
    }
}

package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.SampleValidator;
import com.yjp.delivery.controller.sample.dto.request.SampleSaveReq;
import com.yjp.delivery.controller.sample.dto.response.SampleGetRes;
import com.yjp.delivery.controller.sample.dto.response.SampleSaveRes;
import com.yjp.delivery.store.entity.SampleEntity;
import com.yjp.delivery.store.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleGetRes getSample(Long sampleId) {
        SampleEntity sampleEntity = sampleRepository.findBySampleId(sampleId);
        SampleValidator.validate(sampleEntity);
        return SampleServiceMapper.INSTANCE.toSampleGetRes(sampleEntity);
    }

    public SampleSaveRes saveSample(SampleSaveReq sampleSaveReq) {
        return SampleServiceMapper.INSTANCE.toSampleSaveRes(
            sampleRepository.save(SampleEntity.builder()
                .name(sampleSaveReq.getName())
                .text(sampleSaveReq.getText())
                .build()));
    }

    @Mapper
    public interface SampleServiceMapper {

        SampleServiceMapper INSTANCE = Mappers.getMapper(SampleServiceMapper.class);

        SampleGetRes toSampleGetRes(SampleEntity sampleEntity);

        SampleSaveRes toSampleSaveRes(SampleEntity sampleEntity);
    }
}

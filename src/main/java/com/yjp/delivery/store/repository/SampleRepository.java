package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
    SampleEntity findBySampleId(Long sampleId);
}

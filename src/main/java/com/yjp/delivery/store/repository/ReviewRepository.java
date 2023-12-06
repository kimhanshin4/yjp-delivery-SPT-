package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    ReviewEntity findByReviewId(Long reviewId);


}

package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.ReviewEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    ReviewEntity findByReviewId(Long reviewId);

    ReviewEntity findByReviewIdAndUserEntityUsername(Long reviewId, String username);

    List<ReviewEntity> findByShopEntityShopId(Long shopId);

    List<ReviewEntity> findByUserEntityUsername(String username);

}

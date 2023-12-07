package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByUserId(Long userId);
}

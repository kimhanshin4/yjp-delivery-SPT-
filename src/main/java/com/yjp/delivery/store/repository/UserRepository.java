package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}

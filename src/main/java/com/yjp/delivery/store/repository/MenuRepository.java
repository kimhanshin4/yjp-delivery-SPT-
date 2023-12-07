package com.yjp.delivery.store.repository;

import com.yjp.delivery.store.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    MenuEntity findByMenuId(Long menuId);
}

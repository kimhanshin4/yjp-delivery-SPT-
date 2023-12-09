package com.yjp.delivery.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yjp.delivery.store.entity.MenuEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;
    private MenuEntity saveMenu;

    @BeforeEach
    void setUp() {
        Long menuId = 1L;
        saveMenu = menuRepository.save(MenuEntity.builder()
            .menuId(menuId)
            .build());
    }

    @Test
    @DisplayName("menuId로 메뉴 정보 조회 테스트")
    void menuId_메뉴_정보_조회() {
        // given
        Long menuId = saveMenu.getMenuId();

        // when
        MenuEntity menu = menuRepository.findByMenuId(menuId);

        // then
        assertThat(menu).isEqualTo(saveMenu);
    }
}

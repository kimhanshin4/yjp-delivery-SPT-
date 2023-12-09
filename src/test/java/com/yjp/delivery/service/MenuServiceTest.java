package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.menu.dto.response.MenuGetRes;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 단건 조회 테스트")
    void 메뉴_단건_조회() {
        // given
        Long menuId = 1L;
        String menuName = "menu";
        MenuEntity menu = MenuEntity.builder().menuId(menuId).menuName(menuName).build();
        when(menuRepository.findByMenuId(any())).thenReturn(menu);

        // when
        MenuGetRes menuGetRes = menuService.getMenu(menuId);

        // then
        assertThat(menuGetRes.getMenuName()).isEqualTo(menuName);
        verify(menuRepository).findByMenuId(any());
    }
}

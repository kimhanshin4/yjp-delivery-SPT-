package com.yjp.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.service.provider.S3Provider;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AdminMenuServiceTest {

    @InjectMocks
    private AdminMenuService adminMenuService;

    @Mock
    private ShopRepository shopRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private S3Provider s3Provider;

    @Test
    @DisplayName("메뉴 저장 테스트")
    void 메뉴_저장() throws IOException {
        // given
        String menuName = "menu";
        String url = "url";
        ShopEntity shop = ShopEntity.builder().build();
        MenuEntity menu = MenuEntity.builder().menuName(menuName).shopEntity(shop).build();
        AddMenuReq addMenuReq = AddMenuReq.builder().menuName(menuName).build();
        MultipartFile multipartFile = null;
        when(shopRepository.findByShopId(any())).thenReturn(shop);
        when(s3Provider.saveFile(any(), any())).thenReturn(url);
        when(menuRepository.save(any())).thenReturn(menu);

        // when
        AddMenuRes addMenuRes = adminMenuService.addMenu(multipartFile, addMenuReq);

        // then
        assertThat(addMenuRes.getMenuName()).isEqualTo(menuName);
        verify(shopRepository).findByShopId(any());
        verify(s3Provider).saveFile(any(), any());
        verify(menuRepository).save(any());
    }
}

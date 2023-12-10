package com.yjp.delivery.controller.menu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.menu.dto.response.MenuGetRes;
import com.yjp.delivery.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {MenuController.class})
class MenuControllerTest extends BaseMvcTest {

    @MockBean
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 단건 조회 테스트")
    void 메뉴_단건_조회() throws Exception {
        Long menuId = 1L;
        String menuName = "menu";
        MenuGetRes result = MenuGetRes.builder().menuName(menuName).build();
        when(menuService.getMenu(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/menus/" + menuId))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

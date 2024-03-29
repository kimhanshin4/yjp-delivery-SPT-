package com.yjp.delivery.controller.admin.menu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.DeleteMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.UpdateMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.DeleteMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.UpdateMenuRes;
import com.yjp.delivery.service.AdminMenuService;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(controllers = {AdminMenuController.class})
class AdminMenuControllerTest extends BaseMvcTest {

    @MockBean
    private AdminMenuService adminMenuService;

    @Test
    @DisplayName("메뉴 저장 테스트")
    void 메뉴_저장() throws Exception {
        Long menuId = 1L;
        String menuName = "menu";
        int price = 10;
        Long shopId = 1L;
        AddMenuReq addMenuReq = AddMenuReq.builder()
            .menuName(menuName).price(price).shopId(shopId).build();
        String imageUrl = "images/loopy-goonchim.png";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file = new MockMultipartFile(
            "loopy-goonchim",
            fileResource.getFilename(),
            "image/png",
            fileResource.getInputStream()
        );
        MockMultipartFile multipartFile = new MockMultipartFile(
            "multipartFile",
            "orig",
            "multipart/form-data",
            file.getBytes());
        String json = objectMapper.writeValueAsString(addMenuReq);
        MockMultipartFile req = new MockMultipartFile(
            "addMenuReq",
            "json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8));

        AddMenuRes addMenuRes = AddMenuRes.builder()
            .menuId(menuId)
            .imageUrl(imageUrl)
            .menuName(menuName)
            .price(price)
            .shopId(shopId)
            .build();

        when(adminMenuService.addMenu(any(), any())).thenReturn(addMenuRes);
        this.mockMvc
            .perform(
                multipart("/v1/admin/menu")
                    .file(multipartFile)
                    .file(req))
            .andDo(print())
            .andExpect(status().isOk());
    }


    @Test
    @DisplayName("메뉴 수정 테스트")
    void 메뉴_수정() throws Exception {
        Long menuId = 1L;
        String menuName = "menu";
        int price = 10;
        Long shopId = 1L;
        UpdateMenuReq updateMenuReq = UpdateMenuReq.builder()
            .menuId(menuId).menuName(menuName).price(price).shopId(shopId).build();
        String imageUrl = "images/loopy-goonchim.png";
        Resource fileResource = new ClassPathResource(imageUrl);
        MockMultipartFile file = new MockMultipartFile(
            "loopy-goonchim",
            fileResource.getFilename(),
            "image/png",
            fileResource.getInputStream()
        );
        MockMultipartFile multipartFile = new MockMultipartFile(
            "multipartFile",
            "orig",
            "multipart/form-data",
            file.getBytes());
        String json = objectMapper.writeValueAsString(updateMenuReq);
        MockMultipartFile req = new MockMultipartFile(
            "updateMenuReq",
            "json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8));

        UpdateMenuRes updateMenuRes = UpdateMenuRes.builder()
            .menuId(menuId)
            .imageUrl(imageUrl)
            .menuName(menuName)
            .price(price)
            .shopId(shopId)
            .build();

        when(adminMenuService.updateMenu(any(), any())).thenReturn(updateMenuRes);
        this.mockMvc
            .perform(
                multipart(HttpMethod.PATCH, "/v1/admin/menu")
                    .file(multipartFile)
                    .file(req))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("메뉴 삭제 테스트")
    void 메뉴_삭제() throws Exception {
        Long menuId = 1L;
        Long shopId = 1L;
        DeleteMenuReq deleteMenuReq = DeleteMenuReq.builder().menuId(menuId).shopId(shopId).build();
        DeleteMenuRes result = new DeleteMenuRes();

        when(adminMenuService.deleteMenu(any())).thenReturn(result);
        this.mockMvc
            .perform(
                delete("/v1/admin/menu")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(deleteMenuReq)))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

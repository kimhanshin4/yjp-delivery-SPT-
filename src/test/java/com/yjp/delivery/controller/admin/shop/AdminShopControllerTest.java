package com.yjp.delivery.controller.admin.shop;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.admin.shop.dto.request.AddShopReq;
import com.yjp.delivery.controller.admin.shop.dto.response.AddShopRes;
import com.yjp.delivery.service.AdminShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {AdminShopController.class})
class AdminShopControllerTest extends BaseMvcTest {

    @MockBean
    private AdminShopService adminShopService;

    @Test
    @DisplayName("가게 저장 테스트")
    void 가게_저장() throws Exception {
        String shopName = "shop";
        AddShopReq addShopReq = AddShopReq.builder().shopName(shopName).build();
        AddShopRes result = AddShopRes.builder().shopName(shopName).build();
        when(adminShopService.addShop(any())).thenReturn(result);
        this.mockMvc
            .perform(
                post("/v1/admin/shop")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(addShopReq)))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

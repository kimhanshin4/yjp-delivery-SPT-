package com.yjp.delivery.controller.shop;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.shop.dto.response.ShopGetAllRes;
import com.yjp.delivery.controller.shop.dto.response.ShopGetRes;
import com.yjp.delivery.service.ShopLikeService;
import com.yjp.delivery.service.ShopService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {ShopController.class})
class ShopControllerTest extends BaseMvcTest {

    @MockBean
    private ShopService shopService;
    @MockBean
    private ShopLikeService shopLikeService;

    @Test
    @DisplayName("전체 가게 조회 테스트")
    void 전체_가게_조회() throws Exception {
        Long shopId = 1L;
        List<ShopGetRes> shopGetReses = List.of(ShopGetRes.builder().shopId(shopId).build());
        int total = 0;
        ShopGetAllRes result = ShopGetAllRes.builder()
            .shopGetReses(shopGetReses)
            .total(total)
            .build();
        when(shopService.getAllShops()).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/shop"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("가게 단건 조회 테스트")
    void 가게_단건_조회() throws Exception {
        Long shopId = 1L;
        ShopGetRes result = ShopGetRes.builder().shopId(shopId).build();
        when(shopService.getShop(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/shop/" + shopId))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

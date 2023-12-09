package com.yjp.delivery.controller.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.order.dto.request.OrderGetShopReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReq;
import com.yjp.delivery.controller.order.dto.request.OrderSaveReqList;
import com.yjp.delivery.controller.order.dto.response.OrderGetRes;
import com.yjp.delivery.controller.order.dto.response.OrderGetResWrapper;
import com.yjp.delivery.controller.order.dto.response.OrderGetShopRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveRes;
import com.yjp.delivery.controller.order.dto.response.OrderSaveResList;
import com.yjp.delivery.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {OrderController.class})
class OrderControllerTest extends BaseMvcTest {

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("주문 저장 테스트")
    void 주문_저장() throws Exception {
        Long orderId = 1L;
        String username = "ysys";
        int totalPrice = 0;
        Long menuId = 1L;
        String menuName = "menu";
        OrderSaveReq orderSaveReq = OrderSaveReq.builder().menuId(menuId).build();
        OrderSaveRes orderSaveRes = OrderSaveRes.builder().menuName(menuName).build();
        List<OrderSaveRes> orderSaveReses = List.of(orderSaveRes);
        OrderSaveReqList orderSaveReqList = OrderSaveReqList.builder()
            .orderSaveReqs(List.of(orderSaveReq))
            .build();
        OrderSaveResList result = OrderSaveResList.builder()
            .orderId(orderId)
            .username(username)
            .totalPrice(totalPrice)
            .orderSaveReses(orderSaveReses)
            .build();
        when(orderService.saveOrders(any())).thenReturn(result);
        this.mockMvc
            .perform(
                post("/v1/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderSaveReqList))
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shop으로 주문 조회 테스트")
    void shop_주문_조회() throws Exception {
        Long shopId = 1L;
        String menuName = "menu";
        String shopName = "shop";
        OrderGetShopReq orderGetShopReq = OrderGetShopReq.builder().shopId(shopId).build();
        OrderGetRes orderGetRes = OrderGetRes.builder().menuName(menuName).build();
        OrderGetResWrapper orderGetResWrapper = OrderGetResWrapper.builder()
            .shopName(shopName).orderGetReses(List.of(orderGetRes)).build();
        OrderGetShopRes result = OrderGetShopRes.builder()
            .orderGetResWrappers(List.of(orderGetResWrapper)).build();
        when(orderService.getAllOrderByShop(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/order/shops")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderGetShopReq)))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

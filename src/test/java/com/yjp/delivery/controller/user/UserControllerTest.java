package com.yjp.delivery.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.user.dto.request.ProfileGetReq;
import com.yjp.delivery.controller.user.dto.response.ProfileGetRes;
import com.yjp.delivery.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest extends BaseMvcTest {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("프로필 조회 테스트")
    void 프로필_조회() throws Exception {
        Long userId = 1L;
        String email = "emoney96@naver.com";
        String username = "ysys";
        String introduction = "intro";
        String profileImageUrl = "url";
        ProfileGetReq request = ProfileGetReq.builder().userId(userId).build();
        ProfileGetRes result = ProfileGetRes.builder()
            .email(email)
            .username(username)
            .introduction(introduction)
            .profileImageUrl(profileImageUrl)
            .build();
        when(userService.getUserProfile(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print());
    }
}

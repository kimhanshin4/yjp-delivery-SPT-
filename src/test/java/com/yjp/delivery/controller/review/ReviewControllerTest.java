package com.yjp.delivery.controller.review;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.review.dto.request.ReviewDeleteReq;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqShop;
import com.yjp.delivery.controller.review.dto.request.ReviewGetReqUser;
import com.yjp.delivery.controller.review.dto.request.ReviewSaveReq;
import com.yjp.delivery.controller.review.dto.request.ReviewUpdateReq;
import com.yjp.delivery.controller.review.dto.response.ReviewDeleteRes;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResShop;
import com.yjp.delivery.controller.review.dto.response.ReviewGetResUser;
import com.yjp.delivery.controller.review.dto.response.ReviewSaveRes;
import com.yjp.delivery.controller.review.dto.response.ReviewUpdateRes;
import com.yjp.delivery.service.ReviewService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@WebMvcTest(controllers = {ReviewController.class})
class ReviewControllerTest extends BaseMvcTest {

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 저장 테스트")
    void 리뷰_저장() throws Exception {
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        ReviewSaveReq reviewSaveReq = ReviewSaveReq.builder()
            .shopId(shopId).username(username).content(content).build();
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
        String json = objectMapper.writeValueAsString(reviewSaveReq);
        MockMultipartFile req = new MockMultipartFile(
            "req",
            "json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8));

        ReviewSaveRes reviewSaveRes = ReviewSaveRes.builder()
            .username(username).content(content).build();

        when(reviewService.saveReview(any(), any())).thenReturn(reviewSaveRes);
        this.mockMvc
            .perform(
                multipart("/v1/review")
                    .file(multipartFile)
                    .file(req)
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void 리뷰_수정() throws Exception {
        Long reviewId = 1L;
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        ReviewUpdateReq reviewUpdateReq = ReviewUpdateReq.builder()
            .reviewId(reviewId).shopId(shopId).username(username).content(content).build();
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
        String json = objectMapper.writeValueAsString(reviewUpdateReq);
        MockMultipartFile req = new MockMultipartFile(
            "req",
            "json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8));

        ReviewUpdateRes reviewUpdateRes = ReviewUpdateRes.builder()
            .username(username).content(content).build();

        when(reviewService.updateReview(any(), any())).thenReturn(reviewUpdateRes);
        this.mockMvc
            .perform(
                multipart(HttpMethod.PATCH, "/v1/review")
                    .file(multipartFile)
                    .file(req)
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void 리뷰_삭제() throws Exception {
        Long reviewId = 1L;
        String username = "ysys";
        ReviewDeleteReq req = ReviewDeleteReq.builder()
            .reviewId(reviewId).username(username).build();
        ReviewDeleteRes result = new ReviewDeleteRes();
        when(reviewService.deleteReview(any())).thenReturn(result);
        this.mockMvc
            .perform(
                delete("/v1/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("shop으로 리뷰 조회 테스트")
    void shop_리뷰_조회() throws Exception {
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        String imageUrl = "url";
        ReviewGetReqShop req = ReviewGetReqShop.builder().shopId(shopId).build();
        ReviewGetResShop res = ReviewGetResShop.builder()
            .shopId(shopId).username(username).content(content).imageUrl(imageUrl).build();
        List<ReviewGetResShop> result = List.of(res);
        when(reviewService.findShopReview(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/review/shops")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("user로 리뷰 조회 테스트")
    void user_리뷰_조회() throws Exception {
        Long shopId = 1L;
        String username = "ysys";
        String content = "content";
        String imageUrl = "url";
        ReviewGetReqUser req = ReviewGetReqUser.builder().username(username).build();
        ReviewGetResUser res = ReviewGetResUser.builder()
            .shopId(shopId).username(username).content(content).imageUrl(imageUrl).build();
        List<ReviewGetResUser> result = List.of(res);
        when(reviewService.findUserReview(any())).thenReturn(result);
        this.mockMvc
            .perform(
                get("/v1/review/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .principal(userMockPrincipal))
            .andDo(print())
            .andExpect(status().isOk());
    }
}

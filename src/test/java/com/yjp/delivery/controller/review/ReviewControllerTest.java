package com.yjp.delivery.controller.review;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yjp.delivery.controller.BaseMvcTest;
import com.yjp.delivery.controller.review.dto.request.ReviewSaveReq;
import com.yjp.delivery.controller.review.dto.response.ReviewSaveRes;
import com.yjp.delivery.service.ReviewService;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
}

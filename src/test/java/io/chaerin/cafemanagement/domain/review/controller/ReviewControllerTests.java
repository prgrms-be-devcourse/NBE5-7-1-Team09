package io.chaerin.cafemanagement.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.repository.ReviewRepository;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(ReviewController.class)
class ReviewControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Test
    @DisplayName("리뷰 작성 성공 테스트")
    void create_review_test() throws Exception {
        // given
        Long productId = 1L;
        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto();
        requestDto.setContent("좋은커피");

        // doNothing은 void 메소드에만 사용 가능
//        doNothing().when(reviewService).save(requestDto, productId);

        ReviewResponseDto responseDto = new ReviewResponseDto(1L, requestDto.getContent(), productId);

        doReturn(responseDto).when(reviewService).save(requestDto, productId);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/{productId}/reviews", productId)
                        .param("content", "좋은커피"))
                // 302 Found 리디렉션 상태 응답 코드는 요청한 리소스가 Location 헤더에 지정된 URL로 일시적으로 이동되었음을 나타냄
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/" + productId));

    }
    @Test
    @DisplayName("리뷰 작성 실패 테스트 - content가 비어있음")
    void create_review_ng_test() throws Exception {
        // given
        Long productId = 1L;
        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto();
        requestDto.setContent("");


        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/products/{productId}/reviews", productId)
                        .param("content", ""))
                .andExpect(model().attributeHasFieldErrors("reviewCreateRequestDto", "content"))
                .andExpect(view().name("review/form")); // 다시 폼 보여주는지 확인

    }
}
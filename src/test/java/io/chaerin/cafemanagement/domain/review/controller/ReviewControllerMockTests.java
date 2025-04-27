package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.entity.Review;
import io.chaerin.cafemanagement.domain.review.repository.ReviewRepository;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@WebMvcTest(ReviewController.class)
class ReviewControllerMockTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;


    @Test
    @DisplayName("리뷰 작성 성공 테스트")
    void create_review_test() throws Exception {
        // given
        Long productId = 1L;
        Long userId = 1L;
        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto();
        requestDto.setContent("좋은커피");

        // doNothing은 void 메소드에만 사용 가능
//        doNothing().when(reviewService).save(requestDto, productId);

        ReviewResponseDto responseDto = new ReviewResponseDto(1L, requestDto.getContent(),"1234", LocalDateTime.now(), productId, userId);

        doReturn(responseDto).when(reviewService).save(requestDto, productId, userId);

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
                .andExpect(view().name("/review/form")); // 다시 폼 보여주는지 확인

    }

    @Test
    @DisplayName("리뷰 목록 조회 테스트")
    void read_reviews_test() throws Exception {
        // given
        Long productId = 1L;
        Long userId = 1L;

        List<ReviewResponseDto> reviewList = List.of(
                new ReviewResponseDto(1L, "짱맛있다", "1234", LocalDateTime.now(), productId, userId),
                new ReviewResponseDto(2L, "짱맛없다", "1234",LocalDateTime.now(), productId,userId)
        );

        // doReturn().when(): 이 값을 리턴하라 (Mockito)
        // given().willReturn(): 이 값이 주어졌을 때 이런 결과 (BDDMockito)
        //   -> 가독성 측면에서 BDDMockito 사용이 더 나음.......

        given(reviewService.getReviewList(productId)).willReturn(reviewList);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{productId}/reviews", productId))
                .andExpect(status().isOk())
                .andExpect(view().name("product/reviewList"))
                // 반환된 리뷰 리스트가 모델에 reviews로 들어감
                .andExpect(model().attribute("reviews", reviewList));
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void delete_reviews_test() throws Exception {

    }


}
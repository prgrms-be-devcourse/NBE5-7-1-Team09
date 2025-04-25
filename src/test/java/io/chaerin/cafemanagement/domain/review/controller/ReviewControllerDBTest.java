package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.entity.Review;
import io.chaerin.cafemanagement.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReviewControllerDBTest {


    @Autowired
    private MockMvc mockMvcc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 작성 실제 DB 테스트")
    void create_review_real_db_test() throws Exception {
        // given
        Long productId = 1L;
        String content = "진짜로 저장되는지 확인";

        // when
        mockMvcc.perform(MockMvcRequestBuilders.post("/products/{productId}/reviews", productId)
                        .param("content", content))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/" + productId));

        // then
        List<Review> reviews = reviewRepository.findByProduct_ProductIdOrderByCreatedAt(productId);

        assertThat(reviews)
                .isNotEmpty()
                .anyMatch(r -> r.getContent().equals(content));
    }



}
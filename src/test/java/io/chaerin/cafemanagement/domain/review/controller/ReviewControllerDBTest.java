package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReviewControllerDBTest {


    @Autowired
    private MockMvc mockMvcc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

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


    @Test
    @DisplayName("리뷰 목록 조회 실제 DB 테스트")
    void get_review_list_real_db_test() throws Exception {
        // given
        Product product = productRepository.save(Product.builder()
                .name("테스트커피")
                .price(5000)
                .imageUrl("image.jpg")
                .build());


        reviewRepository.save(Review.builder()
                .product(product)
                .content("짱맛있다")
                .build());

        reviewRepository.save(Review.builder()
                .product(product)
                .content("짱맛없다")
                .build());

        Long productId = product.getProductId();

        // when & then
        mockMvcc.perform(MockMvcRequestBuilders.get("/products/{productId}/reviews", productId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(model().attribute("reviews", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(view().name("product/reviewList"));
    }



}
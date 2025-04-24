package io.chaerin.cafemanagement.domain.review.dto;

import io.chaerin.cafemanagement.domain.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private String content;
    private Long productId;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.content = review.getContent();
        this.productId = review.getProduct().getProductId();
    }
}

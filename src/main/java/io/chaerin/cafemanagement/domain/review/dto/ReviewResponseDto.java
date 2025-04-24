package io.chaerin.cafemanagement.domain.review.dto;

import io.chaerin.cafemanagement.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String content;
    private Long productId;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .productId(review.getProduct().getProductId())
                .build();
    }
}

package io.chaerin.cafemanagement.domain.review.dto;

import io.chaerin.cafemanagement.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {
    private Long reviewId;
    private String content;
    private LocalDateTime createdAt;
    private Long productId;
    private Long userId;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .productId(review.getProduct().getProductId())
                .userId(review.getUser().getUserId())
                .build();
    }

    public ReviewResponseDto(Long reviewId, String content, LocalDateTime createdAt, Long productId, Long userId) {
        this.reviewId = reviewId;
        this.content = content;
        this.createdAt = createdAt;
        this.productId = productId;
        this.userId = userId;
    }
}

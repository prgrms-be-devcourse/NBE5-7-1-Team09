package io.chaerin.cafemanagement.domain.review.entity;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private LocalDateTime createdAt;

    private String content;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public static Review create(ReviewCreateRequestDto requestDto, Product product, User user) {
        return Review.builder()
                .product(product)
                .user(user)
                .content(requestDto.getContent())
                .build();

    }
}

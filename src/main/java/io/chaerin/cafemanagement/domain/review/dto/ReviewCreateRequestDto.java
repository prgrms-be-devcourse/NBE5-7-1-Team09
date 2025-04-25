package io.chaerin.cafemanagement.domain.review.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequestDto {
    // 유효성 어노테이션 추가
    @NotBlank(message = "리뷰 내용은 필수입니다.")
    private String content;
}

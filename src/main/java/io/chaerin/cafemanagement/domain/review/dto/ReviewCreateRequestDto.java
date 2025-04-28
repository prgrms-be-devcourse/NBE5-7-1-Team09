package io.chaerin.cafemanagement.domain.review.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequestDto {
    @NotBlank(message = "리뷰 내용은 필수입니다.")
    private String content;
}

package io.chaerin.cafemanagement.domain.question.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDto {

    private Long questionId;
    private String title;
    private String content;

}

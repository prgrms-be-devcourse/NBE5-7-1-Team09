package io.chaerin.cafemanagement.domain.question.dto;

import io.chaerin.cafemanagement.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class QuestionResponseDto {

    private final Long questionId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public static QuestionResponseDto fromEntity(Question question) {
        return QuestionResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();
    }

}

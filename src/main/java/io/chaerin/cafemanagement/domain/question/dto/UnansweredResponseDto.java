package io.chaerin.cafemanagement.domain.question.dto;

import io.chaerin.cafemanagement.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UnansweredResponseDto {

    private Long questionId;
    private String title;
    private String content;
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    public static UnansweredResponseDto fromEntity(Question question) {
        return UnansweredResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .answer(question.getAnswer())
                .createdAt(question.getCreatedAt())
                .answeredAt(question.getAnswerCreatedAt())
                .build();
    }

}

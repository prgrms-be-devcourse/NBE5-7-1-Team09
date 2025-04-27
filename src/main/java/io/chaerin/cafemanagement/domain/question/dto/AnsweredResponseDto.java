package io.chaerin.cafemanagement.domain.question.dto;

import io.chaerin.cafemanagement.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class AnsweredResponseDto {

    private final Long questionId;
    private final String title;
    private final String content;
    private final String answer;
    private final LocalDateTime createdAt;
    private final LocalDateTime answeredAt;

    public static AnsweredResponseDto fromEntity(Question question) {
        return AnsweredResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .answer(question.getAnswer())
                .createdAt(question.getCreatedAt())
                .answeredAt(question.getAnswerCreatedAt())
                .build();
    }

}

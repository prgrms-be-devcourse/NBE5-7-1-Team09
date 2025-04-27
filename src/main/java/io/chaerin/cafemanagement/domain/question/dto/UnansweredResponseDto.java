package io.chaerin.cafemanagement.domain.question.dto;

import io.chaerin.cafemanagement.domain.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class UnansweredResponseDto {

    private final Long questionId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public static UnansweredResponseDto fromEntity(Question question) {
        return UnansweredResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();
    }

}

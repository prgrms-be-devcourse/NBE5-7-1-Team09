package io.chaerin.cafemanagement.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDto {

    @NotBlank(message = "답변 내용을 입력해주세요.")
    private String answer;

}

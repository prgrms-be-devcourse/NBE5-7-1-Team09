package io.chaerin.cafemanagement.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

    @NotBlank(message = "제목과 내용을 입력해주세요.")
    private String title;

    @NotBlank(message = "제목과 내용을 입력해주세요.")
    private String content;


}

package io.chaerin.cafemanagement.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {       // 문의사항 작성 요청용

    @NotBlank(message = "제목과 내용을 입력해주세요.")
    private String title;

    @NotBlank(message = "제목과 내용을 입력해주세요.")
    private String content;


}

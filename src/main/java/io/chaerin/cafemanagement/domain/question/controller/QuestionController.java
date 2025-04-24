package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("order")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 문의사항 작성
    @PostMapping("/{orderId}/question")
    public String saveQuestion(@PathVariable Long orderId, @ModelAttribute QuestionRequestDto requestDto) {

        Long id = questionService.saveQuestion(orderId, requestDto);

        return "redirect:/question/" + id + "/question";

    }

    // todo : 로그인으로 바뀌면 바꿔야함
    // 문의사항 삭제
    @DeleteMapping("/{questionId}/question")
    public String deleteQuestion(@PathVariable Long questionId) {

        String email = questionService.deleteQuestion(questionId);

        // 주문 목록 조회로 이동
        return "redirect:/order?email=" + email;
    }

}

package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 문의사항 작성
    @PostMapping("/{orderId}/question")
    public String saveQuestion(@PathVariable Long orderId, QuestionRequestDto requestDto) {

        Long id = questionService.saveQuestion(orderId, requestDto);

        return "redirect:/question/" + id + "/question";

    }

}

package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.question.dto.AnswerRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.UnansweredResponseDto;
import io.chaerin.cafemanagement.domain.question.dto.AnsweredResponseDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/question")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;

    @PostMapping("/{questionId}")
    public String saveAnswer(@PathVariable Long questionId, @ModelAttribute AnswerRequestDto requestDto) {
        questionService.saveAnswer(questionId, requestDto);

        return "redirect:/admin/question/unanswered";
    }

    @PutMapping("/{questionId}")
    public String updateAnswer(@PathVariable Long questionId, @ModelAttribute AnswerRequestDto requestDto) {
        questionService.saveAnswer(questionId, requestDto);

        return "redirect:/admin/question/answered";
    }

    @GetMapping("/unanswered")
    public String showUnansweredPage(Model model) {

        List<UnansweredResponseDto> responseDto = questionService.findAllUnansweredQuestion();
        model.addAttribute("unansweredResponseDto", responseDto);

        model.addAttribute("answerRequestDto", new AnswerRequestDto());

        return "/question/unanswered";
    }

    @GetMapping("/answered")
    public String showAnsweredPage(Model model) {

        List<AnsweredResponseDto> responseDto = questionService.findAllAnsweredQuestion();
        model.addAttribute("answeredResponseDto", responseDto);

        model.addAttribute("answerRequestDto", new AnswerRequestDto());

        return "/question/answered";
    }


}

package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.order.service.OrderService;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final OrderService orderService;


    @PostMapping("/{orderId}/question")
    public String saveQuestion(@PathVariable Long orderId, @Valid @ModelAttribute QuestionRequestDto requestDto) {

        Long id = questionService.saveQuestion(orderId, requestDto);

        return "redirect:/order/" + id + "/question";

    }

    // todo : 로그인으로 바뀌면 바꿔야함
    @DeleteMapping("/{questionId}/question")
    public String deleteQuestion(@PathVariable Long questionId) {

        String email = questionService.deleteQuestion(questionId);

        return "redirect:/orders?email=" + email;
    }

    @GetMapping("/{orderId}/question")
    public String showQuestion(@PathVariable Long orderId, Model model) {

        model.addAttribute("order", orderService.getOrderById(orderId));

        if (questionService.existsQuestion(orderId)) {

            QuestionResponseDto responseDto = questionService.findQuestionByOrderId(orderId);
            model.addAttribute("responseDto", responseDto);

            return "/order/question/detail";

        } else {
            model.addAttribute("requestDto", new QuestionRequestDto());
            return "/order/question/form";
        }

    }

}

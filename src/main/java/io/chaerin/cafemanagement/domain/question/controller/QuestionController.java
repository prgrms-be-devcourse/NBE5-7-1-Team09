package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.order.service.OrderService;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final OrderService orderService;


    @PostMapping("/{orderId}/question")
    public String saveQuestion(@PathVariable Long orderId, @Valid @ModelAttribute QuestionRequestDto requestDto, HttpSession session) {
        questionService.checkLogin(session);
        Long id = questionService.saveQuestion(orderId, requestDto);

        return "redirect:/order/" + id + "/question";

    }

    @DeleteMapping("/{questionId}/question")
    public String deleteQuestion(@PathVariable Long questionId, HttpSession session) {
        questionService.checkLogin(session);
        questionService.deleteQuestion(questionId);

        return "redirect:/orders";
    }

    @GetMapping("/{orderId}/question")
    public String showQuestion(@PathVariable Long orderId, Model model, HttpSession session) {
        questionService.checkLogin(session);

        model.addAttribute("order", orderService.getOrderById(orderId));

        if (questionService.existsQuestion(orderId)) {

            QuestionResponseDto responseDto = questionService.findQuestionByOrderId(orderId);
            model.addAttribute("questionResponseDto", responseDto);

            return "/question/detail";

        } else {
            model.addAttribute("questionRequestDto", new QuestionRequestDto());
            return "/question/form";
        }

    }

}

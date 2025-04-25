package io.chaerin.cafemanagement.domain.question.controller;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
import io.chaerin.cafemanagement.domain.question.service.QuestionService;
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


    // 문의사항 작성
    @PostMapping("/{orderId}/question")
    public String saveQuestion(@PathVariable Long orderId, @ModelAttribute QuestionRequestDto requestDto) {

        Long id = questionService.saveQuestion(orderId, requestDto);

        return "redirect:/order/" + id + "/question";

    }

    // todo : 로그인으로 바뀌면 바꿔야함
    // 문의사항 삭제
    @DeleteMapping("/{questionId}/question")
    public String deleteQuestion(@PathVariable Long questionId) {

        String email = questionService.deleteQuestion(questionId);

        // 주문 목록 조회로 이동
        return "redirect:/order?email=" + email;
    }

    // 문의사항 조회
    // 문의내역 있으면 내역 담아 뷰 반환
    // 없으면 문의 입력 뷰 반환
    @GetMapping("/{orderId}/question")
    public String showQuestion(@PathVariable Long orderId, Model model) {

        // 추가 요청 해야함
//        model.addAttribute("order", orderService.~~)


        // 문의 내역이 있나?
        if (questionService.existsQuestion(orderId)) {

            QuestionResponseDto responseDto = questionService.findQuestionByOrderId(orderId);
            model.addAttribute("responseDto", responseDto);

            return "/order/question/detail";

        } else { // 없다?
            return "/order/question/form";
        }

    }

}

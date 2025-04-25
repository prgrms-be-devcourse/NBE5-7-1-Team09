package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products/{productId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public String createReview(
            @PathVariable Long productId,
            @Valid @ModelAttribute ReviewCreateRequestDto requestDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);

            // 유효성 검증 실패 시 반환될 테스트용 페이지
            // html 추가 시, 리뷰 작성 페이지로 변경 예정
            return "review/form";
        }

        //TODO: 추후 유저 정보 받아오도록 변경 예정
        Long userId = 1L;

        reviewService.save(requestDto, productId, userId);

        return "redirect:/products/" + productId + "/reviews";
    }

    @GetMapping("/form")
    public String showReviewForm(
            @PathVariable Long productId,
            Model model
    ) {
        model.addAttribute("productId", productId);
        model.addAttribute("reviewCreateRequestDto", new ReviewCreateRequestDto());

        return "review/form";
    }


    @GetMapping
    public String getReviewList(
            @PathVariable Long productId,
            Model model
    ) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewList(productId);

        model.addAttribute("productId", productId);
        model.addAttribute("reviews", reviewList);

        return "review/list";
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(
            @PathVariable Long reviewId,
            @PathVariable Long productId
    ) throws IllegalAccessException {

        //TODO: 추후 유저 정보 받아오도록 변경 예정
        Long userId = 1L;

        reviewService.deleteReview(reviewId, userId);

        return "redirect:/products/" + productId;
    }
}

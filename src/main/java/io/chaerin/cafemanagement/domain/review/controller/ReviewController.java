package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            @ModelAttribute ReviewCreateRequestDto requestDto
    ) {

        reviewService.save(requestDto, productId);

        return "redirect:/products/" + productId;
    }

    @GetMapping
    public String getReviewList(
            @PathVariable Long productId,
            Model model
    ) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewList(productId);

        model.addAttribute("reviews", reviewList);

        return "/products/" + productId;
    }
}

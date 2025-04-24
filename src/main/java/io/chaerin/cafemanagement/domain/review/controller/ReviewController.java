package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/{productId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public String saveReview(
            @PathVariable Long productId,
            @ModelAttribute ReviewCreateRequestDto requestDto
    ) {

        reviewService.save(requestDto, productId);

        return "redirect:/products/" + productId;
    }
}

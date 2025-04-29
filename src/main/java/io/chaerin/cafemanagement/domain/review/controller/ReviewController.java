package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import io.chaerin.cafemanagement.domain.user.dto.PrincipalDetails;
import io.chaerin.cafemanagement.domain.user.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);

            return "review/form";
        }

        Long userId = principalDetails.getUserId();

        try {
            reviewService.save(requestDto, productId, userId);
        } catch (IllegalStateException e) {
            model.addAttribute("productId", productId);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("redirectUrl", "/products/" + productId + "/reviews");
            return "review/form";
        }

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
            Model model,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewList(productId);

        Long currentUserId = principalDetails.getUserId();

        model.addAttribute("productId", productId);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("reviews", reviewList);

        return "review/list";
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(
            @PathVariable Long reviewId,
            @PathVariable Long productId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) throws IllegalAccessException {

        Long userId = principalDetails.getUserId();

        reviewService.deleteReview(reviewId, userId);

        return "redirect:/products/" + productId + "/reviews";
    }
}

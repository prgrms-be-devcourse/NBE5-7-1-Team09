package io.chaerin.cafemanagement.domain.review.controller;

import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.service.ReviewService;
import io.chaerin.cafemanagement.domain.user.entity.User;
import jakarta.servlet.http.HttpSession;
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
            Model model,
            HttpSession session
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);

            return "review/form";
        }

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }
        Long userId = ((User) loginUser).getUserId();

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
            Model model,
            HttpSession session
    ) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewList(productId);

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }
        Long currentUserId = ((User) loginUser).getUserId();

        model.addAttribute("productId", productId);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("reviews", reviewList);

        return "review/list";
    }

    @DeleteMapping("/delete/{reviewId}")
    public String deleteReview(
            @PathVariable Long reviewId,
            @PathVariable Long productId,
            HttpSession session
    ) throws IllegalAccessException {

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }
        Long userId = ((User) loginUser).getUserId();

        reviewService.deleteReview(reviewId, userId);

        return "redirect:/products/" + productId + "/reviews";
    }
}

package io.chaerin.cafemanagement.domain.review.service;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.entity.Review;
import io.chaerin.cafemanagement.domain.review.repository.ReviewRepository;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto save(ReviewCreateRequestDto requestDto, Long productId, Long userId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));


        Optional<Review> reviewOptional = reviewRepository.findByUserAndProduct(user, product);
        if (reviewOptional.isPresent()) {
            throw new IllegalStateException("이미 해당 상품에 리뷰를 작성하셨습니다.");
        }



        Review saved = reviewRepository.save( Review.create(requestDto, product, user));

        return ReviewResponseDto.toDto(saved);

    }

    @Transactional
    public List<ReviewResponseDto> getReviewList(Long productId) {
        List<Review> reviewList = reviewRepository.findByProduct_ProductIdOrderByCreatedAt(productId);

        return reviewList.stream()
                .map(ReviewResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) throws IllegalAccessException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 리뷰가 없습니다."));

        if (!Objects.equals(review.getUser().getUserId(), userId)) {
            throw new IllegalAccessException("본인이 작성한 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.deleteById(reviewId);
    }
}
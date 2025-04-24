package io.chaerin.cafemanagement.domain.review.service;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.entity.ProductRepository;
import io.chaerin.cafemanagement.domain.review.dto.ReviewCreateRequestDto;
import io.chaerin.cafemanagement.domain.review.dto.ReviewResponseDto;
import io.chaerin.cafemanagement.domain.review.entity.Review;
import io.chaerin.cafemanagement.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    // 후기 작성
    @Transactional
    public ReviewResponseDto save(ReviewCreateRequestDto requestDto, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

        /*
        * 유저 중복 체크는 로그인 구현 완료되면 추가하겠습니다
        * */


        Review saved = reviewRepository.save( Review.create(requestDto, product));

        return ReviewResponseDto.toDto(saved);

    }


}

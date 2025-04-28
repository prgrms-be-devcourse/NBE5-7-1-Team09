package io.chaerin.cafemanagement.domain.review.repository;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.review.entity.Review;
import io.chaerin.cafemanagement.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct_ProductIdOrderByCreatedAt(Long productId);

    Optional<Review> findByUserAndProduct(User user, Product product);

}

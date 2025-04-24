package io.chaerin.cafemanagement.domain.question.repository;


import io.chaerin.cafemanagement.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByOrderId(Long orderId);
    boolean existsByOrderId(Long orderId);

}


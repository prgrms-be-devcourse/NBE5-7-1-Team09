package io.chaerin.cafemanagement.domain.question.repository;


import io.chaerin.cafemanagement.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByOrderId(Long orderId);
    boolean existsByOrderId(Long orderId);

    @Query("select q from Question q where q.answer is not null")
    List<Question> findAllAnsweredQuestions();

    @Query("select q from Question q where q.answer is null")
    List<Question> findAllUnansweredQuestions();

}


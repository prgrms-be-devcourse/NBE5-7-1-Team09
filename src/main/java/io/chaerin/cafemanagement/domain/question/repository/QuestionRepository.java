package io.chaerin.cafemanagement.domain.question.repository;


import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByOrder(Order order);
    boolean existsByOrder(Order order);

    @Query("select q from Question q where q.answer is not null order by q.answerCreatedAt desc")
    List<Question> findAllAnsweredQuestions();

    @Query("select q from Question q where q.answer is null")
    List<Question> findAllUnansweredQuestions();

}


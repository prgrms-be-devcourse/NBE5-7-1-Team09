package io.chaerin.cafemanagement.domain.question.service;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class QuestionService {

    private final OrderRepository orderRepository;
    private final QuestionRepository questionRepository;

    // 문의사항 작성
    @Transactional
    public Long saveQuestion(Long orderId, QuestionRequestDto requestDto) {

        // existsById 도 고려해봤는데, 큰 차이 없을 것 같아서 이렇게 했습니다
        orderRepository.findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("해당하는 주문이 없습니다."));

        Question saved = questionRepository.save(
                Question.builder()
                        .orderId(orderId)
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .build()
        );

        return saved.getOrderId();
    }


}

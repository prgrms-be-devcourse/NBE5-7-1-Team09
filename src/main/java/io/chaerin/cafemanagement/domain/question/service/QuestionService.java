package io.chaerin.cafemanagement.domain.question.service;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
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

    @Transactional
    public Long saveQuestion(Long orderId, QuestionRequestDto requestDto) {

        if(!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("해당하는 주문이 없습니다.");
        }

        if(!questionRepository.existsByOrderId(orderId)) {
            questionRepository.save(
                    Question.builder()
                            .orderId(orderId)
                            .title(requestDto.getTitle())
                            .content(requestDto.getContent())
                            .build()
            );
        }

        return orderId;
    }

    // todo : 로그인으로 바뀌면 바꿔야함
    @Transactional
    public String deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        Order order = orderRepository.findById(question.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        questionRepository.delete(question);

        return order.getEmail();
    }

    public boolean existsQuestion(Long orderId) {
        return questionRepository.existsByOrderId(orderId);
    }

    public QuestionResponseDto findQuestionByOrderId(Long orderId){
        Question question = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        return QuestionResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .build();

    }




}

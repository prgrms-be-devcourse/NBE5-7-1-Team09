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

    // todo : 로그인으로 바뀌면 바꿔야함
    // 문의사항 삭제
    @Transactional
    public String deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        // 삭제 후 조회페이지 반환을 위해.
        Order order = orderRepository.findById(question.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        questionRepository.delete(question);

        return order.getEmail();
    }

    // 문의사항 내역이 있는지?
    public boolean existsQuestion(Long orderId) {
        return questionRepository.existsById(orderId);
    }

    // 문의사항 조회
    public QuestionResponseDto findQuestionByOrderId(Long orderId){
        Question question = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        return QuestionResponseDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .build();

    }




}

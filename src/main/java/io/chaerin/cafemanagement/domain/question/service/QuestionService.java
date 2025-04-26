package io.chaerin.cafemanagement.domain.question.service;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.question.dto.AnswerRequestDto;
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
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        Order findOrder = orderRepository.findById(findQuestion.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));

        questionRepository.delete(findQuestion);

        return findOrder.getEmail();
    }

    public boolean existsQuestion(Long orderId) {
        return questionRepository.existsByOrderId(orderId);
    }

    public QuestionResponseDto findQuestionByOrderId(Long orderId){
        Question findQuestion = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        return QuestionResponseDto.fromEntity(findQuestion);
    }

    public void saveAnswer(Long questionId, AnswerRequestDto requestDto) {
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의사항이 없습니다."));
        findQuestion.writeAnswer(requestDto.getAnswer());

    }






}

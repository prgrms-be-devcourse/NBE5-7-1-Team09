package io.chaerin.cafemanagement.domain.question.service;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.question.dto.*;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void deleteQuestion(Long questionId) {
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        questionRepository.delete(findQuestion);
    }

    @Transactional(readOnly = true)
    public boolean existsQuestion(Long orderId) {
        return questionRepository.existsByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    public QuestionResponseDto findQuestionByOrderId(Long orderId){
        Question findQuestion = questionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("작성한 문의사항이 없습니다."));

        return QuestionResponseDto.fromEntity(findQuestion);
    }

    @Transactional
    public void saveAnswer(Long questionId, AnswerRequestDto requestDto) {
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의사항이 없습니다."));
        findQuestion.writeAnswer(requestDto.getAnswer());

    }

    @Transactional(readOnly = true)
    public List<AnsweredResponseDto> findAllAnsweredQuestion() {
        return questionRepository.findAllAnsweredQuestions().stream()
                .map(AnsweredResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UnansweredResponseDto> findAllUnansweredQuestion() {
        return questionRepository.findAllUnansweredQuestions().stream()
                .map(UnansweredResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void checkLogin(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }
    }




}

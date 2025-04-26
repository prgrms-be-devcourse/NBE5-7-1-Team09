package io.chaerin.cafemanagement.domain.question.service;


import io.chaerin.cafemanagement.domain.question.dto.*;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void cleanUp() {
        questionRepository.deleteAll();
    }

    @Test
    @Sql(statements = {
            "INSERT INTO orders (order_id, email, address, post_code, create_at) VALUES (101, 'test2@example.com', '','', CURRENT_TIMESTAMP);",
            "INSERT INTO product (product_id, name, price) VALUES (10, 'p1', 1000);",
            "INSERT INTO product (product_id, name, price) VALUES (11, 'p2', 1500);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1001, 10, 101, 2);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1002, 11, 101, 3);"
    })
    @DisplayName("문의 생성 테스트")
    void question_save_test() throws Exception {

        Long orderId1 = 101L;
        Long savedOrderId = questionService.saveQuestion(orderId1, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        assertThat(savedOrderId).isEqualTo(orderId1);
        assertThat(questionService.existsQuestion(savedOrderId)).isTrue();

        // 없는 id를 넣으면 실패한다.
        assertThatThrownBy(
                () -> {
                    questionService.saveQuestion(123412L, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));
                }
        ).isInstanceOf(IllegalArgumentException. class);


    }
    @Test
    @Sql(statements = {
            "INSERT INTO orders (order_id, email, address, post_code, create_at) VALUES (101, 'test2@example.com', '','', CURRENT_TIMESTAMP);",
            "INSERT INTO product (product_id, name, price) VALUES (10, 'p1', 1000);",
            "INSERT INTO product (product_id, name, price) VALUES (11, 'p2', 1500);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1001, 10, 101, 2);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1002, 11, 101, 3);"
    })
    @DisplayName("문의 삭제 테스트")
    void question_delete_test() throws Exception {

        Long orderId1 = 101L;
        Long savedOrderId = questionService.saveQuestion(orderId1, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        Question question = questionRepository.findByOrderId(savedOrderId).orElseThrow();

        assertThat(questionService.deleteQuestion(question.getQuestionId())).isEqualTo("test2@example.com");

        assertThatThrownBy(
                () -> {
                    questionService.deleteQuestion(question.getQuestionId());
                }
        ).isInstanceOf(IllegalArgumentException. class);

    }


    @Test
    @Sql(statements = {
            "INSERT INTO orders (order_id, email, address, post_code, create_at) VALUES (101, 'test2@example.com', '','', CURRENT_TIMESTAMP);",
            "INSERT INTO product (product_id, name, price) VALUES (10, 'p1', 1000);",
            "INSERT INTO product (product_id, name, price) VALUES (11, 'p2', 1500);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1001, 10, 101, 2);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1002, 11, 101, 3);"
    })
    @DisplayName("문의 조회 테스트")
    void question_select_test() throws Exception {
        Long orderId1 = 101L;

        Long savedOrderId1 = questionService.saveQuestion(orderId1, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        QuestionResponseDto findQuestion1 = questionService.findQuestionByOrderId(savedOrderId1);
        assertThat(findQuestion1.getQuestionId()).isNotNull();
        assertThat(findQuestion1.getTitle()).isEqualTo("주문관련 문의드려요");
        assertThat(findQuestion1.getContent()).isEqualTo("언제오나요 배송");

        assertThatThrownBy(
                () -> {
                    questionService.findQuestionByOrderId(5L);
                }
        ).isInstanceOf(IllegalArgumentException. class);


    }

    @Test
    @DisplayName("답변 저장 테스트")
    void answer_save_test() throws Exception {
        Question saved = questionRepository.save(
                Question.builder().orderId(1000L).title("배송문의드립니다").content("왤캐안오죠").build()
        );
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("내일 도착할듯?");

        questionService.saveAnswer(saved.getQuestionId(), answerRequestDto);

        Question findQuestion = questionRepository.findById(saved.getQuestionId()).orElseThrow();

        assertThat(findQuestion.getAnswer()).isEqualTo("내일 도착할듯?");
        assertThat(findQuestion.getAnswerCreatedAt()).isNotNull();

    }

    @Test
    @DisplayName("완료된 답변 조회 테스트")
    void answer_answered_test() throws Exception {
        Question saved1 = questionRepository.save(
                Question.builder().orderId(1000L).title("배송문의드립니다").content("왤캐안오죠").build()
        );
        AnswerRequestDto answerRequestDto1 = new AnswerRequestDto("내일 도착할듯?");
        questionService.saveAnswer(saved1.getQuestionId(), answerRequestDto1);

        Question saved2 = questionRepository.save(
                Question.builder().orderId(1001L).title("2배송문의드립니다").content("2왤캐안오죠").build()
        );

        Question saved3 = questionRepository.save(
                Question.builder().orderId(1002L).title("3배송문의드립니다").content("3왤캐안오죠").build()
        );
        AnswerRequestDto answerRequestDto3 = new AnswerRequestDto("3내일 도착할듯?");
        questionService.saveAnswer(saved3.getQuestionId(), answerRequestDto3);


        List<AnsweredResponseDto> allAnsweredQuestion = questionService.findAllAnsweredQuestion();

        assertThat(allAnsweredQuestion.size()).isEqualTo(2);
        assertThat(allAnsweredQuestion.get(0).getContent()).isEqualTo("왤캐안오죠");
        assertThat(allAnsweredQuestion.get(0).getAnswer()).isEqualTo("내일 도착할듯?");
        assertThat(allAnsweredQuestion.get(1).getContent()).isEqualTo("3왤캐안오죠");
        assertThat(allAnsweredQuestion.get(1).getAnswer()).isEqualTo("3내일 도착할듯?");

    }

    @Test
    @DisplayName("미완료된 답변 조회 테스트")
    void answer_unanswered_test() throws Exception {
        Question saved1 = questionRepository.save(
                Question.builder().orderId(1000L).title("배송문의드립니다").content("왤캐안오죠").build()
        );
        AnswerRequestDto answerRequestDto1 = new AnswerRequestDto("내일 도착할듯?");
        questionService.saveAnswer(saved1.getQuestionId(), answerRequestDto1);

        Question saved2 = questionRepository.save(
                Question.builder().orderId(1001L).title("2배송문의드립니다").content("2왤캐안오죠").build()
        );

        Question saved3 = questionRepository.save(
                Question.builder().orderId(1002L).title("3배송문의드립니다").content("3왤캐안오죠").build()
        );
        AnswerRequestDto answerRequestDto3 = new AnswerRequestDto("3내일 도착할듯?");
        questionService.saveAnswer(saved3.getQuestionId(), answerRequestDto3);



        List<UnansweredResponseDto> allUnansweredQuestion = questionService.findAllUnansweredQuestion();

        assertThat(allUnansweredQuestion.size()).isEqualTo(1);
        assertThat(allUnansweredQuestion.get(0).getContent()).isEqualTo("2왤캐안오죠");

    }

}
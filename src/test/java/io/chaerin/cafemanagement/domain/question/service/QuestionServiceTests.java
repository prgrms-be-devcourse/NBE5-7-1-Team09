package io.chaerin.cafemanagement.domain.question.service;


import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

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


        questionService.saveAnswer();


    }



}
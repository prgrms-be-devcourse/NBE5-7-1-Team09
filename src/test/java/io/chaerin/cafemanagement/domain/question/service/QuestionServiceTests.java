package io.chaerin.cafemanagement.domain.question.service;


import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void init(){
        // 제품 3개 생성
//        TestUtils.createProduct(productRepository);
        // 주문 3건 생성



    }

    @Test
    @Sql(statements = {
            // 1) product 삽입
            "INSERT INTO product (product_id, name, price, image_url) VALUES (1, '1번콩', 1000, '/url1');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (2, '2번콩', 2000, '/url2');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (3, '3번콩', 3000, '/url3');",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (1, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",
            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (1, 1, 1, 3);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (2, 2, 1, 1);",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (2, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",


            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (3, 'test3@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",

            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (3, 1, 3, 3);"

    })
    @DisplayName("문의 생성 테스트")
    void question_save_test() throws Exception {


        Long savedOrderId = questionService.saveQuestion(1L, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        assertThat(savedOrderId).isEqualTo(1L);
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
            // 1) product 삽입
            "INSERT INTO product (product_id, name, price, image_url) VALUES (1, '1번콩', 1000, '/url1');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (2, '2번콩', 2000, '/url2');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (3, '3번콩', 3000, '/url3');",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (1, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",
            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (1, 1, 1, 3);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (2, 2, 1, 1);",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (2, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",


            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (3, 'test3@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",

            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (3, 1, 3, 3);"

    })
    @DisplayName("문의 삭제 테스트")
    void question_delete_test() throws Exception {

        Long savedOrderId = questionService.saveQuestion(1L, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        assertThat(savedOrderId).isEqualTo(1L);
        assertThat(questionService.existsQuestion(savedOrderId)).isTrue();

    }


    @Test
    @Sql(statements = {
            // 1) product 삽입
            "INSERT INTO product (product_id, name, price, image_url) VALUES (1, '1번콩', 1000, '/url1');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (2, '2번콩', 2000, '/url2');",
            "INSERT INTO product (product_id, name, price,image_url) VALUES (3, '3번콩', 3000, '/url3');",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (1, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",
            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (1, 1, 1, 3);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (2, 2, 1, 1);",


            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (2, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",


            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (3, 'test3@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",

            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (3, 1, 3, 3);"

    })
    @DisplayName("문의 생성 테스트")
    void question_save_test3() throws Exception {

        Long savedOrderId = questionService.saveQuestion(1L, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        assertThat(savedOrderId).isEqualTo(1L);
        assertThat(questionService.existsQuestion(savedOrderId)).isTrue();

    }




}
package io.chaerin.cafemanagement.domain.question.service;


import io.chaerin.cafemanagement.domain.order.dto.OrderCreateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderItemUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.service.OrderService;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import io.chaerin.cafemanagement.domain.question.dto.QuestionRequestDto;
import io.chaerin.cafemanagement.domain.question.dto.QuestionResponseDto;
import io.chaerin.cafemanagement.domain.question.entity.Question;
import io.chaerin.cafemanagement.domain.question.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class QuestionServiceTests {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

    Long orderId1;
    Long orderId2;

    @BeforeEach
    void init(){
//         제품 2개 생성
        Long productId1 = productRepository.save(
                Product.builder()
                        .name("1번콩")
                        .price(1000)
                        .imageUrl("/url1")
                        .build()
        ).getProductId();
        Long productId2 = productRepository.save(
                Product.builder()
                        .name("2번콩")
                        .price(2000)
                        .imageUrl("/url2")
                        .build()
        ).getProductId();

//         주문 2건 생성
        OrderCreateRequestDto o1 = new OrderCreateRequestDto(
                "test1@example.com",
                "address1",
                "12345",
                List.of(
                        new OrderItemUpdateRequestDto(productId1, 2),
                        new OrderItemUpdateRequestDto(productId2, 1)
                )
        );

        OrderCreateRequestDto o2 = new OrderCreateRequestDto(
                "test1@example.com",
                "address1",
                "12345",
                List.of(
                        new OrderItemUpdateRequestDto(productId1, 3),
                        new OrderItemUpdateRequestDto(productId2, 1)
                )
        );


        orderId1 = orderService.saveOrder(o1).getOrderId();
        orderId2 = orderService.saveOrder(o2).getOrderId();


    }

    @Test
    @DisplayName("문의 생성 테스트")
    void question_save_test() throws Exception {


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
    @DisplayName("문의 삭제 테스트")
    void question_delete_test() throws Exception {

        // 문의사항 2개 생성
        Long savedOrderId = questionService.saveQuestion(orderId1, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));

        Question question = questionRepository.findByOrderId(savedOrderId).orElseThrow();

        // 1번 문의를 삭제하면, 반환값은 해당 주문의 이메일과 같을 것이다.
        assertThat(questionService.deleteQuestion(question.getQuestionId())).isEqualTo("test1@example.com");

        // 해당 문의가 없다면 예외가 발생 할 것이다.
        assertThatThrownBy(
                () -> {
                    questionService.deleteQuestion(question.getQuestionId());
                }
        ).isInstanceOf(IllegalArgumentException. class);

    }


    @Test
    @DisplayName("문의 조회 테스트")
    void question_select_test() throws Exception {
        // 문의사항 2개 생성
        Long savedOrderId1 = questionService.saveQuestion(orderId1, new QuestionRequestDto("주문관련 문의드려요", "언제오나요 배송"));
        Long savedOrderId2 = questionService.saveQuestion(orderId2, new QuestionRequestDto("2 주문관련 문의드려요", "2 언제오나요 배송"));


        QuestionResponseDto findQuestion1 = questionService.findQuestionByOrderId(savedOrderId1);
        assertThat(findQuestion1.getQuestionId()).isNotNull();
        assertThat(findQuestion1.getTitle()).isEqualTo("주문관련 문의드려요");
        assertThat(findQuestion1.getContent()).isEqualTo("언제오나요 배송");


        QuestionResponseDto findQuestion2 = questionService.findQuestionByOrderId(savedOrderId2);
        assertThat(findQuestion2.getQuestionId()).isNotNull();
        assertThat(findQuestion2.getTitle()).isEqualTo("2 주문관련 문의드려요");
        assertThat(findQuestion2.getContent()).isEqualTo("2 언제오나요 배송");

        // 실패 시
        assertThatThrownBy(
                () -> {
                    questionService.findQuestionByOrderId(5L);
                }
        ).isInstanceOf(IllegalArgumentException. class);


    }




}
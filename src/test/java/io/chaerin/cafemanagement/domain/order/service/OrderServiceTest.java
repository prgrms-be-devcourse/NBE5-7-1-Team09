package io.chaerin.cafemanagement.domain.order.service;

import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @DisplayName("이메일로 주문 조회 시, dto 에 제대로 매핑되어야 한다.")
    @Sql(statements = {
            // 1) product 삽입
            "INSERT INTO product (product_id, name, price) VALUES (1, 'test', 5000);",
            // 2) orders 삽입
            "INSERT INTO orders (order_id, email, address, post_code, create_at) " +
                    "VALUES (100, 'test@example.com', '서울시', '01234', CURRENT_TIMESTAMP);",
            // 3) order_item 삽입
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) " +
                    "VALUES (1000, 1, 100, 3);"
    })
    @Test
    void 이메일로_주문목록_조회(){
        List<OrderResponseDto> results = orderService.getOrdersById("test@example.com");

        assertThat(results).hasSize(1);

        OrderResponseDto dto = results.get(0);

        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getOrderId()).isEqualTo(100);
        assertThat(dto.getOrderItemList()).hasSize(1);
        assertThat(dto.getOrderItemList().get(0).productName()).isEqualTo("test");
        assertThat(dto.getOrderItemList().get(0).quantity()).isEqualTo(3);
    }

    @DisplayName("이메일로 주문 조회 시, dto 에 제대로 매핑되어야 한다.")
    @Sql(statements = {
            "INSERT INTO orders (order_id, email, address, post_code, create_at) VALUES (101, 'test2@example.com', '','', CURRENT_TIMESTAMP);",
            "INSERT INTO product (product_id, name, price) VALUES (10, 'p1', 1000);",
            "INSERT INTO product (product_id, name, price) VALUES (11, 'p2', 1500);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1001, 10, 101, 2);",
            "INSERT INTO order_item (order_item_id, product_id, order_id, quantity) VALUES (1002, 11, 101, 3);"
    })
    @Test
    void 이메일로_주문목록에_여러_아이템_조회() {
        OrderResponseDto dto = orderService.getOrdersById("test2@example.com").get(0);
        assertThat(dto.getOrderItemList()).hasSize(2);
        assertThat(dto.getOrderItemList())
                .extracting("productName", "quantity")
                .containsExactly(
                        tuple("p1", 2),
                        tuple("p2", 3)
                );
    }

}
package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
ㅇimport static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private long testProductId;

    @BeforeEach
    void init() {
        Product product = Product.create("test", 1000);
        productRepository.save(product);
        testProductId = product.getProductId();
    }

    // @ModelAttribute 바인딩은 setter로 값 주입한다. DTO에 Setter 추가.
    @Test
    @DisplayName("주문을 생성한다")
    @Rollback(value = false)
    void Post_주문을_생성한다() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/orders")
                            .param("email", "user" + i + "@example.com")
                            .param("address", "test address" + i)
                            .param("postCode", "1000" + i)
                            .param("orderItem[0].productId", String.valueOf(testProductId))
                            .param("orderItem[0].quantity", String.valueOf(i + 1))
                    )
                    .andExpect(status().isOk())
                    .andExpect(view().name("order/result"))
                    .andExpect(model().attributeExists("order"));
        }
    }

    @Test
    @DisplayName("주문을 조회한다")
    void Get_주문을_조회한다() throws Exception {
        mockMvc.perform(get("/orders").param("email", "user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/list"))
                .andExpect(model().attributeExists("orders"));

    }



}
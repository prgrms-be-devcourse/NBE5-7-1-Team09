package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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

    @Test
    @DisplayName("동일 이메일에 다수의 주문이 있을 때 조회하는 경우")
    void Get_동일이메일_다수_주문조회() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/orders")
                            .param("email", "user1@example.com")
                            .param("address", "test address" + i)
                            .param("postCode", "1000" + i)
                            .param("orderItem[0].productId", String.valueOf(testProductId))
                            .param("orderItem[0].quantity", String.valueOf(i + 1)))
                    .andExpect(status().isOk());
        }

        MvcResult result = mockMvc.perform(get("/orders")
                        .param("email", "user1@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/list"))
                .andExpect(model().attributeExists("orders"))
                .andReturn();

        // result.getModelAndView().getModel().get("orders"); 의 반환 타입은 object라 타입 캐스팅을 해줬다.
        // get 하면 서비스에서 List<OrderResponseDto> 반환하므로, 가능.
        @SuppressWarnings("unchecked")
        List<OrderResponseDto> orders = (List<OrderResponseDto>) result.getModelAndView().getModel().get("orders");
        assertThat(orders).hasSize(3);
    }

    @Test
    @DisplayName("주문을 수정한다")
    void Put_주문수정() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/orders")
                        .param("email", "update_test@example.com")
                        .param("address", "초기 주소")
                        .param("postCode", "11111")
                        .param("orderItem[0].productId", String.valueOf(testProductId))
                        .param("orderItem[0].quantity", "1"))
                .andExpect(status().isOk())
                .andReturn();

        OrderResponseDto order = (OrderResponseDto) createResult.getModelAndView().getModel().get("order");
        Long orderId = order.getOrderId();

        // 수정 요청
        mockMvc.perform(put("/orders/" + orderId)
                        .param("email", "update_test@example.com") // 이메일은 동일
                        .param("address", "수정된 주소")
                        .param("postCode", "99999")
                        .param("orderItem[0].productId", String.valueOf(testProductId))
                        .param("orderItem[0].quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/result"))
                .andExpect(model().attributeExists("order"));

    }

    @Test
    @DisplayName("주문을 삭제한다")
    @Rollback(value = false)
    void 주문을_삭제한다() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/orders")
                        .param("email", "update_test@example.com")
                        .param("address", "삭제용 주소")
                        .param("postCode", "11111")
                        .param("orderItem[0].productId", String.valueOf(testProductId))
                        .param("orderItem[0].quantity", "1"))
                .andExpect(status().isOk())
                .andReturn();
        OrderResponseDto order = (OrderResponseDto) createResult.getModelAndView().getModel().get("order");
        Long orderId = order.getOrderId();

        mockMvc.perform(delete("/orders/" + orderId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));
    }


}

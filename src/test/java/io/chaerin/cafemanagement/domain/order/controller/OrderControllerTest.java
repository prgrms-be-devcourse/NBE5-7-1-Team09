package io.chaerin.cafemanagement.domain.order.controller;

import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @Autowired
    private UserRepository userRepository;

    private long testProductId;
    protected MockHttpSession session;// 세션 해당 테스트에서 전역 사용

    @BeforeEach
    void init() throws Exception {
        Product product = Product.create("test", 1000);
        productRepository.save(product);
        testProductId = product.getProductId();
    }

    @BeforeAll
    void setUp() throws Exception {
        // 이미 회원가입이 되어 있는지 확인하고 없으면 가입
        if (userRepository.findByUserName("testUser") == null) {
            mockMvc.perform(post("/join")
                            .param("userName", "testUser")
                            .param("password", "testPassword123"))
                    .andExpect(status().is3xxRedirection());
        }

        // 로그인 후 세션 확보
        MvcResult loginResult = mockMvc.perform(post("/login")
                        .param("userName", "testUser")
                        .param("password", "testPassword123"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // MvcResult에서 세션을 뽑아온다.
        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertNotNull(session, "세션이 생성되어야 한다");

        // 세션에 loginUser 속성이 있고, User 타입인지, 그리고 userName가 맞는지 검증
        Object attr = session.getAttribute("loginUser");
        assertNotNull(attr, "세션에 loginUser 속성이 있어야 한다");
        assertEquals("testUser", ((User) attr).getUserName());
        this.session = session;
    }

    // @ModelAttribute 바인딩은 setter로 값 주입한다. DTO에 Setter 추가.
    @Test
    @DisplayName("주문을 생성한다")
    @Rollback(value = false)
    void Post_주문을_생성한다() throws Exception {
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/orders")
                            .session(session)
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
                            .session(session)
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
                        .session(session)
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
                        .session(session)
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
                        .session(session)
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

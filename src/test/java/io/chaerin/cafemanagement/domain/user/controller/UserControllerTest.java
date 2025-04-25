package io.chaerin.cafemanagement.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.chaerin.cafemanagement.domain.user.dto.JoinRequestDto;
import io.chaerin.cafemanagement.domain.user.dto.UserLoginRequestDto;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 순차 실행
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void 회원가입_완료_확인() throws Exception {
        // 회원가입에 사용할 DTO 객체 생성
        JoinRequestDto joinRequestDto = new JoinRequestDto();
        joinRequestDto.setUserId("testUser");
        joinRequestDto.setPassword("testPassword123");

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)  // 폼 데이터 전송
                        .param("userId", joinRequestDto.getUserId())
                        .param("password", joinRequestDto.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }


    @Test
    @Order(2)
    void 로그인_성공시_세션에_유저정보저장() throws Exception {
        // 실제 로그인 POST 요청 (폼 데이터)
        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .param("userId", "testUser")
                        .param("password", "testPassword123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andReturn();

        // MvcResult에서 세션을 뽑아온다.
        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession(false);
        assertNotNull(session, "세션이 생성되어야 한다");

        // 세션에 loginUser 속성이 있고, User 타입인지, 그리고 userId가 맞는지 검증
        Object attr = session.getAttribute("loginUser");
        assertNotNull(attr, "세션에 loginUser 속성이 있어야 한다");
        assertEquals("testUser", ((User) attr).getUserId());
    }

    @Test
    @Order(3)
    void 로그아웃시_세션이_무효화되는지_확인() throws Exception {
        // 로그인해서 세션을 얻어온다.
        MvcResult loginResult = mockMvc.perform(post("/login")
                        .param("userId", "testUser")
                        .param("password", "testPassword123"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertNotNull(session, "로그인 후 세션이 생성되어야 한다");

        // 세션을 사용해서 로그아웃 요청
        MvcResult logoutResult = mockMvc.perform(post("/logout")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andReturn();

        // 로그아웃 후 세션이 무효화되었는지 확인
        assertNull(logoutResult.getRequest().getSession(false), "세션이 무효화되어야 한다");
    }
}
package io.chaerin.cafemanagement.domain.user.controller;

import io.chaerin.cafemanagement.domain.user.dto.UserLoginRequestDto;
import io.chaerin.cafemanagement.domain.user.dto.JoinRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    // 로그인 폼 표시 API
    @GetMapping("/login")
    public String showLoginForm() {
        return "/login";
    }

    // 회원 가입 API
    @Transactional
    @PostMapping("/join")
    public String join(@RequestBody JoinRequestDto joinRequest) {
        // 서비스에서 회원가입 로직 처리
        userService.join(joinRequest.getUserId(), joinRequest.getPassword());

        return "redirect:/login";
    }

    // 로그인 API
    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequestDto loginRequest,
                        HttpSession session,
                        Model model) {
        User user = userService.login(loginRequest.getUserId(), loginRequest.getPassword());

        session.setAttribute("loginUser", user); // 세션 저장

        log.info("user.getUserId() = {}", user.getUserId());

        return "redirect:/";
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 제거
        return "redirect:/";
    }
}

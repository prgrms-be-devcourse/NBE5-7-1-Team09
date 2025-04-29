package io.chaerin.cafemanagement.domain.user.controller;

import io.chaerin.cafemanagement.domain.user.dto.UserLoginRequestDto;
import io.chaerin.cafemanagement.domain.user.dto.JoinRequestDto;
import io.chaerin.cafemanagement.domain.user.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 폼 표시 API
    @GetMapping("/login")
    public String showLoginForm() {
        return "/user/login";
    }

    @GetMapping("/join")
    public String showJoinForm() {return "/user/join";}

    @GetMapping("/access-denied")
    public String showAccessDeniedForm() {return "/user/access_denied";}

    // 회원 가입 API
    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequestDto joinRequest) {
        // 서비스에서 회원가입 로직 처리
        userService.join(joinRequest.getUserName(), joinRequest.getPassword());

        return "redirect:/login";
    }

}

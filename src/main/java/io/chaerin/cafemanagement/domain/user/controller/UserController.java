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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "error/403";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute JoinRequestDto joinRequest, RedirectAttributes redirectAttributes) {
        if (userService.isUserNameDuplicated(joinRequest.getUserName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 사용 중인 아이디입니다.");
            return "redirect:/join";
        }

        userService.join(joinRequest.getUserName(), joinRequest.getPassword());
        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다!");
        return "redirect:/login";
    }

}

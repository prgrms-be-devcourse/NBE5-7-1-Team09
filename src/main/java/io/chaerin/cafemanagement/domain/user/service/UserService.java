package io.chaerin.cafemanagement.domain.user.service;

import io.chaerin.cafemanagement.domain.user.entity.Role;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static io.chaerin.cafemanagement.domain.user.entity.Role.MEMBER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String userName, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, encodedPassword, MEMBER);
        userRepository.save(user);
    }
//    public User login(String userName, String password, HttpSession session) {
//        User user = userRepository.findByUserName(userName);
//
//        if(user == null){
//            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        session.setAttribute("loginUser", user); // 세션 저장
//        return user;
//    }
}

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

    public boolean isUserNameDuplicated(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }
}

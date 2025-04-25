package io.chaerin.cafemanagement.domain.user.service;

import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String userId, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userId, encodedPassword);
        userRepository.save(user);
    }
    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId);

        if(user == null){
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}

package io.chaerin.cafemanagement.domain.user.repository;

import org.springframework.stereotype.Repository;
import io.chaerin.cafemanagement.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByUsername(String userId);
}

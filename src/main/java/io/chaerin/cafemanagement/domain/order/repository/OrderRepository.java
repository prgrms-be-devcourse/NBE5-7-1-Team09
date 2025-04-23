package io.chaerin.cafemanagement.domain.order.repository;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmail(String email);
}

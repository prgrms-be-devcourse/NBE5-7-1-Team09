package io.chaerin.cafemanagement.domain.order.repository;

import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

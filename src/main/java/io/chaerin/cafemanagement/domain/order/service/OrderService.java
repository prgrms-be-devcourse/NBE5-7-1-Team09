package io.chaerin.cafemanagement.domain.order.service;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDTO;
import io.chaerin.cafemanagement.domain.order.repository.OrderItemRepository;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public List<OrderResponseDTO> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        List<OrderResponseDTO> dtoList = new ArrayList<>();

        for (Order order : orders) {
            dtoList.add(new OrderResponseDTO(order));
        }

        return dtoList;
    }


    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalArgumentException("주문 없음"));
        orderRepository.delete(order);
    }
}

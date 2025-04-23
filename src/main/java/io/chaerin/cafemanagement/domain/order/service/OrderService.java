package io.chaerin.cafemanagement.domain.order.service;

import io.chaerin.cafemanagement.domain.order.dto.OrderItemUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import io.chaerin.cafemanagement.domain.order.repository.OrderItemRepository;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.entity.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderResponseDto saveOrder(OrderUpdateRequestDto request) {

        Order order = Order.create(request.getEmail(), request.getAddress(), request.getPostCode());

        for (OrderItemUpdateRequestDto dto : request.getOrderItem()) {
            // product id 검증 (product Repository 를 임시 구현했다)
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            OrderItem orderItem = OrderItem.create(product, order, dto.getQuantity());
            order.getOrderItemList().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        List<OrderResponseDto> dtoList = new ArrayList<>();

        for (Order order : orders) {
            dtoList.add(new OrderResponseDto(order));
        }

        return dtoList;
    }


    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 없음"));
        orderRepository.delete(order);
    }
}

package io.chaerin.cafemanagement.domain.order.service;

import io.chaerin.cafemanagement.domain.order.dto.OrderCreateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderItemUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.product.entity.Product;

import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
import io.chaerin.cafemanagement.domain.user.entity.User;
import io.chaerin.cafemanagement.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderResponseDto saveOrder(OrderCreateRequestDto request, HttpSession session) {
        //TODO @지민혁 로그인 검증 인터셉터로 분리
        //로그인 세션 검증 로직
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }

        //userId에 해당되는 user객체 받기
        Long userId = ((User) loginUser).getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 상품 생성 로직
        Order order = Order.create(user, request.getEmail(), request.getAddress(), request.getPostCode());

        for (OrderItemUpdateRequestDto dto : request.getOrderItem()) {
            // product id 검증 (product Repository 를 임시 구현했다)
            Product product = productRepository.findById(dto.getProductId())

                    .orElseThrow(() -> new IllegalArgumentException("[create]: 존재하지 않는 상품입니다."));

            OrderItem orderItem = OrderItem.create(product, order, dto.getQuantity());
            order.getOrderItemList().add(orderItem);
        }
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersById(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new IllegalStateException("로그인 후 이용해주세요.");
        }
        Long userId = ((User) loginUser).getUserId();
        List<Order> orders = orderRepository.findByUserUserId(userId);

        List<OrderResponseDto> dtoList = new ArrayList<>();
        for (Order order : orders) {
            dtoList.add(new OrderResponseDto(order));
        }

        return dtoList;
    }

    // id 단건 조회
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id 주문이 없습니다. id=" + orderId));
        return new OrderResponseDto(order);
    }

    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponseDto::new);
    }
    public OrderResponseDto updateOrder(Long orderId, OrderUpdateRequestDto request) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 없음"));
        order.fixAddress(request.getAddress(), request.getPostCode());
        order.clearItems();


        for (OrderItemUpdateRequestDto dto : request.getOrderItem()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("[update] 존재하지 않는 상품입니다."));
            OrderItem oi = OrderItem.create(product, order, dto.getQuantity());
            order.addItem(oi);
        }

        // 더티체킹
        return new OrderResponseDto(order);
    }


    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문 없음"));
        orderRepository.delete(order);
    }
}

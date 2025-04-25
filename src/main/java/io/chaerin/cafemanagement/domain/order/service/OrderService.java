package io.chaerin.cafemanagement.domain.order.service;

import io.chaerin.cafemanagement.domain.order.dto.OrderItemUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import io.chaerin.cafemanagement.domain.order.repository.OrderItemRepository;
import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
import io.chaerin.cafemanagement.domain.product.entity.Product;
import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
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
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("[create]: 존재하지 않는 상품입니다."));

            // Product에 stock이 필요하지 않을까?
            // 생성 시에 가능한 quantity인지 stock과 비교 검증.
//            if (product.getStock() < dto.getQuantity()) {
//                throw new IllegalArgumentException(product.getName());
//            }

            OrderItem orderItem = OrderItem.create(product, order, dto.getQuantity());
            order.getOrderItemList().add(orderItem);
        }
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        List<OrderResponseDto> dtoList = new ArrayList<>();
        // dto 를 바로 가져와서... => 고민좀 해보고.
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

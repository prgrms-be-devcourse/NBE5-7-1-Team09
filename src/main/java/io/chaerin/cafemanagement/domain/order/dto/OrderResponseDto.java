package io.chaerin.cafemanagement.domain.order.dto;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter

@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private final String email;
    private final Long orderId;
    private final String address;
    private final String postCode;
    private final LocalDateTime createdAt;
    private final List<OrderItemResponseDto> orderItemList;
    private int totalPrice;

    public OrderResponseDto(Order order) {
        this.email = order.getEmail();
        this.orderId = order.getOrderId();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.createdAt = order.getCreatedAt();

        List<OrderItemResponseDto> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItemList()) {
            Long productId = item.getProduct().getProductId();
            String productName = item.getProduct().getName();
            Integer quantity = item.getQuantity();
            Integer price = item.getProduct().getPrice();
            items.add(new OrderItemResponseDto(productId, productName, quantity, price));
        }

        this.orderItemList = items;
        this.totalPrice = order.calculateTotalPrice ();

    }

    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderCutoff = createdAt.toLocalDate().atTime(14, 0);

        // 주문이 당일 14시 이전이면, 같은 날 14시에 배송 시작
        if (createdAt.isBefore(orderCutoff)) {
            orderCutoff = createdAt.toLocalDate().atTime(14, 0);
        } else {
            // 주문이 당일 14시 이후면, 다음날 14시에 배송 시작
            orderCutoff = createdAt.toLocalDate().plusDays(1).atTime(14, 0);
        }

        return now.isAfter(orderCutoff) ? "배송중" : "배송 준비중";
    }

}

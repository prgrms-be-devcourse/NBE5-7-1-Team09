package io.chaerin.cafemanagement.domain.order.dto;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderResponseDto {
    private final String email;
    private final Long orderNumber;
    private final String address;
    private final String postCode;
    private final LocalDateTime createdAt;
    private final List<OrderItemResponseDto> orderItemList;


    public OrderResponseDto(Order order) {
        this.email = order.getEmail();
        this.orderNumber = order.getOrderId();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.createdAt = order.getCreatedAt();

        List<OrderItemResponseDto> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItemList()) {
            String productName = item.getProduct().getName();
            items.add(new OrderItemResponseDto(productName, item.getQuantity()));
        }

        this.orderItemList = items;

    }
}

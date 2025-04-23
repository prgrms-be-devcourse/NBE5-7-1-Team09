package io.chaerin.cafemanagement.domain.order.dto;

import io.chaerin.cafemanagement.domain.order.entity.Order;
import io.chaerin.cafemanagement.domain.order.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDTO {
    private String email;
    private String orderNumber;
    private String address;
    private String postCode;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> orderItemList;


    public OrderResponseDTO(Order order) {
        this.email = order.getEmail();
        this.orderNumber = order.getOrderId().toString();
        this.address = order.getAddress();
        this.postCode = order.getPostCode();
        this.createdAt = order.getCreatedAt();

        List<OrderItemResponseDTO> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItemList()) {
            String productName = item.getProduct().getName();
            items.add(new OrderItemResponseDTO(productName, item.getQuantity()));
        }

        this.orderItemList = items;

    }
}

package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private final String productName;
    private final Integer quantity;

    public OrderItemResponseDto(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}

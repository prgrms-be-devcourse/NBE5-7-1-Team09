package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderItemResponseDTO {
    private final String productName;
    private final Integer quantity;

    public OrderItemResponseDTO(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}

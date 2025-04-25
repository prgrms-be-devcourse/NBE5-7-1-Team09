package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private final Long productId;
    private final String productName;
    private final Integer quantity;


    public OrderItemResponseDto(Long productId, String productName, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

}

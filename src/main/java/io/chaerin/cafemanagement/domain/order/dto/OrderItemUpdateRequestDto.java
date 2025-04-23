package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderItemUpdateRequestDto {
    private Long productId;
    private Integer quantity;
}

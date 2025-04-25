package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class OrderItemUpdateRequestDto {
    private Long productId;
    private Integer quantity;
}

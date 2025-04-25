package io.chaerin.cafemanagement.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemUpdateRequestDto {
    private Long productId;
    private Integer quantity;
}

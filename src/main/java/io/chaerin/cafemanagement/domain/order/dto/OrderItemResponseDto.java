package io.chaerin.cafemanagement.domain.order.dto;

public record OrderItemResponseDto(
        Long productId,
        String productName,
        Integer quantity,
        Integer price
) {

}

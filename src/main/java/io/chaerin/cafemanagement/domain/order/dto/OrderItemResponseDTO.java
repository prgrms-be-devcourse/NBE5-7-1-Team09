package io.chaerin.cafemanagement.domain.order.dto;

public class OrderItemResponseDTO {
    private String productName;
    private Integer quantity;

    public OrderItemResponseDTO(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

}

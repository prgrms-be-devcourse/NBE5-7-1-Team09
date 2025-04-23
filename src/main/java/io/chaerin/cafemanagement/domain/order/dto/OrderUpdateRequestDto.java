package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderUpdateRequestDto {
    private String email;
    private String address;
    private String postCode;
    private List<OrderItemUpdateRequestDto> orderItem;
}

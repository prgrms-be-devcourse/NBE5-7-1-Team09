package io.chaerin.cafemanagement.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderUpdateRequestDto {
    private String email;
    private String address;
    private String postCode;
    private List<OrderItemUpdateRequestDto> orderItem;
}

package io.chaerin.cafemanagement.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreateRequestDto {
    private String email;
    private String address;
    private String postCode;
    private List<OrderItemUpdateRequestDto> orderItem = new ArrayList<>();
}

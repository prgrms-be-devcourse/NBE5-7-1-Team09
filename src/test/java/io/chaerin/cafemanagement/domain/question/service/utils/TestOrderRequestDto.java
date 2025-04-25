package io.chaerin.cafemanagement.domain.question.service.utils;

import java.util.List;

public class TestOrderRequestDto {

    private String email;
    private String address;
    private String postCode;
    private List<TestOrderItems> orderItem;

    public TestOrderRequestDto(String email, String address, String postCode, List<TestOrderItems> orderItem) {
        this.email = email;
        this.address = address;
        this.postCode = postCode;
        this.orderItem = orderItem;
    }
}

package io.chaerin.cafemanagement.domain.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String userId;
    private String password;
}

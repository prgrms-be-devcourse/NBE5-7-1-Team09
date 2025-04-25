package io.chaerin.cafemanagement.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String userId;
    private String password;
}

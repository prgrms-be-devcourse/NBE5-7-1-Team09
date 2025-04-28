package io.chaerin.cafemanagement.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String userName;
    private String password;
}

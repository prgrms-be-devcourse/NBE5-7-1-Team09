package io.chaerin.cafemanagement.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinRequestDto {
    private String userId;
    private String password;
}


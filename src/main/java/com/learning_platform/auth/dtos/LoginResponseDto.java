package com.learning_platform.auth.dtos;

import com.learning_platform.auth.security.UserPrincipal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;

    private UserPrincipal user;


}

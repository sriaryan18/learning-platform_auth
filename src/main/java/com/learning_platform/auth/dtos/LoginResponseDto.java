package com.learning_platform.auth.dtos;

import com.learning_platform.dtos.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

  private String accessToken;
  private String refreshToken;

  private UserDto user;
}

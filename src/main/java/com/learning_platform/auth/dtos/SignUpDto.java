package com.learning_platform.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

  private String username;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String phoneNumber;
}

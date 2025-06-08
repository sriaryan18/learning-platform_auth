package com.learning_platform.auth.dtos;

import com.learning_platform.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDto {

  private Role newRole;
  private String userId;
}

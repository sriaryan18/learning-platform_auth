package com.learning_platform.auth.contollers;

import com.learning_platform.auth.dtos.UpdateRoleDto;
import com.learning_platform.auth.service.UpdateRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/v1/updateRole")
public class UpdateRole {

  private final UpdateRoleService updateRoleService;

  UpdateRole(UpdateRoleService updateRoleService) {
    this.updateRoleService = updateRoleService;
  }

  @PutMapping("/")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<String> updateRole(@RequestBody UpdateRoleDto updateRole) {
    try {
      boolean isUpdated = updateRoleService.updateRole(updateRole);

      if (isUpdated) {
        return ResponseEntity.ok("Role updated successfully");
      }
      return ResponseEntity.ok("Role not updated");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating role");
    }
  }
}

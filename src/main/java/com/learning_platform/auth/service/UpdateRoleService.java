package com.learning_platform.auth.service;

import com.learning_platform.auth.dtos.UpdateRoleDto;
import com.learning_platform.auth.models.User;
import com.learning_platform.auth.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UpdateRoleService {

  private final UserRepository userRepository;

  UpdateRoleService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean updateRole(UpdateRoleDto updateRole) {

    Optional<User> user = userRepository.findById(updateRole.getUserId());

    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    user.get().setRole(updateRole.getNewRole());
    userRepository.save(user.get());

    return true;
  }
}

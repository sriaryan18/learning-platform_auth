package com.learning_platform.auth.repository;

import com.learning_platform.auth.models.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

  public Optional<User> getUserByUsername(String username);

  public Optional<User> findByUsername(String username);
}

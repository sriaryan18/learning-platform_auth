package com.learning_platform.auth.repository;

import com.learning_platform.auth.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

    public Optional<User> getUserByUsername(String username);
    public Optional<User> findByUsername(String username);
}

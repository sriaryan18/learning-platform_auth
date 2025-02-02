package com.learning_platform.auth.service;


import com.learning_platform.auth.dtos.LoginDto;
import com.learning_platform.auth.dtos.LoginResponseDto;
import com.learning_platform.auth.dtos.SignUpDto;
import com.learning_platform.auth.mapper.UserMapper;
import com.learning_platform.auth.models.User;
import com.learning_platform.auth.repository.UserRepository;
//import com.learning_platform.auth.utils.JWTUtils;
import com.learning_platform.auth.utils.JWTUtils;
import com.learning_platform.auth.utils.PasswordHashManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordHashManager passwordHashManager;


    @Autowired
    UserMapper userMapper;

    @Autowired
    JWTUtils jwtUtils;





    public LoginResponseDto handleLogin(LoginDto loginDto){


        Optional<User> userOptional = userRepository
                .findByUsername(loginDto.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String decryptedPassword = passwordHashManager.decrypt(user.getPassword());
            if(decryptedPassword.equals(loginDto.getPassword())){
                String access_token = jwtUtils.generateToken(userOptional.get());
                return LoginResponseDto.builder()
                        .access_token(access_token).build();
            }

        }
        throw new RuntimeException("Credentials matching failed");

    }

    public LoginResponseDto handleSignUp(SignUpDto signUpDto){
        String encryptedPassword = passwordHashManager.encrypt(signUpDto.getPassword());
        signUpDto.setPassword(encryptedPassword);
        User savedEntity =  userRepository.save(userMapper.convertSignUpDtoToUser(signUpDto));
        String access_token = jwtUtils.generateToken(savedEntity);

        return LoginResponseDto.builder()
                .access_token(access_token).build();


    }
}

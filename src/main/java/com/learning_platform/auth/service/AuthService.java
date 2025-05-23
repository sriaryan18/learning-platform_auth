package com.learning_platform.auth.service;


import com.learning_platform.auth.dtos.LoginDto;
import com.learning_platform.auth.dtos.LoginResponseDto;
import com.learning_platform.auth.dtos.SignUpDto;
import com.learning_platform.auth.dtos.UserPrincipal;
import com.learning_platform.auth.mapper.UserMapper;
import com.learning_platform.auth.models.User;
import com.learning_platform.auth.repository.UserRepository;
//import com.learning_platform.auth.utils.JWTUtils;
import com.learning_platform.auth.utils.JWTUtils;
import com.learning_platform.auth.utils.PasswordHashManager;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
                UserPrincipal userPrincipal = UserPrincipal.builder().user(user).build();
                String access_token = jwtUtils.generateToken(userPrincipal);
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
        UserPrincipal userPrincipal = UserPrincipal.builder().user(savedEntity).build();
        String access_token = jwtUtils.generateToken(userPrincipal);

        return LoginResponseDto.builder()
                .access_token(access_token).build();


    }

    public Claims getUserDetailsByToken(String token){
        String username = jwtUtils.extractUsername(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            return jwtUtils.decodeJWTClaims(token);
        }
        throw new RuntimeException("User not found");
    }

}

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
import com.learning_platform.auth.utils.TokenType;

import io.jsonwebtoken.Claims;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordHashManager passwordHashManager;
    private final UserMapper userMapper;
    private final JWTUtils jwtUtils;


    public AuthService(
        UserRepository userRepository,
        PasswordHashManager passwordHashManager,
        UserMapper userMapper,
        JWTUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.passwordHashManager = passwordHashManager;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDto handleLogin(LoginDto loginDto){


        Optional<User> userOptional = userRepository
                .findByUsername(loginDto.getUsername());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            String decryptedPassword = passwordHashManager.decrypt(user.getPassword());
            if(decryptedPassword.equals(loginDto.getPassword())){
                UserPrincipal userPrincipal = UserPrincipal.builder().user(user).build();
                String accessToken = jwtUtils.generateToken(userPrincipal, TokenType.ACCESS);
                String refreshToken = jwtUtils.generateToken(userPrincipal, TokenType.REFRESH);
                return LoginResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .user(userPrincipal) 
                        .build();
            }

        }
        throw new RuntimeException("Credentials matching failed");

    }

    public LoginResponseDto handleSignUp(SignUpDto signUpDto){
        String encryptedPassword = passwordHashManager.encrypt(signUpDto.getPassword());
        signUpDto.setPassword(encryptedPassword);
        User savedEntity =  userRepository.save(userMapper.convertSignUpDtoToUser(signUpDto));
        UserPrincipal userPrincipal = UserPrincipal.builder().user(savedEntity).build();
        String accessToken = jwtUtils.generateToken(userPrincipal, TokenType.ACCESS);
        String refreshToken = jwtUtils.generateToken(userPrincipal, TokenType.REFRESH);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


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

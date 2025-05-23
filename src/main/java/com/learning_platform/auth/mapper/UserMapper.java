package com.learning_platform.auth.mapper;

import com.learning_platform.auth.dtos.SignUpDto;

import com.learning_platform.auth.dtos.UserPrincipal;

import com.learning_platform.auth.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertSignUpDtoToUser(SignUpDto signUpDto){
           return  User.builder()
                   .username(signUpDto.getUsername())
                .customerType(signUpDto.getCustomerType())
                .paymentStatus(signUpDto.getPaymentStatus())
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .password(signUpDto.getPassword())
                .paymentStatus(signUpDto.getPaymentStatus())
                .build();
    }

    public UserDetails convertUserToUserDetails(User user){
        
        return  org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities("USER")
        .build();
    }



}

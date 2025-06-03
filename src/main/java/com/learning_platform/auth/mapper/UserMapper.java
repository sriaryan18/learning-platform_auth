package com.learning_platform.auth.mapper;

import java.time.LocalDateTime;


import com.learning_platform.auth.constants.AppConstants;
import com.learning_platform.auth.dtos.SignUpDto;
import com.learning_platform.auth.dtos.UserDto;
import com.learning_platform.auth.enums.PaymentType;
import com.learning_platform.auth.enums.Role;
import com.learning_platform.auth.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertSignUpDtoToUser(SignUpDto signUpDto){
           return  User.builder()
                   .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .phoneNumber(signUpDto.getPhoneNumber())
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .password(signUpDto.getPassword())
                .paymentType(PaymentType.FREE)
                .role(Role.STUDENT)
                .createdAt(LocalDateTime.now())
                .organizationId(AppConstants.CLAIM_ORGANIZATION_ID_DEFAULT)
                .organizationName(AppConstants.CLAIM_ORGANIZATION_NAME_DEFAULT)
                .build();
    }
        
    public UserDetails convertUserToUserDetails(User user){
        
        return  org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities("USER")
        .build();
    }
    public UserDto convertUserToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .paymentType(user.getPaymentType())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .organizationId(user.getOrganizationId())
                .organizationName(user.getOrganizationName())
                .build();
    }



}

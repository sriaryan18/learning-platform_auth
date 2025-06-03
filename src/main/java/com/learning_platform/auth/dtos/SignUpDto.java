package com.learning_platform.auth.dtos;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpDto {


    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;



}

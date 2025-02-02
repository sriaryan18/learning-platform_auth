package com.learning_platform.auth.dtos;

import com.learning_platform.auth.enums.CustomerType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpDto {


    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String paymentStatus;

    private CustomerType customerType; // Individual , Group



}

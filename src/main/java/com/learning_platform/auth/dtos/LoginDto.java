package com.learning_platform.auth.dtos;


import com.learning_platform.auth.enums.CustomerType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {


    private String username;

    private String password;

}

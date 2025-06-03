package com.learning_platform.auth.dtos;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {


    private String username;

    private String password;

}

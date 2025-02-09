package com.learning_platform.auth.contollers;


import com.learning_platform.auth.dtos.LoginDto;
import com.learning_platform.auth.dtos.LoginResponseDto;
import com.learning_platform.auth.dtos.SignUpDto;
import com.learning_platform.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth/api/v1")
public class AuthController {

    @Autowired
    AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto){

        try {
            LoginResponseDto loginResponseDto =  authService.handleLogin(loginDto);
            return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> handleSignUp(@RequestBody SignUpDto signUpDto){
        try{
        LoginResponseDto loginDto = authService.handleSignUp(signUpDto);
        return new ResponseEntity<LoginResponseDto>(loginDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/verifyToken")
    public ResponseEntity<?> getUserDetailsByToken(@RequestHeader String authorization) {
        return ResponseEntity.ok(authService.getUserDetailsByToken(authorization));
    }
    

}

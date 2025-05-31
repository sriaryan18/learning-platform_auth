package com.learning_platform.auth.contollers;

import com.learning_platform.auth.dtos.LoginDto;
import com.learning_platform.auth.dtos.LoginResponseDto;
import com.learning_platform.auth.dtos.SignUpDto;
import com.learning_platform.auth.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @Value("${jwt.access-expiration}")
    private int accessExpiration;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        try {
            LoginResponseDto loginResponseDto = authService.handleLogin(loginDto);

            Cookie cookie = new Cookie("token", loginResponseDto.getAccessToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge(accessExpiration);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("TEST");
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> handleSignUp(@RequestBody SignUpDto signUpDto) {
        try {
            LoginResponseDto loginDto = authService.handleSignUp(signUpDto);
            return new ResponseEntity<LoginResponseDto>(loginDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/verifyToken")
    public ResponseEntity<?> getUserDetailsByToken(@RequestHeader String authorization) {
        return ResponseEntity.ok(authService.getUserDetailsByToken(authorization));
    }

    @GetMapping("test")
    public String test() {
        return "TEST";
    }

}

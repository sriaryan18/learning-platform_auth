package com.learning_platform.auth.utils;

import com.learning_platform.auth.constants.AppConstants;
import com.learning_platform.auth.dtos.UserPrincipal;
import com.learning_platform.auth.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-expiration}") 
    private Long refreshTokenExpiration;

    private static final String BEARER_PREFIX = "Bearer ";

    public String generateToken(UserPrincipal userDetails, TokenType tokenType) {
        Map<String, Object> claims = new HashMap<>();
        // Adding CLAIMS
       
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(AppConstants.CLAIM_ROLE, roles);
        claims.put(AppConstants.CLAIM_ORGANIZATION_ID, userDetails.getUser().getOrganizationId());
        claims.put(AppConstants.CLAIM_ORGANIZATION_NAME, userDetails.getUser().getOrganizationName());
        claims.put(AppConstants.CLAIM_PAYMENT_TYPE, userDetails.getUser().getPaymentType());
        claims.put(AppConstants.CLAIM_CREATED_AT, userDetails.getUser().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        claims.put(AppConstants.CLAIM_ID, userDetails.getUser().getId());
        claims.put(AppConstants.CLAIM_EMAIL, userDetails.getUser().getEmail());
        claims.put(AppConstants.CLAIM_PHONE_NUMBER, userDetails.getUser().getPhoneNumber());
        // claims.put(AppConstants.CLAIM_USER, userDetails.getUser());

        return createToken(claims, userDetails.getUsername(), tokenType);
    }

private String createToken(Map<String, Object> claims, String subject, TokenType tokenType) {
    if (tokenType == null) {
        throw new IllegalArgumentException("TokenType cannot be null");
    }
     long expiryInSeconds = tokenType == TokenType.ACCESS ? accessTokenExpiration : refreshTokenExpiration;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

public String extractUsername(String token) {
    if(token.startsWith(BEARER_PREFIX)){
        token = token.substring(BEARER_PREFIX.length());
     }   
     return extractClaim(token, Claims::getSubject);
 }
public Claims decodeJWTClaims(String token){
    if(token.startsWith(BEARER_PREFIX)){
        token = token.substring(BEARER_PREFIX.length());
     }  
     return extractAllClaims(token);
 }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)

                .getBody();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

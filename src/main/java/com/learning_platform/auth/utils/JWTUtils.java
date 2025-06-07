package com.learning_platform.auth.utils;

import com.learning_platform.auth.dtos.UserPrincipal;
import com.learning_platform.constants.AppConstants;
import com.learning_platform.enums.TokenType;
import com.learning_platform.utils.CommonJwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils extends CommonJwtUtils {

  @Value("${jwt.access-expiration}")
  private long accessTokenExpiration;

  @Value("${jwt.refresh-expiration}")
  private long refreshTokenExpiration;

  @Value("${jwt.secret}")
  private String jwtSecret;

  public String generateToken(UserPrincipal userDetails, TokenType tokenType) {
    Map<String, Object> claims = new HashMap<>();
    // Adding CLAIMS

    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    claims.put(AppConstants.CLAIM_ROLE, roles);
    claims.put(AppConstants.CLAIM_ORGANIZATION_ID, userDetails.getUser().getOrganizationId());
    claims.put(AppConstants.CLAIM_ORGANIZATION_NAME, userDetails.getUser().getOrganizationName());
    claims.put(AppConstants.CLAIM_PAYMENT_TYPE, userDetails.getUser().getPaymentType());
    claims.put(
        AppConstants.CLAIM_CREATED_AT,
        userDetails
            .getUser()
            .getCreatedAt()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
    long expiryInSeconds =
        tokenType == TokenType.ACCESS ? this.accessTokenExpiration : this.refreshTokenExpiration;
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
        .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }
}

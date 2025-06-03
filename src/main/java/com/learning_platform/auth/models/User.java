package com.learning_platform.auth.models;


import com.learning_platform.auth.enums.Role;
import com.learning_platform.auth.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "auth")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String id;

private String username;
private String password;
private String firstName;
private String lastName;
private String email;
private String phoneNumber;
@Enumerated(EnumType.STRING)
@Column(name = "payment_type")
private PaymentType paymentType; 

@Enumerated(EnumType.STRING)
@Column(name = "role")
private Role role;

@Column(name = "created_at")
private LocalDateTime createdAt;
private String organizationId;

private String organizationName;

}

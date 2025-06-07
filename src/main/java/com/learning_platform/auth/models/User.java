package com.learning_platform.auth.models;

import com.learning_platform.enums.PaymentType;
import com.learning_platform.enums.Role;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "auth")
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

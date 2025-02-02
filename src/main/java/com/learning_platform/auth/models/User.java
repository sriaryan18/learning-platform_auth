package com.learning_platform.auth.models;


import com.learning_platform.auth.enums.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Column(name = "payment_status")
private String paymentStatus;

@Column(name = "customer_type")
private CustomerType customerType; // Individual , Group

// add user attributes here

}

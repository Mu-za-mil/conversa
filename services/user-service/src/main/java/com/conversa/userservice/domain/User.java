package com.conversa.userservice.domain;
import jakarta.persistence.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="users", uniqueConstraints={@UniqueConstraint(name="uk_users_username",columnNames="username"),@UniqueConstraint(name="uk_users_email",columnNames="email")})
public class User {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=50) private String username;
 @Column(nullable=false,length=254) private String email;
 @Column(name="password_hash",nullable=false,length=100) private String password;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 @PrePersist void onCreate(){Instant now=Instant.now();createdAt=now;updatedAt=now;}
 @PreUpdate void onUpdate(){updatedAt=Instant.now();}
 protected User(){}
 public User(String username,String email,String password){this.username=username;this.email=email;this.password=password;}
 public UUID getId(){return id;} public String getUsername(){return username;} public String getEmail(){return email;} public String getPassword(){return password;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
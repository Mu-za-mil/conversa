package com.conversa.userservice.controller;
import com.conversa.userservice.dto.*; import com.conversa.userservice.service.AuthService; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final AuthService auth; public AuthController(AuthService auth){this.auth;}
 @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public MessageResponse register(@Valid @RequestBody RegisterRequest request){auth.register(request);return new MessageResponse("User registered successfully");}
 @PostMapping("/login") public TokenResponse login(@Valid @RequestBody LoginRequest request){return auth.login(request);}
 @PostMapping("/refresh") public TokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request){return auth.refresh(request.refreshToken());}
}
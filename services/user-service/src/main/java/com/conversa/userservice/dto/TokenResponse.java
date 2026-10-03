package com.conversa.userservice.dto;
public record TokenResponse(String accessToken,String refreshToken,String tokenType){}
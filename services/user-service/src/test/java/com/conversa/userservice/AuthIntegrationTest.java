package com.conversa.userservice;

import com.conversa.userservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTest {
    @Container static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void database(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired MockMvc mvc;
    @Autowired UserRepository users;

    @BeforeEach void clean() { users.deleteAll(); }

    @Test void registerAndLoginReturnsAccessAndRefreshTokens() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
            {"username":"muzamil","email":"muzamil@example.com","password":"Password123!"}
            """)).andExpect(status().isCreated());

        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
            {"username":"muzamil","password":"Password123!"}
            """)).andExpect(status().isOk())
          .andExpect(jsonPath("$.accessToken").isString())
          .andExpect(jsonPath("$.refreshToken").isString())
          .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test void duplicateUsernameReturnsConflict() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
            {"username":"duplicate","email":"one@example.com","password":"Password123!"}
            """)).andExpect(status().isCreated());

        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
            {"username":"duplicate","email":"two@example.com","password":"Password123!"}
            """)).andExpect(status().isConflict());
    }

    @Test void invalidCredentialsReturnUnauthorized() throws Exception {
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
            {"username":"missing","password":"wrongpassword"}
            """)).andExpect(status().isUnauthorized());
    }
}
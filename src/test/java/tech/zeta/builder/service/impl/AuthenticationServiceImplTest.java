package tech.zeta.builder.service.impl;

import org.junit.jupiter.api.*;
import tech.zeta.builder.service.impl.AuthenticationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationServiceImplTest {

    private static AuthenticationServiceImpl authService;

    @BeforeAll
    static void setup() {
        authService = new AuthenticationServiceImpl();
    }

    @Test
    void testLoginSuccess() {
        int userId = authService.login("john@gmail.com", "12345678", "Builder");
        assertTrue(userId > 0, "Login should succeed for valid credentials");
    }

    @Test
    void testLoginFailureWrongPassword() {
        int userId = authService.login("jack@gmail.com", "wrongpass", "ProjectManager");
        assertEquals(-1, userId, "Login should fail with incorrect password");
    }

    @Test
    void testLoginFailureWrongRole() {
        int userId = authService.login("john@gmail.com", "12345678", "Client");
        assertEquals(-1, userId, "Login should fail if role does not match");
    }

    @Test
    void testLoginFailureUserNotFound() {
        int userId = authService.login("notfound@example.com", "pass123", "Client");
        assertEquals(-1, userId, "Login should fail for non-existent user");
    }
}

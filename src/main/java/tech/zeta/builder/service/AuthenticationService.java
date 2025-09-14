package tech.zeta.builder.service;

import tech.zeta.builder.exception.InvalidUserException;

public interface AuthenticationService {
    public int register(String name, String email, String password, String role);
    public int login(String email, String password, String role);
}

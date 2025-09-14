package tech.zeta.builder.service.impl;

import tech.zeta.builder.dao.UserDAO;
import tech.zeta.builder.model.User;
import tech.zeta.builder.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationServiceImpl() {
        this.userDAO = new UserDAO();
    }

    @Override
    public int register(String name, String email, String password, String role) {
        // Check if User already exists
        User existingUser = userDAO.getUserByEmail(email);
        if (existingUser != null) {
            return -1; // User exists
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        return userDAO.saveUser(user);
    }

    @Override
    public int login(String email, String password, String role) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole().equals(role)) {
            return user.getId(); // Success
        }
        return -1; // Failure
    }
}

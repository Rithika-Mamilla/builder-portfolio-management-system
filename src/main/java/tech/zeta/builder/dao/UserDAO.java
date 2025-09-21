package tech.zeta.builder.dao;

import tech.zeta.builder.model.User;
import tech.zeta.builder.util.DBUtil;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    // Database Connection
    public UserDAO() {
        this.connection = DBUtil.getConnection();
    }

    // Add new user
    public int saveUser(User user) {
        String sql = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?)";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getRole());

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) return resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return -1;
    }

    // Get the user using email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return null;
    }

    // Check whether the user exists
    public boolean checkUser(int id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.getMessage());
        }
        return false;
    }
}

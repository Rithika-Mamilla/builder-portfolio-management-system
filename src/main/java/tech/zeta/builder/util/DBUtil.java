package tech.zeta.builder.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    // Establish Database Connectivity
    private static Connection connection = null;
    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(inputStream);
        } catch (IOException ioException) {
            System.err.println("Exception: " + ioException.getMessage());
            return null;
        }
        String dbClassName = properties.getProperty("db_class_name");
        String dbDatabaseUrl = properties.getProperty("db_database_url");
        String dbDatabaseName = properties.getProperty("db_database_name");
        try {
            Class.forName(dbClassName);
            connection = DriverManager.getConnection(dbDatabaseUrl + "/" + dbDatabaseName);
        } catch (SQLException | ClassNotFoundException exception) { System.err.println(exception.getMessage()); }
        finally { return connection; }
    }
}


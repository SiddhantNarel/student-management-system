package com.studentms.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/student_management_db";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";

    private String url;
    private String username;
    private String password;

    private DatabaseConnection() {
        loadProperties();
    }

    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is != null) {
                props.load(is);
                this.url = props.getProperty("db.url", DEFAULT_URL);
                this.username = props.getProperty("db.username", DEFAULT_USERNAME);
                this.password = props.getProperty("db.password", DEFAULT_PASSWORD);
            } else {
                this.url = DEFAULT_URL;
                this.username = DEFAULT_USERNAME;
                this.password = DEFAULT_PASSWORD;
            }
        } catch (IOException e) {
            this.url = DEFAULT_URL;
            this.username = DEFAULT_USERNAME;
            this.password = DEFAULT_PASSWORD;
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

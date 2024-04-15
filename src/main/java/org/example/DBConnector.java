package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mariadb://localhost:3306/yourDatabaseName";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

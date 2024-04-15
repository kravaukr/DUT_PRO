package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.helper.ConfigHelper.appConfig;

public class DbClient {

    private static final String URL = appConfig.getString("client.db.contact.points");
    private static final String USER = appConfig.getString("client.db.user");
    private static final String PASSWORD = appConfig.getString("client.db.password");


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

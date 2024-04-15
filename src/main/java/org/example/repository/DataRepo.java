package org.example.repository;

import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataRepo {

    private DbClient dbClient;

    public DataRepo(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public User getUser(String login) {
        String query = "SELECT * FROM users WHERE login = ?";
        User user = null;
        try (PreparedStatement stmt = dbClient.getConnection()
                .prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                user = new User(UUID.fromString(id), login, password);
            }
        } catch (SQLException e) {
        }
        return user;
    }

    public boolean createUser(User user) {
        String INSERT_USERS_SQL = "INSERT INTO users (id, login, password) VALUES (?, ?, ?);";

        try (Connection connection = dbClient.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());

            System.out.println(preparedStatement);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Обробляйте або логуйте помилку
            System.out.println(e);
            return false;
        }
    }

}

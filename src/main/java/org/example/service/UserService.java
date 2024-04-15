package org.example.service;

import org.example.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean createUser(User user);
    Optional<User> getUser(String login);
    User updateUser(User user);
    void deleteUser(String login);
}

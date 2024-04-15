package org.example.service;

import org.example.model.User;
import org.example.repository.DataRepo;

import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private DataRepo dataRepo;

    public UserServiceImpl(DataRepo dataRepo) {
        this.dataRepo = dataRepo;
    }

    @Override
    public boolean createUser(User userDto) {
        return dataRepo.createUser(userDto);
    }

    @Override
    public Optional<User> getUser(String login) {
        User user = dataRepo.getUser(login);
        if (user != null) return Optional.of(user);
        return Optional.empty();
    }

    @Override
    public User updateUser(User user) {
        // Реалізація оновлення користувача
        return user;
    }

    @Override
    public void deleteUser(String login) {

    }
}

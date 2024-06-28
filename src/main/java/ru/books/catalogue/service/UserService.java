package ru.books.catalogue.service;

import ru.books.catalogue.model.User;

import java.util.Optional;

public interface UserService {

    void registration(String username, String password);

    void deleteUserById(Long id);

    Optional<User> getUserById(Long id);
}

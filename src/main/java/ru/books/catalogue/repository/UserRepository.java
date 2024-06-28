package ru.books.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import ru.books.catalogue.model.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

package ru.books.catalogue.repository;

import org.springframework.data.repository.CrudRepository;
import ru.books.catalogue.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}

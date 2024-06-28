package ru.books.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.books.catalogue.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

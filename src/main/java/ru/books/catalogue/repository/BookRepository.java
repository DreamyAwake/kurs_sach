package ru.books.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.books.catalogue.model.Author;
import ru.books.catalogue.model.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthor(Author author);
}

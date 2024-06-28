package ru.books.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Mark;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByBook(Book book);

    List<Mark> findMarksByBookId(Long bookId);

    Mark findMarksByBookIdAndUserId(long bookId, Long userId);
}

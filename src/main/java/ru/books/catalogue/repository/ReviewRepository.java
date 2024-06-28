package ru.books.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByBook(Book book);
}

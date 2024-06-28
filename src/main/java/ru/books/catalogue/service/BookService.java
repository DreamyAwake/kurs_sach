package ru.books.catalogue.service;

import ru.books.catalogue.controller.requests.BookRequest;
import ru.books.catalogue.exceptions.AuthorNotFoundException;
import ru.books.catalogue.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

    Book addBook(BookRequest book) throws AuthorNotFoundException;

    List<Book> getAllBooks();

    List<Book> getBooksByAuthor(long id) throws AuthorNotFoundException;

    Optional<Book> putBookById(Long id, Book updatedBook);

    Optional<Book> getBookById(Long id);

    void deleteBookById(Long id);
}

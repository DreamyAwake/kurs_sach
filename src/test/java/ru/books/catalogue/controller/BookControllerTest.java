package ru.books.catalogue.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.books.catalogue.controller.requests.BookRequest;
import ru.books.catalogue.exceptions.AuthorNotFoundException;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.service.BookService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    @Test
    public void testAddBook() throws AuthorNotFoundException {
        BookRequest bookRequest = new BookRequest();
        Book mockBook = new Book();
        when(bookService.addBook(bookRequest)).thenReturn(mockBook);

        ResponseEntity<Book> response = bookController.addBook(bookRequest);

        assertEquals(mockBook, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllBooks() {
        List<Book> mockBookList = Arrays.asList(new Book(), new Book());
        when(bookService.getAllBooks()).thenReturn(mockBookList);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(mockBookList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetBooksByAuthorId() throws AuthorNotFoundException {
        Long authorId = 1L;
        List<Book> mockBookList = Arrays.asList(new Book(), new Book());
        when(bookService.getBooksByAuthor(authorId)).thenReturn(mockBookList);

        ResponseEntity<List<Book>> response = bookController.getBookByAuthorId(authorId);

        assertEquals(mockBookList, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatedBookById() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        Optional<Book> updatedBookOptional = Optional.of(updatedBook);
        when(bookService.putBookById(bookId, updatedBook)).thenReturn(updatedBookOptional);

        ResponseEntity<Book> response = bookController.updatedBookById(bookId, updatedBook);

        assertEquals(updatedBook, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteBookById() {
        Long bookId = 1L;
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        Mockito.verify(bookService).deleteBookById(bookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
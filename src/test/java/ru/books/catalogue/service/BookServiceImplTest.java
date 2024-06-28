package ru.books.catalogue.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.books.catalogue.controller.requests.BookRequest;
import ru.books.catalogue.exceptions.AuthorNotFoundException;
import ru.books.catalogue.model.Author;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.repository.BookRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testAddBook() throws AuthorNotFoundException {
        Author author = new Author();

        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthorId(1L);
        bookRequest.setBookName("Test Book");
        bookRequest.setBookDesc("Test Description");
        bookRequest.setNumberPage(100);
        bookRequest.setScore(4.5);
        bookRequest.setPublisher("Test Publisher");

        Mockito.when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(new Book(1L, author, "Test Book", "Test Description", 100, 4.5, "Test Publisher", new Date()));
        Book book = bookService.addBook(bookRequest);
        assertEquals("Test Book", book.getBookName());
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetBooksByAuthor() throws AuthorNotFoundException {
        Author author = new Author();
        Mockito.when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Mockito.when(bookRepository.findAllByAuthor(author)).thenReturn(books);
        List<Book> result = bookService.getBooksByAuthor(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(1L, new Author(), "Test Book", "Test Description", 100, 4.5, "Test Publisher", new Date());
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookService.getBookById(1L);
        assertEquals("Test Book", result.get().getBookName());
    }

    @Test
    public void testAddBookWithAuthorNotFoundException() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthorId(1L);
        Mockito.when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
        assertThrows(AuthorNotFoundException.class, () -> bookService.addBook(bookRequest));
    }
}
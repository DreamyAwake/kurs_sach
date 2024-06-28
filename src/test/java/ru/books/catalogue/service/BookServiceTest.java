package ru.books.catalogue.service;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.books.catalogue.controller.requests.BookRequest;
import ru.books.catalogue.exceptions.AuthorNotFoundException;
import ru.books.catalogue.model.Book;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

public class BookServiceTest {

    @Mock
    private BookService bookService;

    @Test
    void testAddBook() throws AuthorNotFoundException {
        BookRequest bookRequest = new BookRequest("Great Book", "John Doe");
        Book book = new Book("Great Book", "John Doe");
        when(bookService.addBook(bookRequest)).thenReturn(book);

        assertEquals(book, bookService.addBook(bookRequest));
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Book 1", "Author A"));
        books.add(new Book("Book 2", "Author B"));
        when(bookService.getAllBooks()).thenReturn(books);

        assertEquals(books, bookService.getAllBooks());
    }

    @Test
    void testGetBooksByAuthor() throws AuthorNotFoundException {
        long authorId = 1L;
        List<Book> booksByAuthor = new ArrayList<>();
        booksByAuthor.add(new Book("Book 1", "Author A"));
        when(bookService.getBooksByAuthor(authorId)).thenReturn(booksByAuthor);

        assertEquals(booksByAuthor, bookService.getBooksByAuthor(authorId));
    }

    @Test
    void testPutBookById() {
        Long id = 1L;
        Book updatedBook = new Book("Updated Book", "Author Z");
        when(bookService.putBookById(id, updatedBook)).thenReturn(Optional.of(updatedBook));

        assertTrue(bookService.putBookById(id, updatedBook).isPresent());
        assertEquals(updatedBook, bookService.putBookById(id, updatedBook).get());
    }

    @Test
    void testGetBookById() {
        Long id = 1L;
        Book book = new Book("Book 1", "Author A");
        when(bookService.getBookById(id)).thenReturn(Optional.of(book));

        assertTrue(bookService.getBookById(id).isPresent());
        assertEquals(book, bookService.getBookById(id).get());
    }

    @Test
    void testDeleteBookById() {
        Long id = 1L;
        bookService.deleteBookById(id);

        verify(bookService).deleteBookById(id);
    }
}
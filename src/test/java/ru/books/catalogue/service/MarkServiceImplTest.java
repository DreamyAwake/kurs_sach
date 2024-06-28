package ru.books.catalogue.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.books.catalogue.controller.requests.MarkRequest;
import ru.books.catalogue.exceptions.MarkNotFoundException;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Mark;
import ru.books.catalogue.model.User;
import ru.books.catalogue.repository.BookRepository;
import ru.books.catalogue.repository.MarkRepository;
import ru.books.catalogue.repository.UserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarkServiceImplTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private MarkServiceImpl markService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMark_NewMark() throws MarkNotFoundException {
        MarkRequest markRequest = new MarkRequest(1L, 1L, 5);
        Book book = new Book();
        User user = new User();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(markRepository.findMarksByBookIdAndUserId(1L, 1L)).thenReturn(null);
        when(markRepository.save(any(Mark.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(markRepository.findMarksByBookId(1L)).thenReturn(Arrays.asList(
                new Mark(1L, book, user, 4, new Date()),
                new Mark(2L, book, user, 5, new Date())
        ));

        Mark mark = markService.addMark(markRequest);

        assertNotNull(mark);
        assertEquals(5, mark.getIntMark());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testAddMark_UpdateMark() throws MarkNotFoundException {
        MarkRequest markRequest = new MarkRequest(1L, 1L, 4);
        Book book = new Book();
        User user = new User();
        Mark existingMark = new Mark(1L, book, user, 3, new Date());

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
        when(markRepository.findMarksByBookIdAndUserId(1L, 1L)).thenReturn(existingMark);

        Mark mark = markService.addMark(markRequest);

        assertNotNull(mark);
        assertEquals(4, mark.getIntMark());
        verify(markRepository, times(1)).save(existingMark);
    }

    @Test
    void testAddMark_BookNotFound() {
        MarkRequest markRequest = new MarkRequest(1L, 1L, 5);

        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        assertThrows(MarkNotFoundException.class, () -> markService.addMark(markRequest));
    }

    @Test
    void testAddMark_UserNotFound() {
        MarkRequest markRequest = new MarkRequest(1L, 1L, 5);
        Book book = new Book();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        assertThrows(MarkNotFoundException.class, () -> markService.addMark(markRequest));
    }

    @Test
    void testAddMark_InvalidMark() {
        MarkRequest markRequest = new MarkRequest(1L, 1L, 6);
        Book book = new Book();
        User user = new User();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> markService.addMark(markRequest));
    }

    @Test
    void testGetAllMarks() {
        when(markRepository.findAll()).thenReturn(Arrays.asList(new Mark(), new Mark()));

        List<Mark> marks = markService.getAllMarks();

        assertEquals(2, marks.size());
        verify(markRepository, times(1)).findAll();
    }

    @Test
    void testGetMarksByBook() throws MarkNotFoundException {
        Book book = new Book();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(markRepository.findAllByBook(book)).thenReturn(Arrays.asList(new Mark(), new Mark()));

        List<Mark> marks = markService.getMarksByBook(1L);

        assertEquals(2, marks.size());
        verify(markRepository, times(1)).findAllByBook(book);
    }

    @Test
    void testGetMarksByBook_BookNotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        assertThrows(MarkNotFoundException.class, () -> markService.getMarksByBook(1L));
    }

    @Test
    void testGetMarkById() {
        Mark mark = new Mark();

        when(markRepository.findById(1L)).thenReturn(Optional.of(mark));

        Optional<Mark> result = markService.getMarkById(1L);

        assertTrue(result.isPresent());
        assertEquals(mark, result.get());
    }
}
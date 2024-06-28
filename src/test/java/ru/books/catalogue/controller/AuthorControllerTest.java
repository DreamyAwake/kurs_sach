package ru.books.catalogue.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.books.catalogue.model.Author;
import ru.books.catalogue.service.AuthorService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorController = new AuthorController(authorService);
    }

    @Test
    void testAddAuthor() {
        Author author = new Author(null, "Name", "Surname");
        when(authorService.addAuthor(author)).thenReturn(author);

        ResponseEntity<Author> response = authorController.addAuthor(author);

        assertEquals(author, response.getBody());
        verify(authorService, times(1)).addAuthor(author);
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(null, "Name", "Surname"));
        authors.add(new Author(1L, "Name", "Surname"));
        when(authorService.getAllAuthors()).thenReturn(authors);

        ResponseEntity<List<Author>> response = authorController.getAllAuthors();

        assertEquals(authors, response.getBody());
        verify(authorService, times(1)).getAllAuthors();
    }
}
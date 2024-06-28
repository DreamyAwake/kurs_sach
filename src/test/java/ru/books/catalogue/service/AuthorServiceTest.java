package ru.books.catalogue.service;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.books.catalogue.model.Author;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorServiceTest {

    @Mock
    private AuthorService authorService;

    @Test
    void testAddAuthor() {
        Author author = new Author("Mister Smit");
        when(authorService.addAuthor(author)).thenReturn(author);

        assertEquals(author, authorService.addAuthor(author));
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Jane Smith"));
        authors.add(new Author("Alice Johnson"));
        when(authorService.getAllAuthors()).thenReturn(authors);

        assertEquals(authors, authorService.getAllAuthors());
    }

    @Test
    void testGetAuthorById() {
        Long id = 1L;
        Author author = new Author("John Doe");
        when(authorService.getAuthorById(id)).thenReturn(Optional.of(author));

        assertTrue(authorService.getAuthorById(id).isPresent());
        assertEquals(author, authorService.getAuthorById(id).get());
    }

    @Test
    void testPutAuthorById() {
        Long id = 1L;
        Author updatedAuthor = new Author("John Cena");
        when(authorService.putAuthorById(id, updatedAuthor)).thenReturn(Optional.of(updatedAuthor));

        assertTrue(authorService.putAuthorById(id, updatedAuthor).isPresent());
        assertEquals(updatedAuthor, authorService.putAuthorById(id, updatedAuthor).get());
    }

    @Test
    void testDeleteAuthorById() {
        Long id = 1L;
        authorService.deleteAuthorById(id);

        verify(authorService).deleteAuthorById(id);
    }
}
}
package ru.books.catalogue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.books.catalogue.model.Author;
import ru.books.catalogue.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/authors")
@RestController
public class AuthorController {

    private final AuthorService authorService;
    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.addAuthor(author));
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PutMapping("/{id}")
    ResponseEntity<Author> updatedAuthorById(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        Optional<Author> updatedAuthorOptional = authorService.putAuthorById(id, updatedAuthor);
        return updatedAuthorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }


}

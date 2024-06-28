package ru.books.catalogue.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(generator = "author_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String surname;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}

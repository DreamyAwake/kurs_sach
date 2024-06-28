package ru.books.catalogue.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(generator = "book_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "author_id")
    @ManyToOne
    private Author author;

    private String bookName;

    private String bookDesc;

    private Integer numberPage;

    private Double score;

    private String publisher;

    private Date bookDate;

}

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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(generator = "review_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "review_id_seq", sequenceName = "review_id_seq", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "book_id")
    @ManyToOne
    private Book book;

    private String reviewText;

    private Date reviewDate;
}

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
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(generator = "mark_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mark_id_seq", sequenceName = "mark_id_seq", allocationSize = 1)
    private Long Id;

    @JoinColumn(name = "book_id")
    @ManyToOne
    private Book book;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    private Integer intMark;

    private Date markDate;
}

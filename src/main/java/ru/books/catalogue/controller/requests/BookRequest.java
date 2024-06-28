package ru.books.catalogue.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequest {

    private Long authorId;

    private String bookName;

    private String bookDesc;

    private Integer numberPage;

    private Double score;

    private String publisher;


}

package ru.books.catalogue.service;

import ru.books.catalogue.controller.requests.MarkRequest;
import ru.books.catalogue.exceptions.MarkNotFoundException;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Mark;
import java.util.List;
import java.util.Optional;

public interface MarkService {

    Mark addMark(MarkRequest mark) throws MarkNotFoundException;

    List<Mark> getAllMarks();

    List<Mark> getMarksByBook(long id) throws MarkNotFoundException;

    Optional<Mark> getMarkById(Long id);

    void deleteMarkById(Long id);

    Optional<Mark> putMarkById(Long id, Mark updatedMark);
}

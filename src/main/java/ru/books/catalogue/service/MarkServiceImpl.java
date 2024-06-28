package ru.books.catalogue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.books.catalogue.controller.requests.MarkRequest;
import ru.books.catalogue.exceptions.MarkNotFoundException;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Mark;
import ru.books.catalogue.model.User;
import ru.books.catalogue.repository.BookRepository;
import ru.books.catalogue.repository.MarkRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;
    private final BookService bookService;
    private final UserService userService;
    private final BookRepository bookRepository;

    @Override
    public Mark addMark(MarkRequest mark) throws MarkNotFoundException {
        Optional<Book> book = bookService.getBookById(mark.getBookId());
        Optional<User> user = userService.getUserById(mark.getUserId());

        if (book.isPresent() && user.isPresent()) {
            Mark existingMark = markRepository.findMarksByBookIdAndUserId(mark.getBookId(), mark.getUserId());
            if (existingMark != null) {
                existingMark.setIntMark(mark.getIntMark());
                existingMark.setMarkDate(new Date());
                markRepository.save(existingMark);
                return existingMark;
            } else {
                int intMark = mark.getIntMark();
                if (intMark >= 1 && intMark <= 5) {
                    Mark newMark = markRepository.save(
                            new Mark(
                                    null,
                                    book.get(),
                                    user.get(),
                                    intMark,
                                    new Date()
                            )
                    );

                    List<Mark> marks = markRepository.findMarksByBookId(mark.getBookId());
                    double totalMarks = marks.stream().mapToInt(Mark::getIntMark).sum();
                    int totalMarkCount = marks.size();
                    double averageMark = totalMarkCount > 0 ? totalMarks / totalMarkCount : 0;

                    book.get().setScore(averageMark);
                    bookRepository.save(book.get());

                    return newMark;
                } else {
                    throw new IllegalArgumentException("Оценка книги должна быть от 1 до 5");
                }
            }
        } else {
            throw new MarkNotFoundException(mark.getBookId());
        }
    }


    @Override
    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    @Override
    public List<Mark> getMarksByBook(long id) throws MarkNotFoundException {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return markRepository.findAllByBook(book.get());
        }
        else {
            throw new MarkNotFoundException(id);
        }
    }

    @Override
    public Optional<Mark> getMarkById(Long id) {
        return markRepository.findById(id);
    }

    @Override
    public void deleteMarkById (Long id){
        markRepository.deleteById(id);
    }

    @Override
    public Optional<Mark> putMarkById(Long id, Mark updatedMark) {
        Optional<Mark> existingMark = markRepository.findById(id);
        if (existingMark.isPresent()) {
            Mark markToUpdate = existingMark.get();
            if (updatedMark.getIntMark() != null) {
                markToUpdate.setIntMark(updatedMark.getIntMark());
            }
            markRepository.save(markToUpdate);
        }
        return existingMark;
    }

}
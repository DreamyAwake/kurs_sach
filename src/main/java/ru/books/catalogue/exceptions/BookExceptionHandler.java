package ru.books.catalogue.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.UUID;

@Slf4j
@ControllerAdvice
@Component
public class BookExceptionHandler {

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundException(AuthorNotFoundException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "Нет автора с таким id: " + e.getId()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        String uuid = UUID.randomUUID().toString();
        log.error("Exception with uuid {}: {}", uuid, e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        500L,
                        "Что-то пошло не так, уже исправляем. Пожалуйста, обратитесь с номером ошибки: " + uuid
                )
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        400L,
                        "Оценка книги должна быть от 1 до 5"
                )
        );
    }
}

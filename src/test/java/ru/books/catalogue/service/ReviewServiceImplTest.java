package ru.books.catalogue.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.books.catalogue.controller.requests.ReviewRequest;
import ru.books.catalogue.exceptions.BookNotFoundException;
import ru.books.catalogue.model.Author;
import ru.books.catalogue.model.Book;
import ru.books.catalogue.model.Review;
import ru.books.catalogue.repository.ReviewRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookService bookService;

    @Test
    public void testAddReview() throws BookNotFoundException {
        ReviewRequest reviewRequest = new ReviewRequest(1L, "Great book");
        Author author = new Author(1L, "Name", "Surname");
        Book book = new Book(1L, author, "Book Name", "Book Desc", 5, null, "ACT", new Date());

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review(1L, book, "Great book", new Date()));

        ReviewServiceImpl reviewService = new ReviewServiceImpl(reviewRepository, bookService);
        Review addedReview = reviewService.addReview(reviewRequest);

        assertEquals("Great book", addedReview.getReviewText());
    }

    @Test
    public void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(Arrays.asList(new Review(), new Review()));

        ReviewServiceImpl reviewService = new ReviewServiceImpl(reviewRepository, bookService);
        List<Review> allReviews = reviewService.getAllReviews();

        assertEquals(2, allReviews.size());
    }

    @Test
    public void testGetReviewsByBook() throws BookNotFoundException {
        Author author = new Author(1L, "Name", "Surname");
        Book book = new Book(1L, author, "Book Name", "Book Desc", 5, null, "ACT", new Date());

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(reviewRepository.findAllByBook(book)).thenReturn(Arrays.asList(new Review(), new Review()));

        ReviewServiceImpl reviewService = new ReviewServiceImpl(reviewRepository, bookService);
        List<Review> reviewsByBook = reviewService.getReviewsByBook(1L);

        assertEquals(2, reviewsByBook.size());
    }

    @Test
    public void testGetReviewById() {
        Review expectedReview = new Review(1L, new Book(), "Review Text", new Date());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(expectedReview));

        ReviewServiceImpl reviewService = new ReviewServiceImpl(reviewRepository, bookService);
        Optional<Review> reviewById = reviewService.getReviewById(1L);

        assertEquals(expectedReview, reviewById.get());
    }
}
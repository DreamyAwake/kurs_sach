package ru.books.catalogue.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.books.catalogue.controller.requests.ReviewRequest;
import ru.books.catalogue.exceptions.BookNotFoundException;
import ru.books.catalogue.model.Review;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewService reviewService;

    @Test
    void testAddReview() throws BookNotFoundException {
        ReviewRequest mockRequest = new ReviewRequest(null, "Great book!");
        Review mockReview = new Review(1L, null, "Great book!", new Date());
        when(reviewService.addReview(mockRequest)).thenReturn(mockReview);

        assertEquals(mockReview, reviewService.addReview(mockRequest));
    }

    @Test
    void testGetAllReviews() {
        List<Review> mockReviews = Arrays.asList(
                new Review(1L, null, "Good read", new Date()),
                new Review(2L, null, "Must read", new Date())
        );
        when(reviewService.getAllReviews()).thenReturn(mockReviews);

        assertEquals(mockReviews, reviewService.getAllReviews());
    }

    @Test
    void testGetReviewsByBook() throws BookNotFoundException {
        long bookId = 1L;
        List<Review> mockReviews = Collections.singletonList(
                new Review(1L, null, "Excellent book", new Date())
        );
        when(reviewService.getReviewsByBook(bookId)).thenReturn(mockReviews);

        assertEquals(mockReviews, reviewService.getReviewsByBook(bookId));
    }

    @Test
    void testGetReviewById() {
        Long reviewId = 1L;
        Review mockReview = new Review(1L, null, "Informative review", new Date());
        when(reviewService.getReviewById(reviewId)).thenReturn(Optional.of(mockReview));

        assertTrue(reviewService.getReviewById(reviewId).isPresent());
        assertEquals(mockReview, reviewService.getReviewById(reviewId).get());
    }

    @Test
    void testDeleteReviewById() {
        Long reviewId = 1L;
        reviewService.deleteReviewById(reviewId);

        verify(reviewService, times(1)).deleteReviewById(reviewId);
    }

    @Test
    void testPutReviewById() {
        Long reviewId = 1L;
        Review updatedReview = new Review(1L, null, "Updated review", new Date());
        when(reviewService.putReviewById(reviewId, updatedReview)).thenReturn(Optional.of(updatedReview));

        assertTrue(reviewService.putReviewById(reviewId, updatedReview).isPresent());
        assertEquals(updatedReview, reviewService.putReviewById(reviewId, updatedReview).get());
    }
}
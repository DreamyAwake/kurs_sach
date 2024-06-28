package ru.books.catalogue.service;

import ru.books.catalogue.controller.requests.ReviewRequest;
import ru.books.catalogue.exceptions.BookNotFoundException;
import ru.books.catalogue.model.Mark;
import ru.books.catalogue.model.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review addReview(ReviewRequest review) throws BookNotFoundException;

    List<Review> getAllReviews();

    List<Review> getReviewsByBook(long id) throws BookNotFoundException;

    Optional<Review> getReviewById(Long id);

    Optional<Review> putReviewById(Long id, Review updatedReview);

    void deleteReviewById(Long id);
}

package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review);

    List<Review> getReviewsForProduct(int productId);

    void deleteReview(Review review);

    Review findReviewById(int id);

}

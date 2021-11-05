package com.javaproject.storeapp.service.impl;

import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.Review;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.ReviewRepository;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForProduct(int productId) {
        Product product = productService.findProductById(productId);
        return reviewRepository.findReviewsByProduct(productId);
    }

    @Override
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Review findReviewById(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            return reviewOptional.get();
        } else {
            throw new ResourceNotFoundException("Review with Id " + id + " not found.");
        }
    }
}

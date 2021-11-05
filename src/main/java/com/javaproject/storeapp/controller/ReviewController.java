package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.dto.ReviewRequest;
import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.Review;
import com.javaproject.storeapp.entity.User;
import com.javaproject.storeapp.mapper.ReviewMapper;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.ReviewService;
import com.javaproject.storeapp.service.impl.ProductServiceImpl;
import com.javaproject.storeapp.service.impl.ReviewServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final ReviewMapper reviewMapper;

    public ReviewController(ReviewService reviewService, ProductService productService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.reviewMapper = reviewMapper;
    }

    @PostMapping("{productId:\\d+}")
    public ModelAndView createReview(@PathVariable int productId,
                                     @Valid
                                     @RequestBody
                                     @ModelAttribute ReviewRequest reviewRequest,
                                     Principal principal) {

        Product product = productService.findProductById(productId);
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        //compute rating
        List<Review> reviews = reviewService.getReviewsForProduct(productId);
        int sum = reviews.stream().mapToInt(Review::getRating).sum();
        double newRating = (sum + reviewRequest.getRating()) / (reviews.size() + 1);
        productService.updateRating(product, newRating);

        //create Review entity
        Review review = reviewMapper.reviewRequestToReview(reviewRequest);
        review.setAuthor(user.getUsername());
        review.setProduct(product);
        review.setDateAdded(LocalDate.now());
        reviewService.createReview(review);

        ModelAndView modelAndView = new ModelAndView("productDetails");
        modelAndView.addObject("product", product);
        List<Review> reviewsFound = reviewService.getReviewsForProduct(product.getId());
        modelAndView.addObject("reviews", reviewsFound);
        return modelAndView;
    }

    @PostMapping("/delete/{reviewId}")
    public ModelAndView deleteReview(@PathVariable int reviewId,
                                     Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Review review = reviewService.findReviewById(reviewId);
        Product product = productService.findProductById(review.getProduct().getId());
        ModelAndView modelAndView = new ModelAndView("productDetails");
        if (user.getUsername().equals(review.getAuthor()) || user.getRole().getName().equals("ROLE_ADMIN")) {
            reviewService.deleteReview(review);
            //update rating
            List<Review> reviews = reviewService.getReviewsForProduct(product.getId());
            int sum = reviews.stream().mapToInt(Review::getRating).sum();
            double newRating = reviews.size() != 0 ? sum / reviews.size() : sum;
            productService.updateRating(product, newRating);

            modelAndView.addObject("sameUser", "YES");
        } else {
            modelAndView.addObject("sameUser", "NO");
        }
        modelAndView.addObject("product", product);
        List<Review> reviewsFound = reviewService.getReviewsForProduct(product.getId());
        modelAndView.addObject("reviews", reviewsFound);
        return modelAndView;
    }
}

package com.javaproject.storeapp.mapper;

import com.javaproject.storeapp.dto.ReviewRequest;
import com.javaproject.storeapp.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review reviewRequestToReview(ReviewRequest reviewRequest) {
        return new Review(reviewRequest.getComment(), reviewRequest.getRating());
    }
}

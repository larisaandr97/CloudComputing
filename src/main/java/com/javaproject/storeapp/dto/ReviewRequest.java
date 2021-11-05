package com.javaproject.storeapp.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReviewRequest {

    @NotBlank(message = "Comment cannot be null.")
    @Length(min = 2, max = 250)
    private String comment;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

//    @NotNull
//    @Length(min = 2, max = 100)
//    private String author;

//    @NotNull
//    private Product product;

    public ReviewRequest(String comment, int rating) {//, Product product) {
        this.comment = comment;
        this.rating = rating;
        // this.author = author;
        //  this.product = product;
    }
}

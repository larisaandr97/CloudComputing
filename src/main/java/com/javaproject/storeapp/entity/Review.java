package com.javaproject.storeapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;
    private int rating;
    private String author;

    @ManyToOne()
    private Product product;

    private LocalDate dateAdded;

    public Review() {
    }

    public Review(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }
}

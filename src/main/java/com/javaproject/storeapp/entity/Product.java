package com.javaproject.storeapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String name;
    private String description;
    private double price;

    private int stock;

    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory productCategory;

    @Lob
    private Byte[] image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    public Product() {
    }

    public Product(String name, String description, double price, ProductCategory productCategory, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.stock = stock;
    }

    public Product(int id, String name, String description, double price, ProductCategory productCategory, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.stock = stock;
    }
}

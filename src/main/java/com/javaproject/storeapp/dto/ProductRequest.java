package com.javaproject.storeapp.dto;

import com.javaproject.storeapp.entity.ProductCategory;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {

    @NotNull
    @Length(min = 2, message = "Name must have between 2 and 100 characters.")
    private String name;

    @NotNull
    @Length(min = 1, max = 100, message = "Description must have between 1 and 100 characters.")
    private String description;

    @NotNull
    @DecimalMin(value = "0.1", message = "Price cannot be zero.")
    private double price;

    @NotNull
    @Min(0)
    private int stock;

    @NotNull
    private ProductCategory productCategory;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, double price, int stock, ProductCategory productCategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.productCategory = productCategory;
    }

}

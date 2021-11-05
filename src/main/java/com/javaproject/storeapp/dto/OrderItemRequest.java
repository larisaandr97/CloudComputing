package com.javaproject.storeapp.dto;

import com.javaproject.storeapp.entity.Product;
import lombok.Data;

@Data
public class OrderItemRequest {
    private Product product;
    private int quantity;
    private double price;

    public OrderItemRequest(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

}

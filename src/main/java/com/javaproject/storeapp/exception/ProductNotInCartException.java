package com.javaproject.storeapp.exception;

public class ProductNotInCartException extends RuntimeException {
    public ProductNotInCartException(int id) {
        super("Product with Id " + id + " not in cart.");
    }
}

package com.javaproject.storeapp.exception;

public class CartIsEmptyException extends RuntimeException {

    public CartIsEmptyException(int id) {
        super("Cart for customer with Id " + id + " is empty! You must add some items before making an order.");
    }
}

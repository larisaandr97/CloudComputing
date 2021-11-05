package com.javaproject.storeapp.exception;

public class DuplicateCardNumberException extends RuntimeException {
    public DuplicateCardNumberException(String cardNumber) {
        super("A bank account with the card number " + cardNumber + " already exists.");
    }
}

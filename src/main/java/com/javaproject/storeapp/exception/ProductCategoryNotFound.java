package com.javaproject.storeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductCategoryNotFound extends RuntimeException {
    public ProductCategoryNotFound(String name) {
        super("Product category " + name + " does not exist!");
    }
}

package com.javaproject.storeapp.entity;

public enum ProductCategory {
    FASHION, SUPERMARKET, LAPTOPS, PHONES, HOME, BOOKS, TOYS;

    public static boolean contains(String test) {
        for (ProductCategory c : ProductCategory.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

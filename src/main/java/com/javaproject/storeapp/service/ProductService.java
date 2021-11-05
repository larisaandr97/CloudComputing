package com.javaproject.storeapp.service;

import com.javaproject.storeapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product createProduct(Product product);

    Product findProductById(int id);

    Page<Product> getProductsBy(String category, String name, boolean descending, Pageable pageable);

    Product updateStock(int productId, int stock);

    void updateRating(Product product, double value);
}

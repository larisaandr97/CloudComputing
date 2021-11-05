package com.javaproject.storeapp.mapper;

import com.javaproject.storeapp.dto.ProductRequest;
import com.javaproject.storeapp.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product productRequestToProduct(ProductRequest productRequest) {
        return new Product(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getProductCategory(), productRequest.getStock());
    }
}

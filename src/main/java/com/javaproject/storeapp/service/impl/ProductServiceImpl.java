package com.javaproject.storeapp.service.impl;

import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.ProductCategory;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.ProductRepository;
import com.javaproject.storeapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product createProduct(Product p) {
        return productRepository.save(p);
    }

    @Override
    public Product findProductById(int id) {
        Optional<Product> productOptional = Optional.ofNullable(productRepository.findProductById(id));
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ResourceNotFoundException("Product with Id " + id + " not found.");
        }
    }

    @Override
    public Page<Product> getProductsBy(String category, String name, boolean descending, Pageable pageable) {
        if (category != null && !category.equals("null")) {
            String upperCaseCategory = category.toUpperCase();
            if (!ProductCategory.contains(upperCaseCategory))
                throw new ResourceNotFoundException("Category " + category + " not found.");
        }
        if (category != null && category.equals("null"))
            category = null;
        List<Product> products = productRepository.getProductsBy(category, name, descending);
        return findPaginated(products, pageable);
    }

    private Page<Product> findPaginated(List<Product> products, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> result;

        if (products.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, products.size());
            result = products.subList(startItem, toIndex);
        }

        return new PageImpl<>(result, PageRequest.of(currentPage, pageSize), products.size());//descending ? Sort.by("price").descending() : Sort.by("price").ascending()), products.size());
    }

    @Override
    public Product updateStock(int productId, int stock) {
        Product product = findProductById(productId);
        product.setStock(stock);
        return productRepository.save(product);
    }

    @Override
    public void updateRating(Product product, double value) {
        product.setRating(value);
        productRepository.save(product);
    }

}


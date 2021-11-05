package com.javaproject.storeapp.repository;


import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(int id);

    default List<Product> getProductsBy(String category, String name, boolean descending) {//, Pageable pageable) {
        return this.findAll().stream()
                .filter(product -> {
                    if (category != null) {//we filter by category only if the category was sent in the request
                        if (name != null && !name.equals("")) { //we filter by name only if the name was sent in the request
                            return ProductCategory.valueOf(category.toUpperCase()).equals(product.getProductCategory()) && product.getName().toLowerCase().contains(name.toLowerCase());
                        } else {
                            return ProductCategory.valueOf(category.toUpperCase()).equals(product.getProductCategory());
                        }
                    } else if (name != null && !name.equals("")) { //we filter by name only if the name was sent in the request
                        return product.getName().toLowerCase().contains(name.toLowerCase());
                    } else {//no filters are sent in the request, all products should be returned
                        return true;
                    }
                })
                .sorted(descending ? Comparator.comparingDouble(Product::getPrice).reversed() : Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());

    }
}
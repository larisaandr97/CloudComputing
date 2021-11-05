package com.javaproject.storeapp.service;


import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.ProductCategory;
import com.javaproject.storeapp.exception.ResourceNotFoundException;
import com.javaproject.storeapp.repository.ProductRepository;
import com.javaproject.storeapp.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    @DisplayName("Add a new Product")
    public void createProductTest() {
        //arrange
        Product product = new Product("Harry Potter", "fantasy series book", 60.0, ProductCategory.BOOKS, 10);
        Product savedProduct = new Product("Harry Potter", "fantasy series book", 60.0, ProductCategory.BOOKS, 10);
        when(productRepository.save(product)).thenReturn(savedProduct);

        //act
        Product result = productService.createProduct(product);

        //assert
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getProductCategory(), result.getProductCategory());
        assertEquals(product.getStock(), result.getStock());
        verify(productRepository, Mockito.times((1))).save(product);
    }

    @Test
    @DisplayName("Find Product By Id - happy flow")
    public void findProductByIdTestHappyFlow() {

        Product product = new Product();
        product.setId(1);
        when(productRepository.findProductById(product.getId()))
                .thenReturn(product);

        Product result = productService.findProductById(product.getId());

        assertNotNull(product.getId());
        assertEquals(product.getId(), result.getId());

    }

    @Test
    @DisplayName("Find product By Id - product not found")
    public void findProductByIdTestNotFound() {

        Product product = new Product();
        product.setId(1);
        when(productRepository.findProductById(product.getId()))
                .thenReturn(null);

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> productService.findProductById(product.getId()));
        assertEquals("Product with Id " + product.getId() + " not found.", exception.getMessage());

    }

   /* @Test
    @DisplayName("Find products by different parameters - happy flow")
    public void findProductsByTestHappyFlow() {
        String category = "toys";
        String name = "lego";
        Pageable sortedByName =
                PageRequest.of(0, 6, Sort.by("price").ascending() );
        when(productRepository.getProductsBy(category, name, false,sortedByName))
                .thenReturn(Arrays.asList(
                        new Product("lego creator", "fiat", 50.0, ProductCategory.TOYS, 15),
                        new Product("lego disney", "Elsa castle", 250.0, ProductCategory.TOYS, 4)));

        List<Product> result = productService.getProductsBy(category, name, false).stream().collect(Collectors.toList());

        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getProductCategory(), ProductCategory.TOYS);

    }*/

    @Test
    @DisplayName("Find products by different parameters - product category not found")
    public void findProductsByTestCategoryNotFoundException() {
//        String category = "games";
//
//        RuntimeException exception = assertThrows(ProductCategoryNotFound.class, () -> productService.getProductsBy(category, null, false));
//
//        assertEquals("Product category " + category + " does not exist!", exception.getMessage());

    }


}

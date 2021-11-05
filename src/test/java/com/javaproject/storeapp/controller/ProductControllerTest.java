package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.mapper.ProductMapper;
import com.javaproject.storeapp.service.ImageService;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.ReviewService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private Model model;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ImageService imageService;

    @Mock
    private ReviewService reviewService;

    private ProductController productController;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        productController = new ProductController(productService, productMapper, imageService, reviewService);
    }

    @Captor
    ArgumentCaptor<Product> argumentCaptor;

    @Test
    @DisplayName("Get Product by Id Test")
    public void getProductByIdTest() {
        int id = 1;
        Product productTest = new Product();
        productTest.setId(id);

        when(productService.findProductById(id)).thenReturn(productTest);

        String viewName = productController.getProduct(id, model);
        Assert.assertEquals("productDetails", viewName);
        verify(productService, times(1)).findProductById(id);

        verify(model, times(1))
                .addAttribute(eq("product"), argumentCaptor.capture());

        Product productArg = argumentCaptor.getValue();
        Assert.assertEquals(productArg.getId(), productTest.getId());
    }
}


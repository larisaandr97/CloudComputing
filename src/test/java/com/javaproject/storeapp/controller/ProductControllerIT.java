package com.javaproject.storeapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaproject.storeapp.dto.ProductRequest;
import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.ProductCategory;
import com.javaproject.storeapp.mapper.ProductMapper;
import com.javaproject.storeapp.service.ImageService;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.ReviewService;
import com.javaproject.storeapp.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("mysql")
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ImageService imageService;

    @MockBean
    private ReviewService reviewService;

    @Test
    @DisplayName("Create a new Product")
    @WithMockUser(roles = "ADMIN")
    public void createProductTest() throws Exception {
        ProductRequest request = new ProductRequest("Cupcake", "Chocolate mousse", 10, 12, ProductCategory.SUPERMARKET);

        when(productService.createProduct(any())).thenReturn(new Product(1, "Cupcake", "Chocolate mousse", 10, ProductCategory.SUPERMARKET, 12));

        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("admin2021")))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Get Product by Id - Not found")
    public void getProductByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/products/{id}", 20))
                .andExpect(status().isNotFound())
                .andExpect(view().name("notfound"));

    }

    @Test
    @DisplayName("Get Product by Id - Positive case")
    public void getProductByIdTest() throws Exception {
        Product product = new Product(1, "Cupcake", "chocolate", 12, ProductCategory.SUPERMARKET, 5);
        mockMvc.perform(get("/products/{id}", "1"))//.param("product", String.valueOf(product)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("product", product))
                .andExpect(view().name("productDetails"));

    }
}

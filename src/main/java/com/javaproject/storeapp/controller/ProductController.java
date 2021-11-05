package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.dto.ProductRequest;
import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.entity.ProductCategory;
import com.javaproject.storeapp.entity.Review;
import com.javaproject.storeapp.mapper.ProductMapper;
import com.javaproject.storeapp.service.ImageService;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.ReviewService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/products")
@CrossOrigin(origins = {"http://localhost:3000"})
@Api(value = "/products",
        tags = "Products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ProductMapper productMapper, ImageService imageService, ReviewService reviewService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.imageService = imageService;
        this.reviewService = reviewService;
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        List<ProductCategory> categoriesAll = Arrays.asList(ProductCategory.values());
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("categoriesAll", categoriesAll);
        return "addProduct";
    }

    @PostMapping
    public String addProduct(@Valid
                             @RequestBody
                             @ModelAttribute
                                     ProductRequest productRequest,
                             BindingResult bindingResult,
                             @RequestParam("imagefile") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }
        Product product = productMapper.productRequestToProduct(productRequest);
        Product createdProduct = productService.createProduct(product);
        imageService.saveImageFile(createdProduct.getId(), file);
        return "redirect:/products/";
    }

    @GetMapping("{id}")
    public String getProduct(@PathVariable int id, Model model) {
        Product productFound = productService.findProductById(id);
        List<Review> reviewsFound = reviewService.getReviewsForProduct(id);
        model.addAttribute("reviews", reviewsFound);
        model.addAttribute("product", productFound);
        return "productDetails";
    }

    @GetMapping
    public String getAllProducts(
            @RequestParam(required = false)
                    String category,
            @RequestParam(required = false)
                    String name,
            @RequestParam(required = false)
                    boolean descending,
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        Page<Product> productPage = productService.getProductsBy(category, name, descending, PageRequest.of(currentPage - 1, pageSize));//, descending ? Sort.by("price").descending() : Sort.by("price").ascending()));
        model.addAttribute("productPage", productPage);
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "products";
    }

}

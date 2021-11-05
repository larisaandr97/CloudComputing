package com.javaproject.storeapp.controller;

import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.service.ProductService;
import com.javaproject.storeapp.service.impl.ProductServiceImpl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ProductService productService;

    public ImageController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String returnHomePage() {
        return "home";
    }

    @GetMapping("products/getImage/{id}")
    public void downloadImage(@PathVariable int id, HttpServletResponse response) throws IOException {
        Product product = productService.findProductById(id);

        if (product.getImage() != null) {
            byte[] byteArray = new byte[product.getImage().length];
            int i = 0;
            for (Byte wrappedByte : product.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            try {
                IOUtils.copy(is, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

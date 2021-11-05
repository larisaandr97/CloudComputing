package com.javaproject.storeapp.service.impl;

import com.javaproject.storeapp.entity.Product;
import com.javaproject.storeapp.repository.ProductRepository;
import com.javaproject.storeapp.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;

    public ImageServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(int productId, MultipartFile file) {
        try {
            Product product = productRepository.findProductById(productId);
            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
            product.setImage(byteObjects);
            productRepository.save(product);
        } catch (IOException e) {
        }
    }
}

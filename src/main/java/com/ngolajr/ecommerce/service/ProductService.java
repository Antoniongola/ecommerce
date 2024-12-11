package com.ngolajr.ecommerce.service;
import com.ngolajr.ecommerce.model.Product;
import com.ngolajr.ecommerce.model.util.FIleManager;
import com.ngolajr.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FIleManager fileManager;

    public Product newProduct(Product product, MultipartFile image) throws IOException {
        if(productRepository.findByNameEqualsIgnoreCase(product.getName()).isEmpty() && !image.getName().isEmpty()) {
            productRepository.save(product);
            product.setImage(image.getName());
            fileManager.saveFile(image, image.getName());

            return productRepository.save(product);
        }
        return null;
    }

    public Product findProductById(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findByNameContainsIgnoreCase(name);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategoryContainsIgnoreCase(category);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product, long id, MultipartFile image) throws IOException {
        Product oldProduct = productRepository.findById(id).orElseThrow();
        if(!image.isEmpty()){
            fileManager.deleteFile(oldProduct.getImage());
            fileManager.saveFile(image, image.getName());
            product.setImage(image.getName());
        }

        if(productRepository.findById(id).isPresent()) {
            return productRepository.save(product);
        }

        return null;
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }
}
package com.zepto.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zepto.entity.ProductEntity;
import com.zepto.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // CREATE
    @PostMapping
    public ProductEntity addProduct(@RequestBody ProductEntity product) {
        return productRepository.save(product);
    }

    // READ ALL
    @GetMapping
    public Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ProductEntity getProduct(@PathVariable int id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        return product.orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ProductEntity updateProduct(@PathVariable int id, @RequestBody ProductEntity product) {
        Optional<ProductEntity> existing = productRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        ProductEntity entity = existing.get();
        entity.setName(product.getName());
        entity.setCategory(product.getCategory());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setAvailable(product.isAvailable());
        return productRepository.save(entity);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        if (!productRepository.existsById(id)) {
            return "Product not found";
        }
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }
}
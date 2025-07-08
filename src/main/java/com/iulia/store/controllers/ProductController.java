package com.iulia.store.controllers;

import com.iulia.store.dtos.ProductDto;
import com.iulia.store.entities.Product;
import com.iulia.store.mappers.ProductMapper;
import com.iulia.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @RequestMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId){
        List<Product> products;
        if(categoryId == null) {
            products = productRepository.findAllWithCategory();
        } else {
            products = productRepository.findByCategoryId(categoryId);
        }
        return products.stream()
                .map(productMapper::productToProductDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        var product = productRepository.findById(id).stream().map(productMapper::productToProductDto).findFirst().orElse(null);
        if(product == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(product);
    }


}

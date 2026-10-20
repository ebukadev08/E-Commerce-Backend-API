
package com.ebuka.ecommerce.controller;

import com.ebuka.ecommerce.dto.req.ProductRequest;
import com.ebuka.ecommerce.dto.res.ApiRes;
import com.ebuka.ecommerce.model.Product;
import com.ebuka.ecommerce.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiRes<Product>> createProduct(
            @Valid @RequestBody ProductRequest request) {

        Product product = productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiRes.success(
                        "Product created successfully", product));
    }

    @GetMapping
    public ResponseEntity<ApiRes<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(
                ApiRes.success("Products retrieved successfully", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRes<Product>> getProductById(
            @PathVariable Long id) {

        Product product = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiRes.success("Product retrieved successfully", product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiRes<Product>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        Product product = productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiRes.success("Product updated successfully", product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiRes<Void>> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiRes.success("Product deleted successfully", null));
    }
}

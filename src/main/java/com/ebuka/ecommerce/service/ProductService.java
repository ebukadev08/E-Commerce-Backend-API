
package com.ebuka.ecommerce.service;

import com.ebuka.ecommerce.dto.req.ProductRequest;
import com.ebuka.ecommerce.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest request);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}

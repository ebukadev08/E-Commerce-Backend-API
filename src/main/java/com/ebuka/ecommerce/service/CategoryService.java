
package com.ebuka.ecommerce.service;

import com.ebuka.ecommerce.dto.req.CategoryRequest;
import com.ebuka.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryRequest request);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);
}

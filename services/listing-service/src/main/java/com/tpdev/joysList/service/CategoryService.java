package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Category;

import java.util.List;

public interface CategoryService {

    void createCategory(Category category);

    List<Category> getAllCategory();

    Category getCategoryByName(String name);

    void deleteCategory(Long id);
}

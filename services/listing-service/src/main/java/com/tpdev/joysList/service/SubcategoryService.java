package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {

    List<Subcategory> getSubcategoriesByCategory(Long categoryId);

    void createSubcategory(Subcategory subcategory);

    void deleteSubcategory(Long id);

    Subcategory findById(Long id);

    List<Subcategory> findAll();
}

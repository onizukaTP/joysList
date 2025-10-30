package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.repo.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository repository;

    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    public Subcategory createSubcategory(Subcategory subcategory) {
        return repository.save(subcategory);
    }

    public void deleteSubcategory(Long id) {
        repository.deleteById(id);
    }

    public Subcategory findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

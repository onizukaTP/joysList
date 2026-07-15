package com.tpdev.joysList.service.impl;

import com.tpdev.joysList.entity.Subcategory;
import com.tpdev.joysList.repo.SubcategoryRepository;
import com.tpdev.joysList.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository repository;

    @Override
    public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public void createSubcategory(Subcategory subcategory) {
        repository.save(subcategory);
    }

    @Override
    public void deleteSubcategory(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Subcategory findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Subcategory> findAll() {
        return repository.findAll();
    }
}
